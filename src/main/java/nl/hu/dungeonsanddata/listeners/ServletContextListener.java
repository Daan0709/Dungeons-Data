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
            PersistenceManager.loadAccountsFromAzure();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down...");
        try {
            PersistenceManager.saveAccountsToAzure();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
