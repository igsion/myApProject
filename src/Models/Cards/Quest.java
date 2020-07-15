package Models.Cards;

import Models.ABC.Observer;

public class Quest extends Card implements Observer {

    private int requiredPoint;
    private String action;
    private String reward;

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

    public int getRequiredPoint() {
        return requiredPoint;
    }

    public String getAction() {
        return action;
    }

    public String getReward() {
        return reward;
    }

    @Override
    public void update(String event, int player) {

    }
}
