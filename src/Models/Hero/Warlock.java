package Models.Hero;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Warlock extends Hero{

    public Warlock() {
        super("Warlock", 3);
        super.setHeroPower(createHeroPower());
        super.setSpecialPower(createSpecialPower());
    }

    private HeroPower createHeroPower(){
        return new HeroPower("sacrifice", "Normal", "Do one randomly - Give a random minion +1/+1;" +
                " or Draw a card.", 0, null);
    }

    private SpecialPower createSpecialPower(){
        JsonObject special = new JsonObject();
        JsonObject specialMech = new JsonObject();
        JsonArray specialArray = new JsonArray();
        specialArray.add(35);
        specialMech.add("setHeroHealth", specialArray);
        special.add("onStart", specialMech);
        return new SpecialPower(special);
    }

}
