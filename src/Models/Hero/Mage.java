package Models.Hero;

public class Mage extends Hero {

    public Mage() {
        super("Mage", 1);
        super.setHeroPower(createHeroPower());
    }

    private HeroPower createHeroPower(){
        return new HeroPower("Fireblast", "Normal", "Deal 1 damage.", 2);
    }
}
