package Controllers;

import Models.States.MenuState;
import Models.States.State;
import Views.Display;
import Views.PlayPanels.ChooseDeckPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController implements ActionListener {

    private JButton shopButton, statusButton, collectionButton, settingButton, playButton;
    private MenuState menuState;

    public MenuController(JButton shopButton, JButton statusButton ,JButton collectionButton ,
                          JButton settingButton ,JButton playButton){
        this.shopButton = shopButton;
        this.statusButton = statusButton;
        this.collectionButton = collectionButton;
        this.settingButton = settingButton;
        this.playButton = playButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == shopButton){
            Display.getDisplay().changePage("shop");
            if(menuState == null) {
                menuState = (MenuState) State.getState();
                menuState.changeState("shop");
            }else{
                menuState.changeState("shop");
            }
        }
        if(e.getSource() == statusButton){
            Display.getDisplay().changePage("status");
            if(menuState == null) {
                menuState = (MenuState) State.getState();
                menuState.changeState("status");
            }else{
                menuState.changeState("status");
            }
        }
        if(e.getSource() == collectionButton){
            Display.getDisplay().changePage("collection");
            if(menuState == null) {
                menuState = (MenuState) State.getState();
                menuState.changeState("collection");
            }else{
                menuState.changeState("collection");
            }
        }

        if(e.getSource() == settingButton){
            Display.getDisplay().changePage("setting");
            if(menuState == null) {
                menuState = (MenuState) State.getState();
                menuState.changeState("setting");
            }else{
                menuState.changeState("setting");
            }
        }

        if(e.getSource() == playButton){
            Display.getDisplay().changePage("chooseDeck");
            if(menuState == null) {
                menuState = (MenuState) State.getState();
                ChooseDeckPanel.getChooseDeckPanel().updateDecks(menuState.getDecks());
                menuState.changeState("play");
            }else{
                ChooseDeckPanel.getChooseDeckPanel().updateDecks(menuState.getDecks());
                menuState.changeState("play");
            }
        }
    }
}
