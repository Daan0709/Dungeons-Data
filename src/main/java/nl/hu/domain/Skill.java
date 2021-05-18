package nl.hu.domain;

public class Skill {
    private String naam;
    private String beschrijving;

    public Skill(String naam, String beschrijving){
        this.naam = naam;
        this.beschrijving = beschrijving;
    }

    public String toString(){
        return naam + ": " + beschrijving;
    }
}
