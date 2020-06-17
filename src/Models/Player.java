package Models;

import Models.Cards.Card;
import Models.FileManagers.CardsFileManager;
import Models.FileManagers.HeroesFileManager;
import Models.Hero.Hero;

import java.util.*;

public class Player {

    private Date date = new Date();
    private int playerGolds = 1000;
    private long playerId;
    private String playerName;
    private String playerPassword;
    private Set<Hero> availableHeros;
    private List<Card> availableCards;
    private ArrayList<Deck> availableDecks;

    public Player(String playerName, String playerPassword) {
        this.playerName = playerName;
        this.playerPassword = playerPassword;
        this.availableHeros = new HashSet<>(0);
        this.availableCards = new ArrayList<>(0);
        this.availableDecks = new ArrayList<>(0);
        this.playerId = date.getTime();
        initialHero();
        initialCards();
        initialDecks();
    }

    public boolean addHero(Hero hero) {
        if (availableHeros.contains(hero)) {
            return false;
        } else {
            availableHeros.add(hero);
            return true;
        }
    }

    public void addCard(Card card) {
        availableCards.add(card);
    }

    private void initialHero() {
        for (Hero hero : HeroesFileManager.getHeroesFileManager().getHeroesSet()) {
            availableHeros.add(hero);
        }
    }

    private void initialCards() {
        for (Card card : CardsFileManager.getCardsFileManager().getCardsSet()) {
            if (card.rarity.equals("free")) {
                availableCards.add(card);
            }
        }
    }

    private void initialDecks() {
        for (Hero hero : HeroesFileManager.getHeroesFileManager().getHeroesSet()) {
            Deck deck = new Deck(hero.getName() + " Basic", hero.getName());
            for (Card card : CardsFileManager.getCardsFileManager().getCardsSet()) {
                if (card.rarity.equals("free")) {
                    if (card.hero.equals(hero.getName()) || card.hero.equals("neutral")) {
                        deck.addCard(card.name);
                    }
                }
            }
            availableDecks.add(deck);
        }
    }

    public boolean doesHaveCard(String cardName) {
        for (Card myCard : availableCards) {
            if (cardName.equals(myCard.name)) {
                return true;
            }
        }
        return false;
    }

    public boolean addDeck(Deck deck){
        if(availableDecks.contains(deck)){
            return false;
        }else{
            availableDecks.add(deck);
            return true;
        }
    }

    public boolean isCardInDecks(Card card) {
        for (Deck deck : availableDecks) {
            if (deck.cards.contains(card)) {
                return true;
            }
        }
        return false;
    }

//    public List<Card> addableCards(Hero hero){
//        List<Card> addableCards = new ArrayList<Card>(0);
//        for(Card card : availableCards){
//            if(hero.isCardAddable(card)){
//                addableCards.add(card);
//            }
////            if(!hero.getDeck().cards.contains(card)){
////                if(card.hero.equals("neutral") || card.hero.equals(hero.getName())){
////                    addableCards.add(card);
////                }
////            }
//        }
//        return addableCards;
//    }

    public Hero getHero(String hero) {
        for (Hero myHero : availableHeros) {
            if (myHero.getName().equals(hero)) {
                return myHero;
            }
        }
        return null;
    }

    public Deck getDeck(String deckName){
        for(Deck deck : availableDecks){
            if(deck.getName().equals(deckName)){
                return deck;
            }
        }
        return null;
    }

//    public boolean addCardToDeck(Card card , Hero hero){
//        for(Hero myHero : availableHeros){
//            if(myHero.getName().equals(hero.getName())){
//                return myHero.getDeck().addCard(card.name);
//            }
//        }
//        return false;
//    }

//    public boolean removeCardFromDeck(Card card , Hero hero){
//        for(Hero myHero : availableHeros){
//            if(myHero.getName().equals(hero.getName())){
//                return myHero.removeCardFromDeck(card);
//            }
//        }
//        return false;
//    }

//    public boolean isCardInDeck(Card card , Hero hero){
//        for(Hero myHero : availableHeros){
//            if(myHero.getName().equals(hero.getName())){
//                if(myHero.isCardInDeck(card)){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public void setPlayerGolds(int playerGolds) {
        this.playerGolds = playerGolds;
    }

    public int getPlayerGolds() {
        return playerGolds;
    }

    public String getPlayerPasswor() {
        return playerPassword;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Card> getAvailableCards() {
        return availableCards;
    }

    public long getPlayerId() {
        return playerId;
    }

    public String getPlayerPassword() {
        return playerPassword;
    }

    public ArrayList<Deck> getAvailableDecks() {
        return availableDecks;
    }

    public Set<Hero> getAvailableHeros() {
        return availableHeros;
    }
}
