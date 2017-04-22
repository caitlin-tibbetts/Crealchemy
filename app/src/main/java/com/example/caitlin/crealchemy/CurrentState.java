package com.example.caitlin.crealchemy;

import android.app.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CurrentState extends Application{

    private Element element;
    private ElementList elementList;
    private ArrayList<Element> toDelete;
    private AchievementList achievementList;

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public ElementList getElementList() {
        return elementList;
    }

    public void setElementList(ElementList elementList) {
        this.elementList = elementList;
    }

    public ArrayList<Element> getToDelete() {
        return toDelete;
    }

    public void setToDelete(ArrayList<Element> toDelete) {
        this.toDelete = toDelete;
    }

    public AchievementList getAchievementList() {
        return achievementList;
    }

    public void setAchievementList(AchievementList achievementList) {
        this.achievementList = achievementList;
    }
}
