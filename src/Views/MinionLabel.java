package Views;

import Models.Cards.Minion;
import Models.Images.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class MinionLabel extends JLabel {

    private Toolkit tk = Toolkit.getDefaultToolkit();

    public MinionLabel(int size, int player, int i, Minion minion) {
        super(new ImageIcon(ImageLoader.minionsImage[minion.id]));
        if(player == 1){
            setBounds(tk.getScreenSize().width / 2 - size * (tk.getScreenSize().width / 8 -
                            tk.getScreenSize().width / 40) / 2 + i * (tk.getScreenSize().width / 8 - tk.getScreenSize().width / 40)
                    , tk.getScreenSize().height / 2 - 50, tk.getScreenSize().width / 8 - tk.getScreenSize().width / 40
                    , tk.getScreenSize().height / 4 - tk.getScreenSize().height / 30);
        } else {
            setBounds(tk.getScreenSize().width / 2 - size * (tk.getScreenSize().width / 8 -
                            tk.getScreenSize().width / 40) / 2 + i * (tk.getScreenSize().width / 8 - tk.getScreenSize().width / 40)
                    , tk.getScreenSize().height / 2 - 200, tk.getScreenSize().width / 8 - tk.getScreenSize().width / 40
                    , tk.getScreenSize().height / 4 - tk.getScreenSize().height / 30);
        }
        JLabel health = new JLabel(String.valueOf(minion.getHp()));
        health.setFont(new Font("hello", Font.BOLD, 24));
        health.setForeground(Color.WHITE);
        health.setSize(50, 50);
        health.setLocation(110, tk.getScreenSize().height / 4 - tk.getScreenSize().height / 8);
        this.add(health);

        JLabel attack = new JLabel(String.valueOf(minion.getAttack()));
        attack.setFont(new Font("hello", Font.BOLD, 24));
        attack.setForeground(Color.WHITE);
        attack.setSize(50, 50);
        attack.setLocation(33, tk.getScreenSize().height / 4 - tk.getScreenSize().height / 8);
        this.add(attack);
    }

    public void addDivine(){
        JLabel divine = new JLabel(new ImageIcon(ImageLoader.divine));
        divine.setSize(tk.getScreenSize().width / 8 - tk.getScreenSize().width / 40
                , tk.getScreenSize().height / 4 - tk.getScreenSize().height / 30);
        divine.setLocation(0, 0);
        this.add(divine);
    }

    public void addTaunt(){
        JLabel taunt = new JLabel(new ImageIcon(ImageLoader.taunt));
        taunt.setSize(100 , 100);
        taunt.setLocation(0, 0);
        this.add(taunt);
    }

}
