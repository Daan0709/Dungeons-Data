package nl.hu.dungeonsanddata.listeners;

import nl.hu.dungeonsanddata.domain.Account;
import nl.hu.dungeonsanddata.domain.Character;
import nl.hu.dungeonsanddata.domain.WrongTypeException;
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
            Character c3 = new Character("Bardin", "Dwarf", 40, "Barbarian", 0);
            a2.addCharacter(c3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down...");
        try {
            for (Account a : PersistenceManager.loadAccountsFromAzure()){
                System.out.println(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
