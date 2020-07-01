package Models.Hero;

public class Warlock extends Hero{

    public Warlock() {
        super("Warlock", 3);
        super.setHeroPower(createHeroPower());
    }

    private HeroPower createHeroPower(){
        return new HeroPower("sacrifice", "Normal", "Do one randomly - Give a random minion +1/+1;" +
                " or Draw a card.", 0);
    }

}
