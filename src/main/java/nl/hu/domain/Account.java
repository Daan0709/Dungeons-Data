package nl.hu.domain;

public class Account {
    private String email;
    private String wachtwoord;

    public Account(String email, String wachtwoord){
        this.email = email;
        this.wachtwoord = wachtwoord;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setWachtwoord(String wachtwoord){
        this.wachtwoord = wachtwoord;
    }
}
