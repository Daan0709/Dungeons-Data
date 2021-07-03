package nl.hu.dungeonsanddata.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpellslotTest {
    Spellslot ss1;
    Spellslot ss2;
    Character c1;

    @BeforeEach
    public void init() throws WrongTypeException {
        c1 = new Character("John", "High Elf", 300, 17, "Ranger");
        ss1 = new Spellslot(1, 3);
        ss2 = new Spellslot(2, 2);
        c1.addSpellslot(ss1);
        c1.addSpellslot(ss2);
    }

    @Test
    public void testAmountOfSpellslotBelow0(){
        /**
         * If the max amount of a spellslot is set below 0,
         * it should become 0 instead.
         */
        ss1.setMaxAmount(-2);
        assertEquals(0, ss1.getMaxAmount());
    }

    @Test
    public void testUsedAmountAboveMaxAmount(){
        /**
         * The used amount of a spellslot cannot go above the max amount,
         * it should stop at the max amount instead.
         */
        ss2.useSpellslot();
        ss2.useSpellslot();
        ss2.useSpellslot();
        ss2.useSpellslot();
        assertEquals(2, ss2.getUsedAmount());
    }

    @Test
    public void testSetMaxAmountBelowUsedAmount(){
        /**
         * If the max amount is a number below the used amount, the used
         * amount should lower itself to the max amount.
         */
        ss2.useSpellslot();
        ss2.useSpellslot();
        ss2.setMaxAmount(0);
        assertEquals(0, ss2.getUsedAmount());
    }
}
