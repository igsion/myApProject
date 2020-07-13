package Controllers.Collection;

import Models.States.CollectionState;
import Models.States.State;
import Views.CollectionPanel;
import Views.Display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BarController implements ActionListener {

    CollectionState collectionState;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("back")){
            if(collectionState == null) {
                collectionState = (CollectionState) State.getState();
            }
            collectionState.changeState();
            Display.getDisplay().changePage("menu");
        }else if(e.getActionCommand().equals("exit")){
            System.exit(1);
        }else{
            CollectionPanel.getCollectionPanel().updateCardPanel(e.getActionCommand().toLowerCase());
        }
    }
}
