/*
 * Action.java
 *
 * Autor: Rafael Polo
 * Date: Julho 2011
 */

package com.extrapolo.graphplan.model;

import com.extrapolo.graphplan.view.Screen;
import java.util.ArrayList;

public class Action extends Mutexable {

    private static ArrayList<Action> allActions = new ArrayList<Action>();
    private ArrayList<State> precondition = new ArrayList<State>();
    private ArrayList<State> effects = new ArrayList<State>();
    private ArrayList<State> fromStates = new ArrayList<State>();
    private boolean isNoOp = false;

    public Action(String id) {
        setId(id);
        allActions.add(this);
    }

    @Override
    public String toString() {
        String noOp = isNoOp ? "noOp " : "";
        return (tabByLevel() + "> " + noOp + "action: " + getId());
    }

    public boolean hasMutexWith(Action action) {
        // não é reflexivo
        if (getId().equals(action.getId())) {
            return false;
        }
        boolean hasInconsistentEffect = hasInconsistentEffect(action);
        boolean hasInterference = hasInterference(action);
        boolean hasCompetingNeeds = hasCompetingNeeds(action);
        return hasInconsistentEffect || hasInterference || hasCompetingNeeds;
    }

    // Mutex: Inconsistent Effects
    private boolean hasInconsistentEffect(Action action) {
        for (State thisEffect : getEffects()) {
            for (State actionEffect : action.getEffects()) {
                if (thisEffect.getId().equals(actionEffect.oposto().getId())) {
                    setMutexWith(action);
                    action.setMutexWith(this);
                    String noOp = isNoOp ? "noOp " : "";
                   Screen.logMutex(tabByLevel() + "} inconsistentEffect -> " + noOp + action.getId());
                    return true;
                }
            }
        }
        return false;
    }

    // Mutex: Interference
    private boolean hasInterference(Action action) {
        for (State thisEffect : getEffects()) {
            for (State actionPrecondition : action.getPrecondition()) {
                if (thisEffect.getId().equals(actionPrecondition.oposto().getId())) {
                    setMutexWith(action);
                    action.setMutexWith(this);
                    Screen.logMutex(tabByLevel() + "} interference -> " + action.getId());
                    return true;
                }
            }
        }
        return false;
    }

    // Mutex: Competing Needs
    private boolean hasCompetingNeeds(Action action) {
        for (State thisPrecondition : getPrecondition()) {
            for (State actionPrecondition : action.getPrecondition()) {
                if (thisPrecondition.getId().equals(actionPrecondition.oposto().getId())) {
                    setMutexWith(action);
                    action.setMutexWith(this);
                    Screen.logMutex(tabByLevel() + "} competingNeeds -> " + action.getId());
                    return true;
                }
            }
        }
        return false;
    }

    public static Action addOrCreate(String id) {
        int index = getAllActions().indexOf(id);
        return index < 0 ? new Action(id) : getAllActions().get(getAllActions().indexOf(id));
    }
    
    public static void reset() {
        allActions.clear();
    }    

    public ArrayList<State> getPrecondition() {
        return precondition;
    }

    public void setPrecondition(ArrayList<State> precondition) {
        this.precondition = precondition;
    }

    public ArrayList<State> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<State> efects) {
        this.effects = efects;
    }

    public ArrayList<State> getFromStates() {
        return fromStates;
    }

    public void setFromStates(ArrayList<State> fromStates) {
        this.fromStates = fromStates;
    }

    public boolean isIsNoOp() {
        return isNoOp;
    }

    public void setIsNoOp(boolean isNoOp) {
        this.isNoOp = isNoOp;
    }

    public static ArrayList<Action> getAllActions() {
        return allActions;
    }

    public void addEffect(State previewState) {
        for (State effect : getEffects()) {
            if (effect.getId().equals(previewState.getId())) {
                return;
            }
        }
        State effectState = new State(previewState.getId());
        effectState.setLevel(previewState.getLevel() + 1);
        getEffects().add(previewState);
    }
}
