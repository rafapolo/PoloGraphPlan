/*
 * Planner.java
 *
 * Autor: Rafael Polo
 * Date: Julho 2011
 */
package com.extrapolo.graphplan.control;

import com.extrapolo.graphplan.model.Action;
import com.extrapolo.graphplan.model.State;
import com.extrapolo.graphplan.model.Mutexable;
import com.extrapolo.graphplan.view.Screen;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Planner {

    private ArrayList<State> inicialStates = new ArrayList<State>();
    private ArrayList<State> goalStates = new ArrayList<State>();
    private ArrayList<State> allStates = new ArrayList<State>();
    private ArrayList<Action> allActions = new ArrayList<Action>();
    private boolean foundGoals = false;

    public Planner(ArrayList<State> inicialStates, ArrayList<State> goalStates) {
        for (State state : inicialStates) {
            state.setLevel(0);
        }
        this.inicialStates = inicialStates;
        this.goalStates = goalStates;
        this.allStates = (ArrayList<State>) State.getAllStates().clone();
        this.allActions = (ArrayList<Action>) Action.getAllActions().clone();
    }

    public ArrayList<List<Action>> getPlan() {
        ArrayList<List<Action>> plan = null;
        // partindo dos estados iniciais
        int level = 0;
        ArrayList<State> actualStates = inicialStates;
        // incluir oposto de estados fora dos estados iniciais
        for (State state : getAllStates()) {
            boolean contains = false;
            for (State inicialState : getInicialStates()) {
                if (inicialState.getId().equals(state.getId()) || inicialState.getId().equals(state.oposto().getId())) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                State opositeState = state.oposto();
                opositeState.setLevel(0);
                actualStates.add(opositeState);
            }
        }
        // exibe estados
        print("# state-level " + level);
        for (State state : actualStates) {
            print(state.toString());
        }

        boolean stopCondition = false;
        while (!stopCondition) {
            // gerar ações possíveis
            level++;
            ArrayList<State> previewStateLevel = (ArrayList<State>) actualStates.clone();
            ArrayList<Action> nextActionLevel = getNextActionLevel(level, actualStates);
            // exibe ações
            print("# action-level " + level);
            for (Action action : nextActionLevel) {
                print(action.toString());
                // procura Mutex
                for (Action withAction : nextActionLevel) {
                    action.hasMutexWith(withAction);
                }
            }

            // próximos estados
            ArrayList<Action> previewActionLevel = (ArrayList<Action>) nextActionLevel.clone();
            ArrayList<State> nextStatesLevel = getNextStateLevel(level, nextActionLevel);
            print("# state-level " + level);
            for (State state : nextStatesLevel) {
                print(state.toString());
                for (State withState : nextStatesLevel) {
                    state.hasInconsistenceSupport(withState);
                }
            }

            actualStates = nextStatesLevel;

            // condição de gol ?
            if (goalCondition(actualStates)) {
                print(tabByLevel(level) + "- GOL?");
                ArrayList<State> goals = retrieveById(goalStates, actualStates);
                // gol?
                if (!hasMutexStates(goals)) {
                    plan = extractSolution(goals, new ArrayList<List<Action>>());
                }
            }

            // condição de parada ?
            if (stopCondition(previewStateLevel, actualStates, previewActionLevel, nextActionLevel)) {
                stopCondition = true;
            }

        }
        if (plan != null) {
            Collections.reverse(plan);
        }
        return plan != null ? (plan) : null;
    }

    private ArrayList<Action> getNextActionLevel(int level, ArrayList<State> previewStates) {
        ArrayList<Action> newLevelActions = new ArrayList<Action>();
        for (Action action : allActions) {
            // action é pré condição de estados anteriores?
            if (action.getPrecondition() != null && containsById(action.getPrecondition(), previewStates)) {
                Action newAction = new Action(action.getId());
                newAction.setEffects(action.getEffects());
                newAction.setPrecondition(action.getPrecondition());
                newAction.setLevel(level);
                newAction.setFromStates(retrieveById(action.getPrecondition(), previewStates));
                newLevelActions.add(newAction);
            }
        }
        // ações sem pré condições
        for (Action doAction : getAllActions()) {
            if (doAction.getPrecondition() == null) {
                Action newAction = doAction;
                newAction.setLevel(level);
                newLevelActions.add(newAction);
            }
        }
        // operação de manutenção
        for (State previewState : previewStates) {
            // todo estado anterior tem ação de manutenção
            Action noOpAction = new Action(previewState.getId());
            noOpAction.setIsNoOp(true);
            // uma NoOp action tem como precondição ela mesma?
            noOpAction.getPrecondition().add(previewState);
            noOpAction.getFromStates().add(previewState);
            noOpAction.setLevel(level);
            noOpAction.addEffect(previewState);
            newLevelActions.add(noOpAction);
        }
        return newLevelActions;
    }

    private ArrayList<State> getNextStateLevel(int level, ArrayList<Action> fromActions) {
        // gerar estados-efeitos
        ArrayList<State> newLevelStates = new ArrayList<State>();
        for (Action actionEffect : fromActions) {
            for (State effect : actionEffect.getEffects()) {
                // efeito já existe no nível?
                State exst = getByID(newLevelStates, effect.getId());
                State newLevelState = (exst == null) ? new State(effect.getId()) : exst;
                // define ação de origem do novo estado
                newLevelState.addFromAction(actionEffect);
                newLevelState.setLevel(level);
                if (exst == null) {
                    newLevelStates.add(newLevelState);
                }
            }
        }
        return newLevelStates;
    }

    private boolean goalCondition(ArrayList<State> actualStates) {
        return containsById(getGoalStates(), actualStates);
    }

    // estados estão contidos uma na outra
    private boolean containsById(ArrayList<State> itens1, ArrayList<State> itens2) {
        for (Mutexable item1 : itens1) {
            boolean exst = false;
            for (Mutexable item2 : itens2) {
                if (item1.getId().equals(item2.getId())) {
                    exst = true;
                    break;
                }
            }
            if (!exst) {
                return false;
            }
        }
        return true;
    }

    // ações estão contidas uma na outra
    private boolean containsActionsById(ArrayList<Action> actions1, ArrayList<Action> actions2) {
        for (Mutexable item1 : actions1) {
            boolean exst = false;
            for (Mutexable item2 : actions2) {
                if (item1.getId().equals(item2.getId())) {
                    exst = true;
                    break;
                }
            }
            if (!exst) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<State> retrieveById(ArrayList<State> itens1, ArrayList<State> itens2) {
        ArrayList<State> contains = new ArrayList<State>();
        for (State item1 : itens1) {
            for (State item2 : itens2) {
                if (item1.getId().equals(item2.getId())) {
                    contains.add(item2);
                    break;
                }
            }
        }
        return contains;
    }

    private State getByID(ArrayList<State> states, String id) {
        for (State state : states) {
            if (state.getId().equals(id)) {
                return state;
            }
        }
        return null;
    }

    private ArrayList<List<Action>> extractSolution(ArrayList<State> actualStates, ArrayList<List<Action>> takenActions) {
        if (actualStates.size() > 0) {
            int level = actualStates.get(0).getLevel();
            if (level == 0) {
                print("GOL!\n");
                return takenActions;
            }
            // pegar combinações das ações que as geraram        
            List<List<Action>> actionsLists = getActionsCombinations(actualStates);
            print(tabByLevel(level) + "- " + actionsLists.size() + " combinações possíveis");
            List<List<Action>> nonMutexActionLists = new ArrayList<List<Action>>();
            // filtra ações mutex
            nonMutexActionLists = getNonMutexActionLists(actionsLists);
            print(tabByLevel(level) + "- " + nonMutexActionLists.size() + " combinações válidas");
            if (nonMutexActionLists.isEmpty()) {
                return null;
            }
            // chegar em estados anteriores
            ArrayList<State> previewStates = new ArrayList<State>();
            for (List<Action> previewActions : nonMutexActionLists) {
                if (foundGoals) {
                    return takenActions; // comentar para retornar todas as soluções
                }
                previewStates.clear();
                for (Action previewAction : previewActions) {
                    previewStates.addAll(previewAction.getFromStates()); // #
                }

                if (!hasMutexStates(previewStates)) {
                    //System.out.println(previewActions);
                    //System.out.println(previewStates);
                    takenActions.add(previewActions);
                    ArrayList<List<Action>> extractedSolution = extractSolution(previewStates, takenActions);
                    if (extractedSolution != null && previewStates.get(0).getLevel() == 0) {
                        foundGoals = true;
                        return takenActions;
                    }
                }
            }
            return null;
        } else {
            return null;
        }
    }

    private boolean hasMutexStates(ArrayList<State> states) {
        for (State itemX : states) {
            for (State itemY : states) {
                if (itemX.isMutexWith(itemY)) {
                    //print(tabByLevel(itemX.getLevel()) + "- Não: " + itemX.getId() + " <M> " + itemY.getId());
                    return true;
                }
            }
        }
        return false;
    }

    private List<List<Action>> getNonMutexActionLists(List<List<Action>> actionsLists) {
        List<List<Action>> nonMutexActionLists = new ArrayList<List<Action>>();
        for (List<Action> actionsList : actionsLists) {
            boolean hasMutex = false;
            for (Action actionX : actionsList) {
                hasMutex = false;
                for (Action actionY : actionsList) {
                    // não pode haver ações mutex entre si                    
                    if (!actionX.equals(actionY) && actionX.isMutexWith(actionY)) {
                        hasMutex = true;
                        break;
                    }
                }
            }
            // lista válida
            if (!hasMutex) {
                nonMutexActionLists.add(actionsList);
            }
        }
        return nonMutexActionLists;
    }

    private List<List<Action>> getActionsCombinations(List<State> states) {
        List<List<Action>> list = new ArrayList<List<Action>>();
        for (State goal : states) {
            List<Action> fromActions = goal.getFromActions();
            if (fromActions.size() > 0) {
                list.add(fromActions);
            }
        }
        return list.size() > 0 ? combinate(list) : list;
    }

    private <T> int combinations(List<List<T>> list) {
        int count = 1;
        for (List<T> current : list) {
            count = count * current.size();
        }
        return count;
    }

    // se ultimos action e state levels forem iguais, pára.
    private boolean stopCondition(ArrayList<State> previewStateLevel, ArrayList<State> currentStates, ArrayList<Action> previewActionLevel, ArrayList<Action> currentActions) {
        boolean equals = false;
        if ((previewStateLevel.size() == currentStates.size()) && (previewActionLevel.size() == currentActions.size())) {
            if ((containsById(previewStateLevel, currentStates) && containsActionsById(previewActionLevel, currentActions))) {
                return true;
            }
        }
        return equals;
    }

    // retorna combinações entre listas
    private <T> List<List<T>> combinate(List<List<T>> uncombinedList) {
        List<List<T>> list = new ArrayList<List<T>>();
        int index[] = new int[uncombinedList.size()];
        int combinations = combinations(uncombinedList) - 1;
        // Initialize index
        for (int i = 0; i < index.length; i++) {
            index[i] = 0;
        }
        // First combination is always valid
        List<T> combination = new ArrayList<T>();
        for (int m = 0; m < index.length; m++) {
            combination.add(uncombinedList.get(m).get(index[m]));
        }
        list.add(combination);
        for (int k = 0; k < combinations; k++) {
            combination = new ArrayList<T>();
            boolean found = false;
            // We Use reverse order
            for (int l = index.length - 1; l >= 0 && found == false; l--) {
                int currentListSize = uncombinedList.get(l).size();
                if (index[l] < currentListSize - 1) {
                    index[l] = index[l] + 1;
                    found = true;
                } else {
                    // Overflow
                    index[l] = 0;
                }
            }
            for (int m = 0; m < index.length; m++) {
                combination.add(uncombinedList.get(m).get(index[m]));
            }
            list.add(combination);
        }
        return list;
    }

    public String tabByLevel(int level) {
        String tab = "";
        for (int x = 0; x < level; x++) {
            tab = tab + "\t";
        }
        return tab;
    }

    private void print(String str) {
        Screen.log(str);
    }

    public ArrayList<State> getInicialStates() {
        return inicialStates;
    }

    public ArrayList<Action> getAllActions() {
        return allActions;
    }

    public ArrayList<State> getGoalStates() {
        return goalStates;
    }

    public ArrayList<State> getAllStates() {
        return allStates;
    }

    public void setAllStates(ArrayList<State> allStates) {
        this.allStates = allStates;
    }
}
