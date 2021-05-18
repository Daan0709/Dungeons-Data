package nl.hu;

import nl.hu.domain.*;
import nl.hu.domain.Character;
import nl.hu.domain.Class;

public class Main {
    public static void main(String[] args) {
        Class ranger = new Class("Ranger");
        Character c1 = new Character("Daan", "High Elf", 9999999,16, ranger, 3);
        c1.setStat("Strength", 15);
        c1.setStat("Dexterity", 23);
        for (Stat stat : c1.getStats()){
            System.out.println(stat);
        }
        System.out.println(c1.getMaxGewicht());
    }
}
