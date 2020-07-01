package Models.Hero;

public class Rogue extends Hero {

    public Rogue() {
        super("Rogue", 2);
        super.setHeroPower(createHeroPower());
    }

    private HeroPower createHeroPower(){
        return new HeroPower("Pocket Picking", "Normal", "Steal 1 card from your opponent's deck.", 3);
    }
}
