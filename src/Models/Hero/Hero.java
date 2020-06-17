package Models.Hero;

public class Hero {

    private String name;
    private String specialPower;
    private int hp;
    private HeroPower heroPower;

    public Hero(String name, String specialPower, int hp) {
        this.name = name;
        this.specialPower = specialPower;
        this.hp = hp;
        buildHeroPower();
    }
//
//    private void buildDeck() {
//        List<Card> cards = new ArrayList<>(0);
//        for (Card card : CardsFileManager.getCardsFileManager().getCardsSet()) {
//            if (card.hero.toLowerCase().equals("neutral") || card.hero.toLowerCase().equals(this.name)) {
//                if (card.rarity.equals("free")) {
//                    cards.add(card);
//                }
//            }
//        }
//        Deck deck = new Deck(cards);
//        this.deck = deck;
//    }

    private void buildHeroPower() {
        if (this.name.toLowerCase().equals("mage")) {
            this.heroPower = new HeroPower("Fireblast", "Deal 1 damage.", 2);
        } else if (this.name.toLowerCase().equals("rogue")) {
            this.heroPower = new HeroPower("Steal Card",
                    "Steal 1 card from your opponent's deck and add it to your hand.", 3);
        } else if (this.name.toLowerCase().equals("warlock")) {
            this.heroPower = new HeroPower("Self Harm",
                    "Does one of these randomly : Add +1/+1 to a random minion on board or Draw a card."
                    , 0);
        }
    }

//    public boolean isDeckFull(){
//        if(this.deck.getCards().size() == 15){
//            return true;
//        }else{
//            return false;
//        }
//    }

//    public boolean isCardAddable(Card card){
//        int count = 0;
//        if(!isDeckFull()){
//            if(!card.hero.equals("neutral") && !card.hero.equals(this.name)){
//                return false;
//            }
//            for(Card myCard : deck.getCards()){
//                if(myCard.name.equals(card.name)){
//                    count++;
//                }
//                if(count == 2){
//                    return false;
//                }
//            }
//            return true;
//        }else{
//            return false;
//        }
//    }

//    public boolean isCardInDeck(Card card){
//        for(Card myCard : deck.getCards()){
//            if(myCard.name.equals(card.name)){
//                return true;
//            }
//        }
//        return false;
//    }

//    public boolean removeCardFromDeck(Card card){
//        for(Card myCard : deck.getCards()){
//            if(myCard.name.equals(card.name)){
//                return deck.getCards().remove(myCard);
//            }
//        }
//        return false;
//    }

    public String getName() {
        return name;
    }

    public HeroPower getHeroPower() {
        return heroPower;
    }

    public String getSpecialPower() {
        return specialPower;
    }

    public int getHp() {
        return hp;
    }
}
