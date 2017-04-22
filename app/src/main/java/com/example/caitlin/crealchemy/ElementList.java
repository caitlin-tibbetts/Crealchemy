package com.example.caitlin.crealchemy;

import java.util.ArrayList;

public class ElementList extends ArrayList<Element>{

    public int indexOf(Element other){
        for(int k = 0; k < this.size(); k++){
            if(this.get(k).equals(other)){
                return k;
            }
        }
        return -1;
    }

    public int indexOf(String otherName){
        for(int k = 0; k < this.size(); k++){
            if(this.get(k).getName().equals(otherName)){
                return k;
            }
        }
        return -1;
    }

    public void sort() {
        int i, j;
        Element temp;
        for (i = 0; i < this.size() - 1; i++) {
            for (j = i + 1; j < this.size(); j++) {
                if (this.get(i).getName()
                        .compareToIgnoreCase(this.get(j).getName()) > 0) {
                    temp = this.get(i);
                    this.set(i, this.get(j));
                    this.set(j, temp);

                }
            }
        }
    }

    @Override
    public String toString(){
        String ret = "";
        for(Element e : this){
            ret += e.toString() + "\n";
        }
        return ret;
    }
}
