package nl.hu.dungeonsanddata.listeners;

import nl.hu.dungeonsanddata.domain.Account;
import nl.hu.dungeonsanddata.domain.Character;
import nl.hu.dungeonsanddata.domain.WrongTypeException;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce){
        System.out.println("Starting...");
        Account a1 = new Account("hoi@hoi.nl", "wachtwoord");
        try {
            Character c1 = new Character("John", "High Elf", 500, "Ranger", 3);
            a1.addCharacter(c1);
            Character c2 = new Character("Kees", "High Elf", 23, "Barbarian", 0);
            a1.addCharacter(c2);
        } catch (WrongTypeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down...");
    }
}
