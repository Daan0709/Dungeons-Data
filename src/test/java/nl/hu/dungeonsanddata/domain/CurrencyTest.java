package nl.hu.dungeonsanddata.domain;

import nl.hu.dungeonsanddata.domain.Character;
import nl.hu.dungeonsanddata.domain.Currency;
import nl.hu.dungeonsanddata.domain.WrongTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {
    private Character c1;

    @BeforeEach
    public void init() throws WrongTypeException {
        c1 = new Character("John", "High Elf", 300, 17, "Ranger", 3);
    }

    @Test
    public void testAantalOnder0False(){
        /**
         * Het aantal van een currency mag niet onder de 0 vallen.
         */
        Currency gold = c1.getSpecificCurrency("Gold");

        c1.addCurrency("Gold", -5);
        assertFalse(gold.getAantal() < 0);

        c1.decreaseCurrency("Gold", -5);
        assertFalse(gold.getAantal() < 0);
    }
}
