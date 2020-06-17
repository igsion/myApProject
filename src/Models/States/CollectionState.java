package Models.States;

import Models.Cards.Card;
import Models.Deck;
import Models.FileManagers.CardsFileManager;
import Models.FileManagers.ProfilesFileManager;
import Models.Game;
import Models.Logger;
import Views.CollectionPanel;
import Views.MenuPanel;

import java.util.HashSet;
import java.util.Set;

public class CollectionState extends State {

    private Deck currentDeck;
    private boolean isChoosingDeck = false;

    public CollectionState(Game game) {
        super(game);
    }

    public void newDeckClass(){
        CollectionPanel.getCollectionPanel().chooseClass(this.game.getPlayer());
        Logger.getLogger().log("Collection Buttons" , "Create new deck : choose a hero"
                , this.game.getPlayer());
    }

    public void createDeck(String name , String hero){
        currentDeck = new Deck(name , hero);
        isChoosingDeck = true;
        CollectionPanel.getCollectionPanel().deckCreating(currentDeck);
        Logger.getLogger().log("Collection Buttons" , "Create new deck : deck "
                        + currentDeck.getName() + " created", this.game.getPlayer());
        Logger.getLogger().log("Collection Buttons" , "Create new deck : choose cards"
                , this.game.getPlayer());
    }

    public void editDeck(String deckName){
        currentDeck = this.game.getPlayer().getDeck(deckName);
        isChoosingDeck = true;
        CollectionPanel.getCollectionPanel().deckCreating(currentDeck);
        Logger.getLogger().log("Collection Buttons" , "Start editing " + currentDeck.getName()
                + "  deck" , this.game.getPlayer());
    }

    public void finishDeck(){
        isChoosingDeck = false;
        this.game.getPlayer().addDeck(currentDeck);
        ProfilesFileManager.getProfilesFileManager().updatePlayer(this.game.getPlayer());
        CollectionPanel.getCollectionPanel().updateDeckPanel(this.game.getPlayer());
        Logger.getLogger().log("Collection Buttons" , "Finished editing " +currentDeck.getName()
                        + " deck", this.game.getPlayer());
    }

    public String addCard(String cardName){
        if(!CardsFileManager.getCardsFileManager().getCard(cardName).hero.equals(currentDeck.getHero().toLowerCase())
                && !CardsFileManager.getCardsFileManager().getCard(cardName).hero.equals("neutral")){
            Logger.getLogger().log("Collection Buttons" , "Adding card : " + cardName
                            + " is from another class", this.game.getPlayer());
            return "Only cards from " + currentDeck.getHero() + " and neutral set can be added";
        }else if(!this.game.getPlayer().doesHaveCard(cardName)){
            Logger.getLogger().log("Collection Buttons" , "Adding card : " + cardName
                    + " is unavailable", this.game.getPlayer());
            return "You don't have " + cardName;
        }else{
            Logger.getLogger().log("Collection Buttons" , "Adding card : " + cardName
                    + " is successfully added", this.game.getPlayer());
            return currentDeck.addCard(cardName);
        }
    }

    public Set<Card> availableCards(Set<Card> allCards){
        Set<Card> newCards = new HashSet<>(0);
        for(Card card : allCards){
            if(this.game.getPlayer().doesHaveCard(card.name)){
                newCards.add(card);
            }
        }
        Logger.getLogger().log("Collection Buttons" , "Filter cards : Available cards"
                , this.game.getPlayer());
        return newCards;
    }

    public Set<Card> unavailableCards(Set<Card> allCards){
        Set<Card> newCards = new HashSet<>(0);
        for(Card card : allCards){
            if(!this.game.getPlayer().doesHaveCard(card.name)){
                newCards.add(card);
            }
        }
        Logger.getLogger().log("Collection Buttons" , "Filter cards : Unavailable cards"
                , this.game.getPlayer());
        return newCards;
    }

    public Set<Card> manaCards(int mana , Set<Card> allCards){
        Set<Card> newCards = new HashSet<>(0);
        for(Card card : allCards){
            if(mana == 10){
                if(card.mana >= 10){
                    newCards.add(card);
                }
            }else{
                if(card.mana == mana){
                    newCards.add(card);
                }
            }
        }
        Logger.getLogger().log("Collection Buttons" , "Filter cards : Cards with " + mana + " mana"
                , this.game.getPlayer());
        return newCards;
    }

    public Set<Card> nameSearch(String name , Set<Card> allCards){
        Set<Card> newCards = new HashSet<>(0);
        for(Card card : allCards){
            if(doesContain(card.name , name)){
                newCards.add(card);
            }
        }
        Logger.getLogger().log("Collection Buttons" , "Filter cards : Cards with " + name
                        + " in the name", this.game.getPlayer());
        return newCards;
    }

    private boolean doesContain(String name , String pattern){
        if(pattern.length() > 0) {
            String[] parts = name.split(" ");
            for (String str : parts) {
                if (str.length() < pattern.length()) {
                    return false;
                } else {
                    for (int i = 0; i <= str.length() - pattern.length(); i++) {
                        if(str.substring(i , i+pattern.length()).equals(pattern)){
                            return true;
                        }
                    }
                }
            }
            return false;
        }else{
            return true;
        }
    }

    public void changeState(){
        State.setState(game.getMenuState());
        MenuPanel.getMenuPanel().setGoldLabel(this.game.getPlayer().getPlayerGolds());
        Logger.getLogger().log("Collection Buttons" , "Back to menu" , this.game.getPlayer());
    }

    public boolean removeCard(String cardName){
        Logger.getLogger().log("Collection Buttons" , "Remove card: Remove " + cardName
                + " from " + currentDeck.getName(), this.game.getPlayer());
        return currentDeck.removeCard(cardName);
    }

    public boolean isChoosingDeck(){
        return isChoosingDeck;
    }

    public void refreshDeck(){
        CollectionPanel.getCollectionPanel().deckCreating(currentDeck);
    }
}
