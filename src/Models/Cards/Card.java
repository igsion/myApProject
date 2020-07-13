package Models.Cards;

public class Card {

    public int mana;
    public int id;
    public String name;
    public String hero;
    public String rarity;
    public String description;
    public String type;
    private int played = 0;

    public int cost() {
        if (rarity.equals("free")) {
            return 0;
        } else if (rarity.equals("common")) {
            return 50;
        } else if (rarity.equals("rare")) {
            return 100;
        } else if (rarity.equals("epic")) {
            return 150;
        } else if (rarity.equals("legendary")) {
            return 200;
        } else {
            return 250;
        }
    }

    public int getPlayed(){
        return played;
    }

    public void cardPlayed(){
        this.played++;
    }
}
