package Views.PlayPanels;

import Controllers.Play.InfoPassiveController;
import Models.InfoPassive;
import Views.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPassivePanel extends JPanel {

    private JPanel infoPanel, playPanel, exitPanel;
    private InfoPassiveController infoPassiveController;

    private static InfoPassivePanel infoPassivePanel = new InfoPassivePanel();

    private InfoPassivePanel(){
        infoPassiveController = new InfoPassiveController();

        setLayout(new BorderLayout());

        exitPanel = new JPanel();
        exitPanel.setLayout(null);
        exitPanel.setPreferredSize(new Dimension(0 , 50));

        JButton backButton = new JButton("Back");
        backButton.setLocation(10 , 5);
        backButton.setSize(new Dimension(100 , 40));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(null);
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.getDisplay().changePage("chooseDeck");
            }
        });
        exitPanel.add(backButton);

        infoPanel = new JPanel();
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.CENTER);
        fl.setHgap(100);
        fl.setVgap(150);
        infoPanel.setLayout(fl);
        infoPanel.setBackground(new Color(250 , 150 , 150));

        playPanel = new JPanel();
        playPanel.setLayout(new BoxLayout(playPanel, BoxLayout.PAGE_AXIS));

        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("MyFont" , Font.PLAIN , 22));
        playButton.setBackground(new Color(238, 206, 149));
        playButton.setForeground(Color.WHITE);
        playButton.setBorder(null);
        playButton.setFocusPainted(false);
        playButton.setMaximumSize(new Dimension(125,  50));
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setActionCommand("playButton");
        playButton.addActionListener(infoPassiveController);

        playPanel.add(Box.createRigidArea(new Dimension(0 , 200)));
        playPanel.add(playButton);
        playPanel.setPreferredSize(new Dimension(300, 0));
        playPanel.setBackground(new Color(250 , 250 , 150));

        add(exitPanel, BorderLayout.NORTH);
        add(playPanel, BorderLayout.EAST);
        add(infoPanel, BorderLayout.CENTER);
    }

    public void updatePassives(InfoPassive[] infoes){
        infoPanel.removeAll();

        for(InfoPassive info : infoes){
            JButton infoButton = new JButton("<html>" + info.getName() + "<br>" + info.getDescription() + "<html>");
            infoButton.setBackground(Color.CYAN);
            infoButton.setPreferredSize(new Dimension(240 , 400));
            infoButton.addActionListener(infoPassiveController);
            infoButton.setActionCommand(info.getName());

            infoPanel.add(infoButton);
        }
    }

    public static InfoPassivePanel getInfoPassivePanel(){
        return infoPassivePanel;
    }
}
