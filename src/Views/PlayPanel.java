package Views;

import Models.Images.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {

    private static PlayPanel playPanel = new PlayPanel();

    private PlayPanel() {
        setLayout(null);
    }

    public void paintComponent(Graphics g){
        g.drawImage(ImageLoader.playground , 0 , 0 , null);
    }

    public static PlayPanel getPlayPanel() {
        return playPanel;
    }
}
