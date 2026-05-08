/*
 * State.java
 *
 * Autor: Rafael Polo
 * Date: Julho 2011
 */

package com.extrapolo.graphplan.model;

import com.extrapolo.graphplan.view.Screen;
import java.util.ArrayList;

public class State extends Mutexable {

    private static ArrayList<State> allStates = new ArrayList<State>();
    private ArrayList<Action> fromActions = new ArrayList<Action>();
    
    public State(String id) {
        setId(id);
        boolean contains = false;
        for (State state : allStates){
            if (state.getId().equals(id)){
                contains = true;
                break;
            }
        }
        if (!contains) {
            allStates.add(this);
        }
    }

    @Override
    public String toString() {
        return (tabByLevel() + "> estado: " + getId() + " [" + fromActions.size() + "]");
    }

    // Mutex: Inconsistence Support
    public boolean hasInconsistenceSupport(State state) {
        // não é reflexivo
        if (getId().equals(state.getId())) {
            return false;
        }
        // negação
        if (getId().equals(state.oposto().getId())) {
            setMutexWith(state);
            Screen.logMutex(tabByLevel() + "} inconsistentSupport -> " + state.getId() + " | negation");
            return true;
        }
        // ações de origem são mutex entre si
        for (Action thisFromAction : getFromActions()) {
            if (thisFromAction.isMutex()) {
                // derivam de ações com mutex entre si?
                for (Action stateFromAction : state.getFromActions()) {
                    if (!stateFromAction.isMutexWith(thisFromAction)) {
                        return false;
                    }
                }
                setMutexWith(state);
                Screen.logMutex(tabByLevel() + "} inconsistentSupport -> " + state.getId());
                for (Action stateFromAction : state.getFromActions()) {
                    if (!thisFromAction.equals(stateFromAction)) {
                       Screen.logMutex(tabByLevel() + "}} " + thisFromAction.getId() + " <-> " + stateFromAction.getId());
                    }
                }
            }
        }
        return false;
    }

    public State oposto() {
        String opId = getId().startsWith("!") ? getId().replace("!", "") : "!" + getId();
        return new State(opId);
    }
    
    public boolean isNegativo() {
        return this.getId().startsWith("!");
    }
    public static void reset() {
        allStates.clear();
    }   
    
    public static State addOrCreate(String id) {
        int index = getAllStates().indexOf(id);
        return index < 0 ? new State(id) : getAllStates().get(getAllStates().indexOf(id));
    }

    public static ArrayList<State> getAllStates() {
        return allStates;
    }

    public void addFromAction(Action action) {
        fromActions.add(action);
    }
    
    public ArrayList<Action> getFromActions() {
        return fromActions;
    }

    public void setFromActions(ArrayList<Action> fromActions) {
        this.fromActions = fromActions;
    }
}
