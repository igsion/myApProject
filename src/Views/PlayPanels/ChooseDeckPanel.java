package Views.PlayPanels;

import Controllers.Play.DeckChooseController;
import Models.Deck;
import Views.Display;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChooseDeckPanel extends JPanel {

    private Gson gson;
    private ArrayList<Deck> decks = new ArrayList<>(0);
    private JPanel deckPanel, playPanel, exitPanel;
    private DeckChooseController deckChooseController;

    private static ChooseDeckPanel chooseDeckPanel = new ChooseDeckPanel();

    private ChooseDeckPanel(){
        gson = new Gson();

        deckChooseController = new DeckChooseController();

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
        playButton.setActionCommand("passiveInfoPanel");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        playButton.addActionListener(deckChooseController);

        playPanel.add(Box.createRigidArea(new Dimension(0 , 200)));
        playPanel.add(playButton);
        playPanel.setPreferredSize(new Dimension(300, 0));
        playPanel.setBackground(Color.GRAY);

        add(exitPanel, BorderLayout.NORTH);
        add(deckPanel, BorderLayout.CENTER);
        add(playPanel, BorderLayout.EAST);
    }

    public void updateDecks(String decks){
        deckPanel.removeAll();

        deckPanel.add(Box.createRigidArea(new Dimension(0 , 200)));

        this.decks = gson.fromJson(decks, new TypeToken<ArrayList<Deck>>(){}.getType());

        for(Deck deck : this.decks){
            JButton deckButton = new JButton(deck.getName());
            deckButton.setFont(new Font("myFont" , Font.PLAIN , 20));
            deckButton.setBorder(null);
            deckButton.setActionCommand(deck.getName());
            deckButton.setBorder(BorderFactory.createEmptyBorder(10 , 60 , 10,60));
            deckButton.setBackground(Color.CYAN);
            deckButton.addActionListener(deckChooseController);
            deckPanel.add(deckButton);
        }
    }

    public static ChooseDeckPanel getChooseDeckPanel(){
        return chooseDeckPanel;
    }
}
