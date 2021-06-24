package nl.hu.dungeonsanddata.domain;

import java.io.Serializable;

public class Skill implements Serializable {
    private String naam;
    private String beschrijving;

    public Skill(String naam, String beschrijving){
        this.naam = naam;
        this.beschrijving = beschrijving;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public String toString(){
        return naam + ": " + beschrijving;
    }
}
