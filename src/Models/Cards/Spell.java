package Models.Cards;

public class Spell extends Card{

    String mechanic;

    public Spell(String name , String hero , int mana , String rarity , String description , String type ,
                   String mechanic , int id){
        this.mechanic = mechanic;
        this.mana = mana;
        this.hero = hero;
        this.name = name;
        this.rarity = rarity;
        this.description = description;
        this.type = type;
        this.id = id;
    }

}
