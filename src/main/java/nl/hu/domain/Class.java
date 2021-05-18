package nl.hu.domain;

import java.util.ArrayList;
import java.util.Arrays;

public class Class {
    private String type;

    public Class(String type) {
        ArrayList<String> availableTypes = new ArrayList<>(Arrays.asList("Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard"));
        try {
            if (availableTypes.contains(type)) {
                this.type = type;
            } else {
                throw new WrongTypeException("This is not an available class.");
            }
        } catch (WrongTypeException wte){
            wte.printStackTrace();
        }
    }

    public String toString(){
        return type;
    }
}
