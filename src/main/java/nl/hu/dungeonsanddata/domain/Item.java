package nl.hu.dungeonsanddata.domain;

import java.io.Serializable;

public class Item implements Serializable {
    private String naam;
    private String beschrijving;
    private double gewicht;

    public Item(String naam, String beschrijving, int gewicht){
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.gewicht = gewicht;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving(){
        return beschrijving;
    }

    public double getGewicht(){
        return gewicht;
    }

    public String toString(){
        return naam + ": " + beschrijving + ". Weight: " + gewicht;
    }

    public boolean equals(Object o){
        boolean gelijkeObjecten = false;
        if (o instanceof Item){
            Item andereItem = (Item) o;
            if (this.naam.equals(andereItem.getNaam()) &&
                    this.beschrijving.equals(andereItem.getBeschrijving())){
                gelijkeObjecten = true;
            }
        }
        return gelijkeObjecten;
    }
}
