/*
 * Mutexable.java
 *
 * Autor: Rafael Polo
 * Date: Julho 2011
 */

package com.extrapolo.graphplan.model;

import java.util.ArrayList;

public abstract class Mutexable {

    private String id;
    private Integer level;
    private ArrayList<Mutexable> mutexWith = new ArrayList<Mutexable>();

    public boolean isMutex(){
        return getMutexWith().size()>0;
    }
    public boolean isMutexWith(Mutexable item){
        return getMutexWith().contains(item);
    }
    
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String tabByLevel() {
        String tab = "";
        for (int x = 0; x < level; x++) {
            tab = tab + "\t";
        }
        return tab;
    }
    public ArrayList<Mutexable> getMutexWith() {
        return mutexWith;
    }
    
    public void setMutexWith(Mutexable item) {
        if (!mutexWith.contains(item)){
            mutexWith.add(item);
        }
    }
}
