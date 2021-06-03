package nl.hu.dungeonsanddata.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account a1;
    Character c1;

    @BeforeEach
    public void init() throws WrongTypeException {
        a1 = new Account("test@email.nl", "wachtwoord");
        c1 = new Character("John", "High Elf", 18, "Cleric", 5);
    }

    @Test
    public void testAddCharacterToAccount(){
        a1.addCharacter(c1);
        ArrayList<Character> characters = a1.getCharacters();
        assertEquals(characters.get(0), c1);
    }
}