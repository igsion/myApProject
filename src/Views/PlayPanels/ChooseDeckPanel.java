package Views.PlayPanels;

import Views.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChooseDeckPanel extends JPanel {

    private ArrayList<String> decks = new ArrayList<>(0);
    private JPanel deckPanel, playPanel, exitPanel;

    private static ChooseDeckPanel chooseDeckPanel = new ChooseDeckPanel();

    private ChooseDeckPanel(){
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
                Display.getDisplay().changePage("menu");
            }
        });
        exitPanel.add(backButton);

        deckPanel = new JPanel();

        FlowLayout fl = new FlowLayout();
        fl.setHgap(50);
        deckPanel.setLayout(fl);

        playPanel = new JPanel();
        playPanel.setLayout(new BoxLayout(playPanel, BoxLayout.PAGE_AXIS));

        JButton playButton = new JButton("Continue");
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
                Display.getDisplay().changePage("choosePassive");
            }
        });

        playPanel.add(Box.createRigidArea(new Dimension(0 , 200)));
        playPanel.add(playButton);
        playPanel.setPreferredSize(new Dimension(300, 0));
        playPanel.setBackground(Color.GRAY);

        add(exitPanel, BorderLayout.NORTH);
        add(deckPanel, BorderLayout.CENTER);
        add(playPanel, BorderLayout.EAST);


        updateDecks();
    }

    public void updateDecks(){
        decks.add("Hello");
        decks.add("How are you");
        decks.add("Im fine");
        deckPanel.removeAll();

        deckPanel.add(Box.createRigidArea(new Dimension(0 , 200)));

        for(String str : decks){
            JButton deckButton = new JButton(str);
            deckButton.setFont(new Font("myFont" , Font.PLAIN , 20));
            deckButton.setBorder(null);
            deckButton.setBorder(BorderFactory.createEmptyBorder(10 , 60 , 10,60));
            deckButton.setBackground(Color.CYAN);
            deckPanel.add(deckButton);
        }
    }

    public static ChooseDeckPanel getChooseDeckPanel(){
        return chooseDeckPanel;
    }
}
