package nl.hu.domain;

public class Spell {
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
