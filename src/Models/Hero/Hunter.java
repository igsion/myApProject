package Models.Hero;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Hunter extends Hero {

    public Hunter() {
        super("Hunter", 4);
        super.setHeroPower(createHeroPower());
        super.setSpecialPower(createSpecialPower());
    }

    private HeroPower createHeroPower(){
        return new HeroPower("Caltrops", "Passive", "Passive \n After your opponent plays a minion," +
                " deal 1 damage to in.", 0, null);
    }

    private SpecialPower createSpecialPower(){
        JsonObject special = new JsonObject();
        JsonObject specialMech = new JsonObject();
        JsonArray specialArray = new JsonArray();
        specialMech.add("giveMinionRush", specialArray);
        special.add("minionPlayed", specialMech);
        return new SpecialPower(null);
    }
}
