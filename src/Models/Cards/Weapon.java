package Models.Cards;

public class Weapon extends Card{

    public int durability;
    public int damage;
    public String mechanic;

    public Weapon(String name , String hero , int mana , String rarity , String description , String type ,
                  String mechanic , int damage , int durability , int id){
        this.damage = damage;
        this.durability = durability;
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
