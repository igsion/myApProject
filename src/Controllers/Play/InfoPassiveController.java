package Controllers.Play;

import Models.States.PlayState;
import Models.States.State;
import Views.Display;
import Views.PlayPanels.PlayPanel;
import com.google.gson.Gson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPassiveController implements ActionListener {

    private boolean isInfoChosen = false;
    private Gson gson = new Gson();
    private boolean isRunning = false;

    @Override
    public void actionPerformed(ActionEvent e) {
        PlayState playState = (PlayState) State.getState();
        if(e.getActionCommand().equals("playButton")){
            if(isInfoChosen){
                Display.getDisplay().changePage("play");
                if(!isRunning){
                    isRunning = true;
                    playState.start();
                    PlayPanel.getPlayPanel().setRunning(true);
                }
                PlayPanel.getPlayPanel().updateState(gson.toJson(playState));
                PlayPanel.getPlayPanel().requestFocus();
                PlayPanel.getPlayPanel().requestFocusInWindow();
            }
        }else {
            String str = playState.chooseInfoPassive(e.getActionCommand());
            isInfoChosen = true;
        }
    }
}
