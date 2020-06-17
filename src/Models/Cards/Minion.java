package Models.Cards;

public class Minion extends Card{

    public String race;
    public String mechanic;
    public int hp;
    public int attack;

    public Minion(String name , String hero , int mana , String rarity , String description , String type ,
                  String race , String mechanic , int attack ,int hp , int id){
        this.race = race;
        this.mechanic = mechanic;
        this.hp = hp;
        this.attack = attack;
        this.mana = mana;
        this.hero = hero;
        this.name = name;
        this.rarity = rarity;
        this.description = description;
        this.type = type;
        this.id = id;
    }

}
