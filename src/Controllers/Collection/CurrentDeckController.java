package Controllers.Collection;

import Models.States.CollectionState;
import Models.States.State;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CurrentDeckController implements ActionListener , MouseListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        CollectionState collectionState = (CollectionState) State.getState();
        collectionState.editDeck(e.getActionCommand());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3){

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
