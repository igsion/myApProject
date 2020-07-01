package Models.Hero;

public class Hunter extends Hero {

    public Hunter() {
        super("Hunter", 4);
        super.setHeroPower(createHeroPower());
    }

    private HeroPower createHeroPower(){
        return new HeroPower("Caltrops", "Passive", "Passive \n After your opponent plays a minion," +
                " deal 1 damage to in.", 0);
    }
}
