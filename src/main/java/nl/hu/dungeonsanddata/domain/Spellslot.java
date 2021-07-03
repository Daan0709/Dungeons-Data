package nl.hu.dungeonsanddata.domain;

import java.io.Serializable;

public class Spellslot implements Serializable {
    private int level;
    private int maxAmount;
    private int usedAmount;

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        if (maxAmount >= 0){
            if (maxAmount < usedAmount){
                usedAmount = maxAmount;
            }
            this.maxAmount = maxAmount;
        } else {
            usedAmount = 0;
            this.maxAmount = 0;
        }
    }

    public int getUsedAmount() {
        return usedAmount;
    }

    public int getLevel(){
        return level;
    }

    public Spellslot(int level, int maxAmount){
        this.level = level;
        this.maxAmount = maxAmount;
    }

    public void useSpellslot(){
        if (usedAmount + 1  > maxAmount){
            return;
        }
        usedAmount++;
    }

    public void removeSpellslot(){
        if (usedAmount - 1  < 0){
            return;
        }
        usedAmount--;
    }


    public void resetSpellslots(){
        usedAmount = 0;
    }

    public boolean equals(Object o){
        boolean equalObjects = false;
        if (o instanceof Spellslot){
            Spellslot otherSpellslot = (Spellslot) o;
            if (otherSpellslot.getLevel() == level){
                equalObjects = true;
            }
        }
        return equalObjects;
    }

}
