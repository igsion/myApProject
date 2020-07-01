package Models.Hero;

public class Hero {

    private String name;
    private HeroPower heroPower;
    private int hp = 30, id;

    public Hero(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void setHeroPower(HeroPower heroPower) {
        this.heroPower = heroPower;
    }

    public String getName() {
        return name;
    }

    public HeroPower getHeroPower() {
        return heroPower;
    }

    public int getHp() {
        return hp;
    }

    public int getId() {
        return id;
    }
}
