package nl.hu.domain;

import java.util.ArrayList;

public class Character {
    private String naam;
    private String race;
    private int experience;
    private int hitpoints;
    private int maxGewicht;
    private Class klasse;
    private ArrayList<Skill> skills = new ArrayList<>();
    private ArrayList<Item> itemlist = new ArrayList<>();
    private ArrayList<Currency> currency = new ArrayList<>();
    private ArrayList<Spell> spells = new ArrayList<>();

    public Character(String naam, String race, int experience, int hitpoints, Class klasse){
        this.naam = naam;
        this.race = race;
        this.experience = experience;
        this.hitpoints = hitpoints;
        this.klasse = klasse;

        currency.add(new Currency("Platinum"));
        currency.add(new Currency("Gold"));
        currency.add(new Currency("Silver"));
        currency.add(new Currency("Copper"));
    }
    public Character(String naam, String race, int hitpoints, Class klasse){
        this(naam, race, 0, hitpoints, klasse);
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

    public int getHitpoints() {
        return hitpoints;
    }

    public int getMaxGewicht() {
        return maxGewicht;
    }

    public Class getKlasse() {
        return klasse;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public ArrayList<Item> getItemlist() {
        return itemlist;
    }

    public ArrayList<Currency> getCurrency() {
        return currency;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public void setExperience(int xp){
        experience = xp;
    }

    public void setHitpoints(int hp){
        hitpoints = hp;
    }

//    public void addCurrency(String type, int aantal){
//        int index = currency.indexOf(Currency )
//    }

    public int calculateTotalGold(){
        int res = 0;
        for (Currency currency : currency){
            if (currency.getType().equals("Platinum")){
                res += (10 * currency.getAantal());
            } else if (currency.getType().equals("Gold")) {
                res += currency.getAantal();
            } else if (currency.getType().equals("Silver")) {
                res += (currency.getAantal() / 10);
            } else if (currency.getType().equals("Copper")) {
                res += (currency.getAantal() / 100);
            }
        }
        return res;
    }
}
