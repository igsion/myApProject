package Controllers.Collection;

import Models.States.CollectionState;
import Models.States.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassChooseController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String hero = e.getActionCommand();
        CollectionState state = (CollectionState) State.getState();
        String name = JOptionPane.showInputDialog("Choose a name for deck");
        state.createDeck(name , hero);
    }

}
