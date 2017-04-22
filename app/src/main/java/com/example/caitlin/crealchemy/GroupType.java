package com.example.caitlin.crealchemy;

public enum GroupType {

    DEFAULT, DAY_AND_NIGHT, FIRE, GEOLOGICAL_EVENT, HUMANITY, LIFE, OUT_OF_SIGHT, OUTER_SPACE, ROCK, WATER_AND_ICE, WEATHER;

    public static GroupType getGroup(String groupName) {
        switch(groupName){
            case "Default": return DEFAULT;
            case "Day and Night": return DAY_AND_NIGHT;
            case "Fire": return FIRE;
            case "Geological Event": return GEOLOGICAL_EVENT;
            case "Humanity": return HUMANITY;
            case "Life": return LIFE;
            case "Out of Sight": return OUT_OF_SIGHT;
            case "Outer Space": return OUTER_SPACE;
            case "Rock": return ROCK;
            case "Water and Ice": return WATER_AND_ICE;
            case "Weather": return WEATHER;
        }
        return null;
    }

    public boolean equals(GroupType other){
        try{
            return this.toString().equals(other.toString());
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }

    public String toString() {
        switch(this){
            case DEFAULT: return "Default";
            case DAY_AND_NIGHT: return "Day and Night";
            case FIRE: return "Fire";
            case GEOLOGICAL_EVENT: return "Geological Event";
            case HUMANITY: return "Humanity";
            case LIFE: return "Life";
            case OUT_OF_SIGHT: return "Out of Sight";
            case OUTER_SPACE: return "Outer Space";
            case ROCK: return "Rock";
            case WATER_AND_ICE: return "Water and Ice";
            case WEATHER: return "Weather";
        }
        return null;
    }
}
