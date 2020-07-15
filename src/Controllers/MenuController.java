package Controllers;

import Controllers.Play.InfoPassiveController;
import Models.States.MenuState;
import Models.States.PlayState;
import Models.States.State;
import Views.Display;
import Views.PlayPanels.ChooseDeckPanel;
import Views.PlayPanels.InfoPassivePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController implements ActionListener {

    private JButton shopButton, statusButton, collectionButton, settingButton, playButton, playDeckReaderButton;
    private MenuState menuState;

    public MenuController(JButton shopButton, JButton statusButton ,JButton collectionButton ,
                          JButton settingButton ,JButton playButton, JButton playDeckReaderButton){
        this.playDeckReaderButton = playDeckReaderButton;
        this.shopButton = shopButton;
        this.statusButton = statusButton;
        this.collectionButton = collectionButton;
        this.settingButton = settingButton;
        this.playButton = playButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == playDeckReaderButton){
            if(menuState == null) {
                menuState = (MenuState) State.getState();
                menuState.changeState("play");
            }else{
                menuState.changeState("play");
            }
            PlayState playState = (PlayState) State.getState();
            InfoPassivePanel.getInfoPassivePanel().updatePassives(playState.generateRandomPassives());
            InfoPassiveController.isDeckReader = true;
            Display.getDisplay().changePage("choosePassive");
        }

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
