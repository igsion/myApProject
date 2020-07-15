package Controllers.Play;

import Models.States.PlayState;
import Models.States.State;
import Views.PlayPanels.PlayPanel;
import com.google.gson.Gson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayController implements ActionListener , MouseListener {

    private Gson gson = new Gson();
    private boolean isRunning = true;

    @Override
    public void actionPerformed(ActionEvent e) {
        PlayState playState = (PlayState) State.getState();
        if(e.getActionCommand().equals("endTurn")){
            playState.nextTurn(1);
            PlayPanel.getPlayPanel().updateState(gson.toJson(playState));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
