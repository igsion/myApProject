package Views;

import Models.Deck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StatusPanel extends JPanel{

    private static StatusPanel statusPanel = new StatusPanel();

    private StatusPanel(){
        setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
    }

    public void updateStatus(ArrayList<Deck> decks){
        removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel , BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panel);

        int i = 1;

        for(Deck deck : decks){
            JLabel number = new JLabel(i + " :");
            panel.add(number);
            JLabel name = new JLabel("Deck Name : " + deck.getName());
            panel.add(name);
            JLabel winrate ;
            if(deck.getTotal() > 0) {
                winrate = new JLabel("Win Rate : " + deck.getWin() / deck.getTotal());
            }else{
                winrate = new JLabel("Win Rate : " + 0);
            }
            panel.add(winrate);
            JLabel win = new JLabel("Total Wins : " + deck.getWin());
            panel.add(win);
            JLabel total = new JLabel("Total Games : " + deck.getTotal());
            panel.add(total);
            JLabel average = new JLabel("Average Cost : " + deck.averageCost() );
            panel.add(average);
            JLabel hero = new JLabel("Hero : " + deck.getHero());
            panel.add(hero);
            JLabel mostPlayed = new JLabel("Most Played Card : " + deck.mostPlayedCard());
            panel.add(mostPlayed);
            i++;
        }

        add(scrollPane);

        repaint();
        revalidate();
    }

    public static StatusPanel getStatusPanel(){
        return statusPanel;
    }
}
