package Views.PlayPanels;

import Models.Cards.Card;
import Views.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPassivePanel extends JPanel {

    private JPanel infoPanel, playPanel, exitPanel;
    private Card[] passives;

    private static InfoPassivePanel infoPassivePanel = new InfoPassivePanel();

    private InfoPassivePanel(){
        passives = new Card[0];

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

        JButton jLabel = new JButton("Number 1");
        jLabel.setBackground(Color.CYAN);
        jLabel.setPreferredSize(new Dimension(240 , 400));

        JButton jLabel1 = new JButton("Number 2");
        jLabel1.setBackground(Color.CYAN);
        jLabel1.setPreferredSize(new Dimension(240 , 400));

        JButton jLabel2 = new JButton("Number 3");
        jLabel2.setBackground(Color.CYAN);
        jLabel2.setPreferredSize(new Dimension(240 , 400));

        infoPanel.add(jLabel);
        infoPanel.add(jLabel1);
        infoPanel.add(jLabel2);

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
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.getDisplay().changePage("play");
                PlayPanel.getPlayPanel().requestFocus();
                PlayPanel.getPlayPanel().requestFocusInWindow();
            }
        });

        playPanel.add(Box.createRigidArea(new Dimension(0 , 200)));
        playPanel.add(playButton);
        playPanel.setPreferredSize(new Dimension(300, 0));
        playPanel.setBackground(new Color(250 , 250 , 150));

        add(exitPanel, BorderLayout.NORTH);
        add(playPanel, BorderLayout.EAST);
        add(infoPanel, BorderLayout.CENTER);
    }

    public void updatePassives(Card[] passives){
        this.passives = passives;
        for(Card card : passives){

        }
    }

    public static InfoPassivePanel getInfoPassivePanel(){
        return infoPassivePanel;
    }
}
