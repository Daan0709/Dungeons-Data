package nl.hu.dungeonsanddata.domain;

import java.io.Serializable;

public class Spell implements Serializable {
    private String naam;
    private String beschrijving;
    private String duration;

    public Spell(String naam, String beschrijving, String duration){
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.duration = duration;
    }

    public String toString(){
        return naam + ": " + beschrijving + ". " + duration;
    }
}
