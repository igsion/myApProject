package Models.Cards;

public class Card {

    public int mana;
    public int id;
    public String name;
    public String hero;
    public String rarity;
    public String description;
    public String type;
    private int played = 0;

//    public Card(int mana , String hero , String name , String rarity , String description , String type , Spell spell){
//        this.mana = mana;
//        this.hero = hero;
//        this.name = name;
//        this.rarity = rarity;
//        this.description = description;
//        this.type = type;
//        this.spell = spell;
//    }
//
//    public Card(int mana , String hero , String name , String rarity , String description , String type , Weapon weapon){
//        this.mana = mana;
//        this.hero = hero;
//        this.name = name;
//        this.rarity = rarity;
//        this.description = description;
//        this.type = type;
//        this.weapon = weapon;
//    }
//
//    public Card(int mana , String hero , String name , String rarity , String description , String type , Quest quest){
//        this.mana = mana;
//        this.hero = hero;
//        this.name = name;
//        this.rarity = rarity;
//        this.description = description;
//        this.type = type;
//        this.quest = quest;
//    }
//
//    public Card(int mana , String hero , String name , String rarity , String description , String type , Minion minion){
//        this.mana = mana;
//        this.hero = hero;
//        this.name = name;
//        this.rarity = rarity;
//        this.description = description;
//        this.type = type;
//        this.minion = minion;
//    }

    public int cost() {
        if (rarity.equals("free")) {
            return 0;
        } else if (rarity.equals("common")) {
            return 50;
        } else if (rarity.equals("rare")) {
            return 100;
        } else if (rarity.equals("epic")) {
            return 150;
        } else if (rarity.equals("legendary")) {
            return 200;
        } else {
            return 250;
        }
    }

    public int getPlayed(){
        return played;
    }

    public void cardPlayed(){
        this.played++;
    }
}
