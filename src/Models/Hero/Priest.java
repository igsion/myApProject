package Models.Hero;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Priest extends Hero{

    public Priest() {
        super("Priest", 5);
        super.setHeroPower(createHeroPower());
        super.setSpecialPower(createSpecialPower());
    }

    private HeroPower createHeroPower(){
        return new HeroPower("Heal", "Normal",
                "Restore 4 Health.", 2, null);
    }

    private SpecialPower createSpecialPower(){
        JsonObject special = new JsonObject();
        JsonObject specialMech = new JsonObject();
        JsonArray specialArray = new JsonArray();
        specialMech.add("doubleHeal", specialArray);
        special.add("healSpell", specialMech);
        return new SpecialPower(null);
    }
}
