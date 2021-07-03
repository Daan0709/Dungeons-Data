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
            Account a1 = new Account("test@test.nl", "test");
            Character c1 = new Character("Bardin", "Dwarf", 2700, 22, "Ranger");
            Spellslot ss1 = new Spellslot(1, 4);
            Spellslot ss2 = new Spellslot(2, 2);
            Spellslot ss3 = new Spellslot(3, 1);
            ss1.useSpellslot();
            ss2.useSpellslot();
            ss2.useSpellslot();
            c1.addSpellslot(ss1);
            c1.addSpellslot(ss2);
            c1.addSpellslot(ss3);
            a1.addCharacter(c1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down...");
        try {
            for (Account account : Account.getAllAccounts()){
                System.out.println(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
