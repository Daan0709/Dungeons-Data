package nl.hu.dungeonsanddata.domain;

import nl.hu.dungeonsanddata.domain.*;
import nl.hu.dungeonsanddata.domain.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    private Character c1;
    private Character c2;

    @BeforeEach
    public void init() throws WrongTypeException {
        c1 = new Character("John", "High Elf", 300, 17, "Ranger", 3);
    }

    @Test
    public void testCharStats(){
        /**
         * Test op aanwezigheid van alle 6 de core stats:
         * Strength, Charisma, Intelligence, Dexterity,
         * Wisdom en Constitution.
         */
        Stat strength = new Stat("Strength");
        Stat charisma = new Stat("Charisma");
        Stat intelligence = new Stat("Intelligence");
        Stat dexterity = new Stat("Dexterity");
        Stat wisdom = new Stat("Wisdom");
        Stat constitution = new Stat("Constitution");

        assertFalse(!c1.getStats().contains(strength) ||
                !c1.getStats().contains(charisma) ||
                !c1.getStats().contains(intelligence) ||
                !c1.getStats().contains(dexterity) ||
                !c1.getStats().contains(wisdom) ||
                !c1.getStats().contains(constitution));
    }

    @Test
    public void testCharCurrencies(){
        /**
         * Test op aanwezigheid van alle 4 de typen currencies:
         * Platinum, Gold, Silver, Copper.
         */
        Currency platinum = new Currency("Platinum");
        Currency gold = new Currency("Gold");
        Currency silver = new Currency("Silver");
        Currency copper = new Currency("Copper");

        assertFalse(!c1.getCurrency().contains(platinum) ||
                        !c1.getCurrency().contains(gold) ||
                        !c1.getCurrency().contains(silver) ||
                        !c1.getCurrency().contains(copper));
    }

    @Test
    public void testWaardePlatinum(){
        /**
         * Test de waarde van Platinum = 10 gold.
         */
        c1.addCurrency("Platinum", 10);
        assertEquals(100, c1.calculateTotalGold());
    }

    @Test
    public void testWaardeGold(){
        /**
         * Test de waarde van gold = 1 gold.
         */
        c1.addCurrency("Gold", 10);
        assertEquals(10, c1.calculateTotalGold());
    }

    @Test
    public void testWaardeSilver(){
        /**
         * Test de waarde van Silver = 0.1 gold.
         */
        c1.addCurrency("Silver", 10);
        assertEquals(1, c1.calculateTotalGold());
    }

    @Test
    public void testWaardeCopper(){
        /**
         * Test de waarde van Copper = 0.01 gold.
         */
        c1.addCurrency("Copper", 10);
        assertEquals(0.1, c1.calculateTotalGold());
    }

    @Test
    public void testStatModifier() throws Exception {
        /**
         * Test of de modifier van de stats goed worden ingesteld:
         * Modifier = (Score - 10) / 2
         * Dit wordt naar beneden afgerond
         */
        c1.setStat("Intelligence", 25);
        c1.setStat("Charisma", 15);
        c1.setStat("Strength", 5);

        assertEquals(7, c1.getSpecificStat("Intelligence").getModifier());
        assertEquals(2, c1.getSpecificStat("Charisma").getModifier());
        assertEquals(-2, c1.getSpecificStat("Strength").getModifier());
    }

    @Test
    public void testAantalHitpointsOnder0(){
        /**
         * Het aantal hitpoints van een character kan niet
         * onder de 0 vallen.
         * Hitpoints van c1 = 17.
         */
        c1.decreaseHitpoints(17);
        assertEquals(0, c1.getHitpoints());
        c1.decreaseHitpoints(1);                // Verlaag hitpoints onder 0
        assertEquals(0, c1.getHitpoints());
    }

    @Test
    public void testAantalHitpointsBovenMaxHitpoints(){
        /**
         * Het aantal hitpoints van een character kan niet
         * boven de waarde van maxHitpoints vallen.
         * maxHitpoints van c1 = 17/
         */
        c1.addHitpoints(1);
        assertEquals(17, c1.getHitpoints());
    }

    @Test
    public void testAantalSpellslotsOnder0(){
        /**
         * Het aantal spellslots van een character kan niet
         * lager zijn dan 0.
         */
        c1.setMaxSpellslots(-1);
        assertEquals(3, c1.getMaxSpellslots());
        c1.setMaxSpellslots(0);
        assertEquals(0, c1.getMaxSpellslots());
    }

    @Test
    public void testAantalVerbruikteSpellsBovenMaxAantalSpellslots(){
        /**
         * Het aantal verbruikte spellslots mag niet
         * boven het maximum aantal spellslots van
         * een character komen.
         * maxSpellslots van c1 = 3.
         */
        for (int i = 0; i < c1.getMaxSpellslots(); i++){
            c1.useSpellslot();
        }
        c1.useSpellslot(); // 4e keer verbruik spellslot

        assertEquals(3, c1.getVerbruikteSpellslots());
    }

    @Test
    public void testMaxGewichtWaarde() throws Exception {
        /**
         * Het maximum gewicht van een character
         * is berekend door de score van zijn
         * "Strength" stat te verminigvuldigen
         * met 15.
         */
        c1.setStat("Strength", 10);

        assertEquals(150, c1.getMaxGewicht());
    }

    @Test
    public void testGewichtItemsMagNietBovenMaxGewicht() throws Exception {
        /**
         * Het gecombineerde gewicht van alle items
         * mogen niet boven het maxGewicht van een
         * character komen.
         */
        Item i1 = new Item("Spear", "A pointy stick", 15);
        c1.setStat("Strength", 10);
        c1.addItem(i1);

        assertEquals(15, c1.getCurrentWeight());

        c1.increaseItemAmount(i1, 10);

        assertEquals(15, c1.getCurrentWeight());
    }

    @Test
    public void testSetInvalidClass(){
        /**
         * Het character mag alleen van
         * een van de 12 geldige classes
         * zijn: Barbarian, Bard, Cleric,
         * Druid, Fighter, Monk, Paladin,
         * Ranger, Rogue, Sorcerer, Warlock
         * of Wizard.
         */
        try {
            c1.setKlasse("Rrangger");
            assertEquals("Ranger", c1.getKlasse().getType());
        } catch (WrongTypeException wte){
            assertEquals("Ranger", c1.getKlasse().getType());
        }
    }

    @Test
    public void testCharacterAanmakenInvalidClass() {
        try {
            c2 = new Character("Jack", "Wood Elf", 300, 17, "Rrangger", 3);
            assertTrue(c2 == null);
        } catch (WrongTypeException wte){
            assertTrue(c2 == null);
        }
    }
}
