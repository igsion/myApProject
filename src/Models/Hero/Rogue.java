package Models.Hero;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Rogue extends Hero {

    public Rogue() {
        super("Rogue", 2);
        super.setHeroPower(createHeroPower());
        super.setSpecialPower(createSpecialPower());
    }

    private HeroPower createHeroPower(){
        return new HeroPower("Pocket Picking", "Normal",
                "Steal 1 card from your opponent's deck.", 3, null);
    }

    private SpecialPower createSpecialPower(){
        JsonObject special = new JsonObject();
        JsonObject specialMech = new JsonObject();
        JsonArray specialArray = new JsonArray();
        specialArray.add(2);
        specialMech.add("lowerCardCost", specialArray);
        special.add("foreignCard", specialMech);
        return new SpecialPower(null);
    }
}
