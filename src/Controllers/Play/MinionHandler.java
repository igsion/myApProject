package Controllers.Play;

import Models.States.PlayState;
import Models.States.State;
import Views.PlayPanels.PlayPanel;
import com.google.gson.Gson;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MinionHandler implements MouseListener {

    private int i, player;
    private Gson gson = new Gson();

    public MinionHandler(int i, int player){
        this.player = player;
        this.i = i;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PlayState playState = (PlayState) State.getState();
        playState.minionSelected(i, player);
        PlayPanel.getPlayPanel().updateState(gson.toJson(playState));
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
