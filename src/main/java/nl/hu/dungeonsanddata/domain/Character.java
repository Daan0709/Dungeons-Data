package nl.hu.dungeonsanddata.domain;

import java.io.Serializable;
import java.util.*;

public class Character implements Serializable {
    private String naam;
    private String race;
    private int experience;
    private int level;
    private int hitpoints;
    private int maxHitpoints;
    private double currentWeight;
    private int maxGewicht;
    private int maxSpellslots;
    private int verbruikteSpellslots;
    private Class klasse;
    private ArrayList<Stat> stats = new ArrayList<>();
    private ArrayList<Skill> skills = new ArrayList<>();
    private HashMap<Item, Integer> itemlist = new HashMap<>();
    private ArrayList<Currency> currency = new ArrayList<>();
    private double totalCurrency;
    private ArrayList<Spell> spells = new ArrayList<>();

    public Character(String naam, String race, int experience, int maxHitpoints, String klasse, int maxSpellslots) throws WrongTypeException {
        this.naam = naam;
        this.race = race;
        this.experience = experience;
        this.hitpoints = maxHitpoints;
        this.maxHitpoints = maxHitpoints;
        this.maxSpellslots = maxSpellslots;
        this.verbruikteSpellslots = 0;
        setKlasse(klasse);
        updateLevel();

        ArrayList<String> allStats = new ArrayList<>(Arrays.asList("Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"));
        for (String stat : allStats) {
            stats.add(new Stat(stat));
        }
        updateMaxGewicht();
        updateCurrentWeight();
        currency.add(new Currency("Platinum"));
        currency.add(new Currency("Gold"));
        currency.add(new Currency("Silver"));
        currency.add(new Currency("Copper"));
        calculateTotalGold();
    }
    public Character(String naam, String race, int maxHitpoints, String klasse, int maxSpellslots) throws WrongTypeException {
        this(naam, race, 0, maxHitpoints, klasse, maxSpellslots);
    }

    public String getNaam() {
        return naam;
    }

    public String getRace() {
        return race;
    }

    public int getExperience() {
        return experience;
    }

    public int getMaxSpellslots(){
        return maxSpellslots;
    }

