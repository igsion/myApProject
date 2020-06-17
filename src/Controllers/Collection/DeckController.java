package Controllers.Collection;

import Models.States.CollectionState;
import Models.States.State;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeckController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        CollectionState collectionState = (CollectionState) State.getState();
        if(e.getActionCommand().equals("finish")){
            collectionState.finishDeck();
            return;
        }else if(e.getActionCommand().equals("newDeck")){
            collectionState.newDeckClass();
        }else{
            collectionState.removeCard(e.getActionCommand());
            collectionState.refreshDeck();
        }
    }

}
