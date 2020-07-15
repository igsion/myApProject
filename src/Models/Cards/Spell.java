package Models.Cards;

import com.google.gson.JsonObject;

public class Spell extends Card {

    private JsonObject mechanic;

    public Spell(String name, String hero, int mana, String rarity, String description, String type,
                 JsonObject mechanic, int id) {
        this.mechanic = mechanic;
        this.mana = mana;
        this.hero = hero;
        this.name = name;
        this.rarity = rarity;
        this.description = description;
        this.type = type;
        this.id = id;
    }

    public JsonObject getMechanic(){
        return mechanic;
    }
}