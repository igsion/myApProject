package Controllers.Play;

import Models.Cards.Card;
import Models.States.PlayState;
import Models.States.State;
import Views.PlayPanels.PlayPanel;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandlerCard implements MouseListener, MouseMotionListener {
    private JLabel label;
    private Card card;
    private Gson gson = new Gson();
    private int x, y;
    private int startingX = 0, startingY = 0;
    private int startX = 0, startY = 0;
    private int player;
    private volatile boolean isCardMoving = false;
    private Toolkit tk = Toolkit.getDefaultToolkit();

    public MouseHandlerCard(JLabel label, Card card, int player) {
        this.label = label;
        this.card = card;
        this.player = player;
        y = label.getY();
        x = label.getX();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!isCardMoving) {
            isCardMoving = true;
            startingX = MouseInfo.getPointerInfo().getLocation().x;
            startingY = MouseInfo.getPointerInfo().getLocation().y;
            startX = x + e.getX() + 5;
            startY = y + e.getY() - 145;
            label.setLocation(x + e.getX() + 5, y + e.getY() - 145);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isCardMoving) {
            if(MouseInfo.getPointerInfo().getLocation().x > tk.getScreenSize().width/8
                    && MouseInfo.getPointerInfo().getLocation().x <  tk.getScreenSize().width/8 + tk.getScreenSize().width/5 * 4
                    && MouseInfo.getPointerInfo().getLocation().y > tk.getScreenSize().height/4
                    && MouseInfo.getPointerInfo().getLocation().y <  tk.getScreenSize().height/4 + tk.getScreenSize().height/2){
                PlayState playState = (PlayState) State.getState();
                playState.playCard(card, player);
                PlayPanel.getPlayPanel().updateState(gson.toJson(playState));
            }
            label.setLocation(this.x, this.y);
            isCardMoving = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(!isCardMoving){
            label.setLocation(label.getX(), label.getY() - 150);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(!isCardMoving){
            label.setLocation(label.getX(), label.getY() + 150);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isCardMoving) {
            label.setLocation(startX + MouseInfo.getPointerInfo().getLocation().x - startingX,
                    startY + MouseInfo.getPointerInfo().getLocation().y - startingY);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}