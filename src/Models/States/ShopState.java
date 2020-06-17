package Models.States;

import Models.Cards.Card;
import Models.FileManagers.ProfilesFileManager;
import Models.Game;
import Models.Logger;
import Views.MenuPanel;

public class ShopState extends State {

    public ShopState(Game game){
        super(game);
    }

    public String buyCard(Card card){
        if(this.game.getPlayer().getPlayerGolds() < card.cost()){
            Logger.getLogger().log("Shop Buttons" , "Buy a card : Golds not enough for " + card.name
                    , this.game.getPlayer());
            return "Not enough gold";
        }else if(this.game.getPlayer().doesHaveCard(card.name)){
            Logger.getLogger().log("Shop Buttons" , "Buy a card : Already have " + card.name
                    , this.game.getPlayer());
            return "You already have this card";
        }else {
            this.game.getPlayer().setPlayerGolds(this.game.getPlayer().getPlayerGolds() - card.cost());
            this.game.getPlayer().addCard(card);
            ProfilesFileManager.getProfilesFileManager().updatePlayer(this.game.getPlayer());
            Logger.getLogger().log("Shop Buttons" , "Buy a card : Successfully bought " + card.name
                    , this.game.getPlayer());
            return "Successful";
        }
    }

    public String sellCard(Card card){
        if(!this.game.getPlayer().doesHaveCard(card.name)){
            Logger.getLogger().log("Shop Buttons" , "Sell a card : Don't have " + card.name
                    , this.game.getPlayer());
            return "Doesn't have card";
        }else if(card.rarity.equals("free")){
            Logger.getLogger().log("Shop Buttons" , "Sell a card : " + card.name + " is a free card"
                    , this.game.getPlayer());
            return "This card cannot be sold";
        }else if(this.game.getPlayer().isCardInDecks(card)) {
            Logger.getLogger().log("Shop Buttons" , "Sell a card : " + card.name + " is in a deck"
                    , this.game.getPlayer());
            return "This card is in one of the decks";
        }else {
            this.game.getPlayer().getAvailableCards().remove(card);
            this.game.getPlayer().setPlayerGolds(this.game.getPlayer().getPlayerGolds() + card.cost());
            ProfilesFileManager.getProfilesFileManager().updatePlayer(this.game.getPlayer());
            Logger.getLogger().log("Shop Buttons" , "Sell a card : " + card.name
                            + " sold successfully", this.game.getPlayer());
            return "Successful";
        }
    }

    public void changeState(){
        State.setState(game.getMenuState());
        MenuPanel.getMenuPanel().setGoldLabel(this.game.getPlayer().getPlayerGolds());
        Logger.getLogger().log("Collection Buttons" , "Back to menu" , this.game.getPlayer());
    }

}
