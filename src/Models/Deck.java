package Models;

import Models.Cards.Card;
import Models.FileManagers.CardsFileManager;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    List<Card> cards;
    private String name;
    private String hero;
    private int win, total;

    public Deck(List<Card> cards, String name, String hero) {
        this.cards = cards;
        this.name = name;
        this.hero = hero;
        this.win = 0;
        this.total = 0;
    }

    public Deck(String name, String hero) {
        this.cards = new ArrayList<>(0);
        this.name = name;
        this.hero = hero;
        this.win = 0;
        this.total = 0;
    }

    public String addCard(String cardName) {
        Card card;
        if (!CardsFileManager.getCardsFileManager().cardExists(cardName)) {
            return "Card doesn't exists";
        } else {
            card = CardsFileManager.getCardsFileManager().getCard(cardName);
        }
        if (cards.size() < 15) {
            int cardCounter = 0;
            for (Card myCard : cards) {
                if (myCard.name.equals(card.name)) {
                    cardCounter++;
                }
            }
            if (cardCounter < 2) {
                cards.add(card);
                return "Successful";
            } else {
                return "2 cards are already in deck";
            }
        } else {
            return "Deck is full";
        }
    }

    public boolean removeCard(String cardName) {
        Card card;
        if (!CardsFileManager.getCardsFileManager().cardExists(cardName)) {
            return false;
        } else {
            card = CardsFileManager.getCardsFileManager().getCard(cardName);
        }
        if (!doesContain(card)) {
            return false;
        } else {
            for(Card myCard : cards) {
                if (myCard.name.equals(card.name)) {
                    cards.remove(myCard);
                    return true;
                }
            }
            return false;
        }
    }

    private boolean doesContain(Card card){
        for(Card myCard : cards){
            if(myCard.name.equals(card.name)){
                return true;
            }
        }
        return false;
    }

    public List<Card> getCards() {
        return cards;
    }

    public String getHero() {
        return hero;
    }

    public String getName(){
        return name;
    }

    public int getWin(){
        return win;
    }

    public int getTotal(){
        return total;
    }

    public double averageCost(){
        double average = 0;
        double total = 0;
        for(Card card : this.cards){
            total += card.mana;
        }
        if(this.cards.size() > 0) {
            average = total /(double) this.cards.size();
        }
        return average;
    }

    public String mostPlayedCard(){
        int number = 0;
        String mostPlayed = "";
        for(Card card : cards){
            if(card.getPlayed() >= number){
                mostPlayed = card.name;
                number = card.getPlayed();
            }
        }
        return mostPlayed;
    }
}
