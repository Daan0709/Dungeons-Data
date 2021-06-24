package nl.hu.dungeonsanddata.domain;

import nl.hu.dungeonsanddata.persistence.PersistenceManager;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;

public class Account implements Serializable, Principal {
    private String email;
    private String wachtwoord;
    private int accountId;
    private String role;
    private ArrayList<Character> characters = new ArrayList<>();
    private static int accountAmount;
    private static ArrayList<Account> allAccounts = new ArrayList<>();

    public Account(String email, String wachtwoord) throws Exception {
        this.email = email;
        this.wachtwoord = wachtwoord;
        accountAmount += 1;
        this.accountId = accountAmount;
        this.role = "user";
        allAccounts.add(this);
        PersistenceManager.saveAccountsToAzure();
    }

    public String getRole() {
        return role;
    }

    public static boolean isValid(String email, String wachtwoord) throws Exception {
        for (Account account : PersistenceManager.loadAccountsFromAzure()){
            if (account.email.equals(email) && account.wachtwoord.equals(wachtwoord)){
                return true;
            }
        }
        return false;
    }
    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public static Account getAccountByEmail(String email){
        for (Account account : allAccounts){
            if (account.getName().equals(email)){
                return account;
            }
        }
        return null;
    }

    public String getName(){
        return email;
    }

    public int getAccountId(){
        return accountId;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setWachtwoord(String wachtwoord){
        this.wachtwoord = wachtwoord;
    }

    public ArrayList<Character> getCharacters(){
        return characters;
    }

    public void removeCharacter(Character c){
        characters.remove(c);
    }

    public void addCharacter(Character c){
        characters.add(c);
    }

    public void setCharacters(ArrayList<Character> characters){
        this.characters = characters;
    }

    public String toString(){
        return email + " ID: " + accountId;
    }
}
