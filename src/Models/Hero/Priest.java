package Models.Hero;

public class Priest extends Hero{

    public Priest() {
        super("Priest", 5);
        super.setHeroPower(createHeroPower());
    }

    private HeroPower createHeroPower(){
        return new HeroPower("Heal", "Normal", "Restore 4 Health.", 2);
    }
}
