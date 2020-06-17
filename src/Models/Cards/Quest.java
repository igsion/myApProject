package Models.Cards;

public class Quest extends Card {

    int requiredPoint;
    String action;
    String reward;

    public Quest(String name , String hero , int mana , String rarity , String description , String type
            ,int requiredPoint , String action , String reward , int id){
        this.name = name;
        this.hero = hero;
        this.mana = mana;
        this.rarity = rarity;
        this.description = description;
        this.type = type;
        this.requiredPoint = requiredPoint;
        this.action = action;
        this.reward = reward;
        this.id = id;
    }

}
