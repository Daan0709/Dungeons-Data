package nl.hu.domain;

import java.util.ArrayList;
import java.util.Arrays;

public class Class {
    private String type;

    public Class(String type) throws WrongTypeException {
        ArrayList<String> availableTypes = new ArrayList<>(Arrays.asList("Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard"));

            if (availableTypes.contains(type)) {
                this.type = type;
            } else {
                throw new WrongTypeException("This is not an available class.");
            }
    }

    public String getType(){
        return type;
    }

    public String toString(){
        return type;
    }
}
