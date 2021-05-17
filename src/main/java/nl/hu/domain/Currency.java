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
