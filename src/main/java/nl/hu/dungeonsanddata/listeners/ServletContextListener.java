package nl.hu.dungeonsanddata.listeners;

import nl.hu.dungeonsanddata.domain.*;
import nl.hu.dungeonsanddata.domain.Character;
import nl.hu.dungeonsanddata.persistence.PersistenceManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce){
        System.out.println("Starting...");
        try {
            Account a1 = new Account("hoi@hoi.nl", "wachtwoord");
            Account a2 = new Account("test@test.nl", "test");
            Character c1 = new Character("John", "High Elf", 500, "Ranger", 3);
            a1.addCharacter(c1);
            Character c2 = new Character("Kees", "High Elf", 23, "Rogue", 0);
            a1.addCharacter(c2);
            Character c3 = new Character("Bardin", "Dwarf", 40, "Cleric", 4);
            a2.addCharacter(c3);
            c3.setStat("Strength", 20);
            c3.setStat("Dexterity", 19);
            c3.setStat("Constitution", 17);
            c3.setStat("Intelligence", 18);
            c3.setStat("Wisdom", -5);
            c3.setStat("Charisma", 16);
            c3.addCurrency("Platinum", 5);
            c3.addCurrency("Silver", 575);
            Item i1 = new Item("Spear", "A pointy stabby wooden spear", 5);
            c3.addItem(i1);
            Item i2 = new Item("Bow", "Big bow boy", 10);
            c3.addItem(i2);
            Skill s1 = new Skill("Nightvision", "See far in the dark");
            c3.addSkill(s1);
            Spell sp1 = new Spell("Goodberry", "Summon a hand full of goodberries", "5h");
            c3.addSpell(sp1);
            c3.useSpellslot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down...");
    }
}
