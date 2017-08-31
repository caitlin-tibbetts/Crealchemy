package com.example.caitlin.crealchemy;

import android.annotation.SuppressLint;

import java.util.ArrayList;

class AchievementList extends ArrayList<Achievement> {
    private ElementList list;

    @SuppressLint("DefaultLocale")
    AchievementList(ElementList list){
        this.list = list;
        for(GroupType g : GroupType.values()){
            if(allElementsInGroupDiscovered(g)){
                this.add(new Achievement(String.format("Discovered all of the Elements from %1$s group!", g.toString()), String.format("allDiscovered_%1$s", g.toString())));
            }
        }
        int sequence[] = {1, 5, 10, 20, 50, 100, 200, 500, 1000, 5000, 10000};
        for(int a : sequence){
            if(a < numberOfElementsCreated()){
                if(a == 1){
                    this.add(new Achievement("Created your first Element!", "firstElement"));
                } else this.add(new Achievement(String.format("Created %1$d Elements", a), String.format("created_%1$d", a)));
            }
        }
        for(int a : sequence){
            if(a < numberOfElementsDiscovered()){
                if(a == 1){
                    this.add(new Achievement("Found your first Element!", "foundFirstElement"));
                } else this.add(new Achievement(String.format("Found %1$d Elements", a), String.format("found_%1$d", a)));
            }
        }
        if(allElementsDiscovered()){
            this.add(new Achievement("Discovered all of the Elements", "allDiscovered"));
        }
    }

    private boolean allElementsDiscovered() {
        for(Element e : list){
            if(e.getIsFound() == 0){
                return false;
            }
        }
        return true;
    }

    private boolean allElementsInGroupDiscovered(GroupType g){
        for(Element e : list){
            if(e.getGroup().equals(g) && e.getIsFound() == 0){
                return false;
            }
        }
        return true;
    }

    private int numberOfElementsCreated(){
        int ret = 0;
        for(Element e : list){
            if(e.getWasCreated() == 1){
                ret++;
            }
        }
        return ret;
    }

    private int numberOfElementsDiscovered(){
        int ret = 0;
        for(Element e : list){
            if(e.getIsFound() == 1) ret++;
        }
        return ret;
    }

}
