package nl.hu.dungeonsanddata.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
    private String email;
    private String wachtwoord;
    private int accountId;
    private ArrayList<Character> characters = new ArrayList<>();
    private static int accountAmount;
    private static ArrayList<Account> allAccounts = new ArrayList<>();

    public Account(String email, String wachtwoord){
        this.email = email;
        this.wachtwoord = wachtwoord;
        accountAmount += 1;
        this.accountId = accountAmount;
        allAccounts.add(this);
    }

    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public String getEmail(){
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
}
