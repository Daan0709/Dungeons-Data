package nl.hu.domain;

public class Stat {
    private String type;
    private int score;
    private int modifier;

    public Stat(String type, int score){
        this.type = type;
        this.score = score;
        updateModifier();
    }

    public Stat(String type){
        this(type, 0);
    }

    public void setScore(int score){
        this.score = score;
        updateModifier();
    }

    public void addScore(int aantal){
        score += aantal;
        updateModifier();
    }

    public String getType(){
        return type;
    }

    public int getScore(){
        return score;
    }

    public void updateModifier(){
        modifier = (score - 10) / 2;
    }

    public String toString(){
        return type +": " + score + " results in " + modifier + " modifier";
    }
}
