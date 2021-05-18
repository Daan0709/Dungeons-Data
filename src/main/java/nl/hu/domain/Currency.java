package nl.hu.domain;

public class Currency {
    private String type;
    private int aantal;

    public Currency(String type){
        this.type = type;
        aantal = 0;
    }

    public String getType(){
        return type;
    }

    public int getAantal(){
        return aantal;
    }

    public void addAantal(int aantal){
        this.aantal += aantal;
    }

    public void decreaseAantal(int aantal){
        if (this.aantal - aantal > 0){         // Only decreases if the amount is higher than 0.
            this.aantal -= aantal;
        } else {
            this.aantal = 0;                    // If the amount would result in a number below or at 0, the amount is set to 0.
        }
    }

    public String toString(){
        return aantal + " " + type;
    }

    public boolean equals(Object obj){
        boolean gelijkeObjecten = false;
        if (obj instanceof Currency){
            Currency andereCurrency = (Currency) obj;
            if (this.type.equals(andereCurrency.getType())) {
                gelijkeObjecten = true;
            }
        }
        return gelijkeObjecten;
    }
}
