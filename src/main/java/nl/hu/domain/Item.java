package nl.hu.domain;

public class Item {
    private String naam;
    private String beschrijving;
    private int aantal;
    private double gewicht;

    public Item(String naam, String beschrijving, int gewicht){
        this.naam = naam;
        this.beschrijving = beschrijving;
        aantal = 1;
        this.gewicht = gewicht;
    }

    public int berekenGewicht(){
        return (int) gewicht / aantal;
    }

    public void addAantal(int aantal){
        int gewichtVanEen = berekenGewicht();
        this.aantal += aantal;
        this.gewicht = gewichtVanEen * this.aantal;
    }

    public void decreaseAantal(int aantal){
        int gewichtVanEen = berekenGewicht();
        this.aantal -= aantal;
        this.gewicht = gewichtVanEen * this.aantal;
    }

    public String toString(){
        return naam + ": " + beschrijving + ". Amount: " + aantal + " with a weight of: " + gewicht;
    }
}
