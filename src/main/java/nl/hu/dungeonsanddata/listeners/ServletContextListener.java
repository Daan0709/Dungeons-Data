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
            c1.getSpecificSpellslot(1).setMaxAmount(4);
            c1.getSpecificSpellslot(2).setMaxAmount(2);
            c1.getSpecificSpellslot(3).setMaxAmount(1);
            c1.getSpecificSpellslot(1).useSpellslot();
            c1.getSpecificSpellslot(1).useSpellslot();
            c1.getSpecificSpellslot(2).useSpellslot();
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
