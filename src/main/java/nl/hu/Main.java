package nl.hu;

import nl.hu.domain.Character;
import nl.hu.domain.Class;

public class Main {
    public static void main(String[] args) {
        Class ranger = new Class("Ranger");
        Character c1 = new Character("Daan", "High Elven", 350,16, ranger);

        System.out.println(String.format("%s the %s %s with %sxp and %shp", c1.getNaam(), c1.getRace(), c1.getKlasse(), c1.getExperience(), c1.getHitpoints()));
        System.out.println(c1.calculateTotalGold());
    }
}
