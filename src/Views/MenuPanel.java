package Views;

import Controllers.MenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    private JLabel goldLabel;

    private static MenuPanel menuPanel = new MenuPanel();

    private MenuPanel(){
        setLayout(null);
        Toolkit tk = Toolkit.getDefaultToolkit();

        Color color = new Color(239 , 206 , 129);
        setBackground(color);

        JButton playButton = new JButton("Play");
        JButton statusButton = new JButton("Status");
        JButton shopButton = new JButton("Shop");
        JButton collectionButton = new JButton("Collection");
        JButton settingButton = new JButton("Setting");
        JButton exitButton = new JButton("Exit");
        goldLabel = new JLabel("");

        playButton.setSize(100 , 30);
        statusButton.setSize(100 , 30);
        shopButton.setSize(100 , 30);
        collectionButton.setSize(100 , 30);
        settingButton.setSize(100 , 30);
        exitButton.setSize(100 , 30);
        goldLabel.setSize(100 , 50);

        playButton.setLocation(500 , 350);
        statusButton.setLocation(500 , 400);
        shopButton.setLocation(500 , 450);
        collectionButton.setLocation(500 , 500);
        settingButton.setLocation(500 , 550);
        exitButton.setLocation(0 , 0);
        goldLabel.setLocation(2*tk.getScreenSize().width/3, tk.getScreenSize().height - 50);

        MenuController menuController = new MenuController(shopButton, statusButton, collectionButton
                , settingButton, playButton);

        playButton.addActionListener(menuController);
        statusButton.addActionListener(menuController);
        shopButton.addActionListener(menuController);
        collectionButton.addActionListener(menuController);
        settingButton.addActionListener(menuController);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        add(playButton);
        add(statusButton);
        add(shopButton);
        add(collectionButton);
        add(settingButton);
        add(exitButton);
        add(goldLabel);
    }

    public static MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public void setGoldLabel(int gold){
        goldLabel.setText("Gold : " + gold);
    }
}
