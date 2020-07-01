package Views.PlayPanels;

import Models.Images.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayPanel extends JPanel {

    private static PlayPanel playPanel = new PlayPanel();

    private PlayPanel() {
        setLayout(null);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.out.println(55);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void paintComponent(Graphics g) {
        g.drawImage(ImageLoader.playground, 0, 0, null);
    }

    public static PlayPanel getPlayPanel() {
        return playPanel;
    }

    private class PlayMenu extends JPanel {

    }
}
