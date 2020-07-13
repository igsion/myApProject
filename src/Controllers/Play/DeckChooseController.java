package Controllers.Play;

import Models.States.PlayState;
import Models.States.State;
import Views.Display;
import Views.PlayPanels.InfoPassivePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeckChooseController implements ActionListener {

    private boolean isDeckChosen = false;

    @Override
    public void actionPerformed(ActionEvent e) {
        PlayState playState = (PlayState) State.getState();
        if (e.getActionCommand().equals("passiveInfoPanel")) {
            if (isDeckChosen) {
                InfoPassivePanel.getInfoPassivePanel().updatePassives(playState.generateRandomPassives());
                Display.getDisplay().changePage("choosePassive");
            }
        } else {
            String deckName = playState.chooseDeck(e.getActionCommand());
            isDeckChosen = true;
        }
    }
}
