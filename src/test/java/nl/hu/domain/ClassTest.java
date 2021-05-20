package nl.hu.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassTest {
    private Class c1;
    private Class c2;

    @Test
    public void testForAvailableTypes(){
        /**
         * Een Class mag maar één van de volgende 12 typen zijn:
         * Barbarian, Bard, Cleric, Druid, Fighter, Monk, Paladin,
         * Ranger, Rogue, Sorcerer, Warlock of Wizard.
         */
        try {
            c1 = new Class("Ranger");
            c2 = new Class("Ranggerr");

            assertTrue(c2 == null && c1.getType().equals("Ranger"));
        } catch (WrongTypeException e){
            assertTrue(c2 == null && c1.getType().equals("Ranger"));
        }
    }
}