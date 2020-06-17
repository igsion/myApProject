package Models.States;

import Models.Game;
import Models.Logger;
import Views.CollectionPanel;
import Views.StatusPanel;

public class MenuState extends State {

    public MenuState(Game game){
        super(game);
    }

    public void changeState(String name){
        if(name.equals("play")){
            State.setState(game.getPlayState());
            Logger.getLogger().log("Menu Buttons" , "Navigate to play" , this.game.getPlayer());
        }else if(name.equals("shop")){
            State.setState(game.getShopState());
            Logger.getLogger().log("Menu Buttons" , "Navigate to shop" , this.game.getPlayer());
        }else if(name.equals("collection")){
            CollectionPanel.getCollectionPanel().updateDeckPanel(game.getPlayer());
            State.setState(game.getCollectionState());
            Logger.getLogger().log("Menu Buttons" , "Navigate to collection" , this.game.getPlayer());
        }else if(name.equals("setting")){
            State.setState(game.getSettingState());
            Logger.getLogger().log("Menu Buttons" , "Navigate to setting" , this.game.getPlayer());
        }else if(name.equals("status")){
            StatusPanel.getStatusPanel().updateStatus(this.game.getPlayer().getAvailableDecks());
            State.setState(game.getStatusState());
            Logger.getLogger().log("Menu Buttons" , "Navigate to status" , this.game.getPlayer());
        }
    }
}
