package Models.Hero;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Mage extends Hero {

    public Mage() {
        super("Mage", 1);
        super.setHeroPower(createHeroPower());
        super.setSpecialPower(createSpecialPower());
    }

    private HeroPower createHeroPower(){
        return new HeroPower("Fireblast", "Normal", "Deal 1 damage.", 2, null);
    }

    private SpecialPower createSpecialPower(){
        JsonObject special = new JsonObject();
        JsonObject specialMech = new JsonObject();
        JsonArray specialArray = new JsonArray();
        specialArray.add(2);
        specialMech.add("lowerSpellCost", specialArray);
        special.add("spellDraw", specialMech);
        return new SpecialPower(null);
    }
}