    public int getVerbruikteSpellslots(){
        return verbruikteSpellslots;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public double getCurrentWeight(){
        return currentWeight;
    }

    public int getMaxGewicht() {
        return maxGewicht;
    }

    public Class getKlasse() {
        return klasse;
    }

    public ArrayList<Stat> getStats() {
        return stats;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public HashMap<Item, Integer> getItemlist() {
        return itemlist;
    }

    public ArrayList<Currency> getCurrency() {
        return currency;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    public Currency getSpecificCurrency(String type){
        for (Currency currency : getCurrency()){
            if (currency.getType().equals(type)){
                return currency;
            }
        }
        return null;
    }

    public Stat getSpecificStat(String type){
        for (Stat stat : getStats()){
            if (stat.getType().equals(type)){
                return stat;
            }
        }
        return null;
    }

    public double getTotalCurrency(){
        return totalCurrency;
    }

    public Item getSpecificItem(String naam, String beschrijving){
        for (Item item : itemlist.keySet()){
            if (item.getNaam().equals(naam) && item.getBeschrijving().equals(beschrijving)){
                return item;
            }
        }
        return null;
    }

    public void setKlasse(String type) throws WrongTypeException {
        klasse = new Class(type);
    }

    public void setExperience(int xp){
        experience = xp;
        updateLevel();
    }

    public void addExperience(int aantal){
        experience += aantal;
        updateLevel();
    }

    public void setMaxHitpoints(int hp) throws Exception {
        if (hp > 0){
            if (hp >= hitpoints) {
                maxHitpoints = hp;
            } else {
                setHitpoints(hp);
                maxHitpoints = hp;
            }
        } else {
            throw new Exception("Max hitpoints can't go below 0 or below the current amount of hitpoints!");
        }
    }

    public void setHitpoints(int hp){
        hitpoints = hp;
    }

    public void decreaseHitpoints(int aantal){
        if (hitpoints - aantal <= 0){
            hitpoints = 0;
        } else {
            hitpoints -= aantal;
        }
    }

    public void addHitpoints(int aantal){
        if (hitpoints + aantal <= maxHitpoints) {
            hitpoints += aantal;
        } else {
            hitpoints = maxHitpoints;
        }
    }

    public void setMaxSpellslots(int aantal){
        if (aantal >= 0){
            maxSpellslots = aantal;
        }
    }

    public void useSpellslot(){
        if (verbruikteSpellslots + 1 <= maxSpellslots){
            verbruikteSpellslots += 1;
        }
    }

    public void resetSpellslots(){
        verbruikteSpellslots = 0;
    }

    public void updateMaxGewicht(){
        Stat strength = null;
        for (Stat stat : stats){
            if (stat.getType().equals("Strength")){
                strength = stat;
            }
        }
        maxGewicht = strength.getScore() * 15;
    }

    public void updateCurrentWeight(){
        double res = 0;
        for (Map.Entry<Item, Integer> entry : itemlist.entrySet()){
            Item item = entry.getKey();
            int aantal = entry.getValue();
            res += (item.getGewicht() * aantal);
        }
        currentWeight = res;
    }

    public void setStat(String type, int score) throws Exception {
        if (score > 30 || score < -30){
            throw new Exception("Stats can't go below -30 or above 30!");
        }
        for (Stat stat : stats){
            if (stat.getType().equals(type)){
                stat.setScore(score);
            }
        }
        if (type.equals("Strength")) {
            updateMaxGewicht();
        }
    }

    public void addCurrency(String type, int aantal){
        for (Currency currency : currency){
            if (currency.getType().equals(type)){
                currency.addAantal(aantal);
            }
        }
        calculateTotalGold();
    }

    public void decreaseCurrency(String type, int aantal){
        for (Currency currency : currency){
            if (currency.getType().equals(type)){
                currency.decreaseAantal(aantal);
            }
        }
        calculateTotalGold();
    }

    public void setCurrency(String type, int aantal) throws Exception {
        for (Currency currency : currency) {
            if (currency.getType().equals(type)) {
                currency.setAantal(aantal);
            }
        }
        calculateTotalGold();
    }

    public void updateLevel(){
        if (experience < 300){
            level = 1;
        } else if (experience < 900){
            level = 2;
        } else if (experience < 2700){
            level = 3;
        } else if (experience < 6500){
            level = 4;
        } else if (experience < 14000){
            level = 5;
        } else if (experience < 23000){
            level = 6;
        } else if (experience < 34000){
            level = 7;
        } else if (experience < 48000){
            level = 8;
        } else if (experience < 64000){
            level = 9;
        } else if (experience < 85000){
            level = 10;
        } else if (experience < 100000){
            level = 11;
        } else if (experience < 120000){
            level = 12;
        } else if (experience < 140000){
            level = 13;
        } else if (experience < 165000){
            level = 14;
        } else if (experience < 195000){
            level = 15;
        } else if (experience < 225000){
            level = 16;
        } else if (experience < 265000){
            level = 17;
        } else if (experience < 305000){
            level = 18;
        } else if (experience < 355000){
            level = 19;
        } else {
            level = 20;
        }
    }

    public double calculateTotalGold(){
        double res = 0;
        for (Currency currency : currency){
            if (currency.getType().equals("Platinum")){
                res += (10 * currency.getAantal());
            } else if (currency.getType().equals("Gold")) {
                res += currency.getAantal();
            } else if (currency.getType().equals("Silver")) {
                res += ((double) currency.getAantal() / 10);    // Silver en copper gecast naar double om afronding te voorkomen.
            } else if (currency.getType().equals("Copper")) {
                res += ((double) currency.getAantal() / 100);   // ^^^
            }
        }
        totalCurrency = res;
        return res;
    }

    public void addSpell(Spell spell){
        spells.add(spell);
    }

    public void increaseItemAmount(Item item, int aantal){
        if (item.getGewicht() * aantal + currentWeight <= maxGewicht){
            for (Map.Entry<Item, Integer> entry : itemlist.entrySet()){
             if (item.equals(entry.getKey())) {
                 itemlist.put(item, itemlist.get(item) + aantal);
                 updateCurrentWeight();
             }
            }
        }
    }

    public void decreaseItemAmount(Item item, int aantal){
        for (Map.Entry<Item, Integer> entry : itemlist.entrySet()){
            if (item.equals(entry.getKey())) {
                if (entry.getValue() <= aantal){
                    itemlist.remove(item);
                } else {
                    itemlist.put(item, itemlist.get(item) - aantal);
                    updateCurrentWeight();
                }
            }
        }
    }

    public void setItemAmount(Item item, int aantal){
        if (item.getGewicht() * aantal + currentWeight <= maxGewicht){
            for (Map.Entry<Item, Integer> entry : itemlist.entrySet()){
                if (item.equals(entry.getKey())) {
                    itemlist.put(item, aantal);
                    updateCurrentWeight();
                }
            }
        }
    }

    public void addItem(Item item){
        if (item.getGewicht() + currentWeight <= maxGewicht) {
            itemlist.put(item, 1);
            updateCurrentWeight();
        }
    }

    public void addSkill(Skill skill){
        skills.add(skill);
    }

    public void removeSpell(Spell spell){
        spells.remove(spell);
    }

    public void removeItem(Item item){
        itemlist.remove(item);
        updateCurrentWeight();
    }

    public void removeSkill(Skill skill){
        skills.remove(skill);
    }

    public String toString(){
        return String.format("%s the level %s %s %s", naam, level, race, klasse);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return getExperience() == character.getExperience() && getLevel() == character.getLevel() && getHitpoints() == character.getHitpoints() && getMaxHitpoints() == character.getMaxHitpoints() && Double.compare(character.getCurrentWeight(), getCurrentWeight()) == 0 && getMaxGewicht() == character.getMaxGewicht() && getMaxSpellslots() == character.getMaxSpellslots() && getVerbruikteSpellslots() == character.getVerbruikteSpellslots() && getNaam().equals(character.getNaam()) && getRace().equals(character.getRace()) && getKlasse().equals(character.getKlasse()) && getStats().equals(character.getStats()) && getSkills().equals(character.getSkills()) && getItemlist().equals(character.getItemlist()) && getCurrency().equals(character.getCurrency()) && getSpells().equals(character.getSpells());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNaam(), getRace(), getExperience(), getLevel(), getHitpoints(), getMaxHitpoints(), getCurrentWeight(), getMaxGewicht(), getMaxSpellslots(), getVerbruikteSpellslots(), getKlasse(), getStats(), getSkills(), getItemlist(), getCurrency(), getSpells());
    }
}
