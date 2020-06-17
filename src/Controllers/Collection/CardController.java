package Controllers.Collection;

import Models.States.CollectionState;
import Models.States.State;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        CollectionState collectionState = (CollectionState) State.getState();
        if(collectionState.isChoosingDeck()) {
            String cardName = e.getActionCommand();
            collectionState.addCard(cardName);
            collectionState.refreshDeck();
        }
    }
}
