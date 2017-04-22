package com.example.caitlin.crealchemy;

import java.util.ArrayList;

public class Element {

    private String name;
    private GroupType group;
    private int isFound;
    private ArrayList<Element[]> parents;
    private int wasCreated;

    private static final String NAME_WATER = "Water";
    private static final String NAME_FIRE = "Fire";
    private static final String NAME_AIR = "Air";
    private static final String NAME_EARTH = "Earth";

    public Element(String name, GroupType group){
        this.name = name;
        this.group = group;
        this.isFound = 0;
        this.parents = new ArrayList<>();
        this.wasCreated = 0;
    }

    public Element(String name, GroupType group, ArrayList<Element[]> parents){
        this.name = name;
        this.group = group;
        this.isFound = 0;
        this.parents = parents;
    }

    public Element(String name, GroupType group, Element parent1, Element parent2){
        this.name = name;
        this.group = group;
        this.isFound = 0;
        Element[] temp = {parent1, parent2};
        this.parents = new ArrayList<>();
        this.parents.add(temp);
    }

    public Element(String name, GroupType group, int isFound){
        this.name = name;
        this.group = group;
        if(isFound == 0 || isFound == 1){
            this.isFound = isFound;
        } else this.isFound = 0;
        this.parents = new ArrayList<>();
    }

    public Element(String name, GroupType group, boolean isFound){
        this.name = name;
        this.group = group;
        if(isFound){
            this.isFound = 1;
        } else this.isFound = 0;
        this.parents = new ArrayList<>();
    }

    public Element(String name, GroupType group, int isFound, ArrayList<Element[]> parents){
        this.name = name;
        this.group = group;
        if(isFound == 0 || isFound == 1){
            this.isFound = isFound;
        } else this.isFound = 0;
        this.parents = parents;
    }

    public Element(String name, GroupType group, boolean isFound, ArrayList<Element[]> parents){
        this.name = name;
        this.group = group;
        if(isFound){
            this.isFound = 1;
        } else this.isFound = 0;
        this.parents = parents;
    }

    public Element(String name, GroupType group, boolean isFound, Element parent1, Element parent2, int wasCreated){
        this.name = name;
        this.group = group;
        if(wasCreated == 0 || wasCreated == 1){
            this.wasCreated = wasCreated;
        } else this.wasCreated = 0;
        this.wasCreated = wasCreated;
        if(isFound){
            this.isFound = 1;
        } else this.isFound = 0;
        Element[] temp = {parent1, parent2};
        this.parents = new ArrayList<>();
        this.parents.add(temp);
    }

    public Element(String name, GroupType group, boolean isFound, Element parent1, Element parent2, boolean wasCreated){
        this.name = name;
        this.group = group;
        if(wasCreated){
            this.wasCreated = 1;
        } else this.wasCreated = 0;
        if(isFound){
            this.isFound = 1;
        } else this.isFound = 0;
        Element[] temp = {parent1, parent2};
        this.parents = new ArrayList<>();
        this.parents.add(temp);
    }

    public Element(String name, GroupType group, int isFound, Element parent1, Element parent2){
        this.name = name;
        this.group = group;
        if(isFound == 0 || isFound == 1){
            this.isFound = isFound;
        } else this.isFound = 0;
        Element[] temp = {parent1, parent2};
        this.parents = new ArrayList<>();
        this.parents.add(temp);
    }

    public Element(String name, GroupType group, int isFound, int wasCreated) {
        this.name = name;
        this.group = group;
        if(isFound == 0 || isFound == 1){
            this.isFound = isFound;
        } else this.isFound = 0;
        if(wasCreated == 0 || wasCreated == 1){
            this.wasCreated = wasCreated;
        } else this.wasCreated = 0;
        this.parents = new ArrayList<>();
    }

    public Element(String name, GroupType group, boolean isFound, ArrayList<Element[]> parents, boolean wasCreated) {
        this.name = name;
        this.group = group;
        if(isFound){
            this.isFound = 1;
        } else this.isFound = 0;
        if(wasCreated){
            this.wasCreated = 1;
        } else this.wasCreated = 0;
        this.parents = parents;
    }

    public String getName(){
        return this.name;
    }

    public String setName(String name){
        this.name = name;
        return this.name;
    }

    public GroupType getGroup() {
        return group;
    }

    public GroupType setGroup(GroupType group) {
        this.group = group;
        return this.group;
    }

    public GroupType setGroup(String group){
        this.group = GroupType.getGroup(group);
        return this.group;
    }

    public int getIsFound(){
        return isFound;
    }

    public int setIsFound(int isFound){
        if(isFound == 0 || isFound == 1){
            this.isFound = isFound;
        } else this.isFound = 0;
        return this.isFound;
    }

    public int setIsFound(boolean b){
        if(b){
            this.isFound = 1;
        } else this.isFound = 0;
        return this.isFound;
    }

    public Element[] getParents(int index){
        return parents.get(index);
    }

    public ArrayList<Element[]> getAllParents(){
        return parents;
    }

    public void addParents(Element parent1, Element parent2){
        Element[] x = {parent1, parent2};
        if(parent1 != null && parent2 != null && x != null){
            parents.add(x);
        }
    }

    public void addParents(Element[] setParents){
        parents.add(setParents);
    }

    public void addAllParents(ArrayList<Element[]> setParents){
        for(Element[] e : setParents){
            addParents(e);
        }
    }

    public int getWasCreated() {
        return wasCreated;
    }

    public int setWasCreated(int wasCreated){
        if(wasCreated == 1 || wasCreated == 0){
            this.wasCreated = wasCreated;
        } else this.wasCreated = 0;
        return this.wasCreated;
    }

    public int setWasCreated(boolean b) {
        if(b){
            this.wasCreated = 1;
        } else this.wasCreated = 0;
        return this.wasCreated;
    }

    public boolean hasParents(){
        return (parents == null) || (parents.size() > 0 && parents != null) || name.equals(NAME_FIRE) || name.equals(NAME_WATER) || name.equals(NAME_EARTH) || name.equals(NAME_AIR);
    }

    public boolean hasName(){
        return name.length() > 0;
    }

    @Override
    public String toString(){
        return name + "\n";
    }

    @Override
    public boolean equals(Object object){
        return !(object==null) && (object instanceof Element && this.getName().compareTo(((Element) object).getName()) == 0);
    }
}
