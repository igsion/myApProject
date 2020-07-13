package Views.PlayPanels;

import Controllers.Play.MinionHandler;
import Controllers.Play.MouseHandlerCard;
import Controllers.Play.PlayController;
import Models.Cards.Card;
import Models.Cards.Minion;
import Models.Hero.Hero;
import Models.Images.ImageLoader;
import Models.States.PlayState;
import Views.MinionLabel;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PlayPanel extends JPanel {

    private PlayMenu playMenu;
    private boolean isRunning = false;
    private boolean isOneTurn, isTwoTurn;
    private ArrayList<Card> handOne, handTwo, deckOne, deckTwo;
    private int turn, manaOne, manaTwo, manaTurnOne, manaTurnTwo;
    private ArrayList<Minion> minionOne, minionTwo;
    private Hero heroOne, heroTwo;
    private Toolkit tk = Toolkit.getDefaultToolkit();
    private PlayController playController;
    private int t = 0;

    private static PlayPanel playPanel = new PlayPanel();

    private PlayPanel() {
        setLayout(null);

        playController = new PlayController();

        playMenu = new PlayMenu();
        add(playMenu);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    playMenu.changeVisibility();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void updateState(String currentState) {
        Gson gson = new Gson();
        PlayState playState = gson.fromJson(currentState, PlayState.class);
        handOne = playState.getHandOne();
        handTwo = playState.getHandTwo();
        deckOne = playState.getDeckOne();
        deckTwo = playState.getDeckTwo();
        heroOne = playState.getHeroOne();
        heroTwo = playState.getHeroTwo();
        minionOne = playState.getMinionOne();
        minionTwo = playState.getMinionTwo();
        manaOne = playState.getManaOne();
        manaTwo = playState.getManaTwo();
        manaTurnOne = playState.getManaTurnOne();
        manaTurnTwo = playState.getManaTurnTwo();
        turn = playState.getTurn();
        updateGame();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(ImageLoader.playground, 0, 0, null);
//        g.drawRect(tk.getScreenSize().width/8 , tk.getScreenSize().height/4,
//                tk.getScreenSize().width/5 * 4, tk.getScreenSize().height/2);
//        g.setColor(Color.GREEN);
//        g.drawRect(tk.getScreenSize().width/6 + tk.getScreenSize().width/60 + tk.getScreenSize().width/3 * 2
//                , tk.getScreenSize().height/2 - 60, 140, 50);
    }

    private void updateGame() {
        removeAll();
        if (isRunning) {
            for (int i = 0; i < handOne.size(); i++) {
                JLabel handLabel = new JLabel(new ImageIcon(ImageLoader.cardsImage[handOne.get(i).id]));
                handLabel.setBounds(tk.getScreenSize().width / 2 - 325 + (400/handOne.size() * i)
                        , tk.getScreenSize().height - 125, tk.getScreenSize().width/6 - tk.getScreenSize().width/40
                        , tk.getScreenSize().height/3 - tk.getScreenSize().height/30);
                MouseHandlerCard mouseHandlerCard = new MouseHandlerCard(handLabel, handOne.get(i), 1);
                handLabel.addMouseMotionListener(mouseHandlerCard);
                handLabel.addMouseListener(mouseHandlerCard);
                add(handLabel);
            }
            for (int i = 0; i < handTwo.size(); i++) {
                JLabel handLabel = new JLabel(new ImageIcon(ImageLoader.cardsImage[handTwo.get(i).id]));
                handLabel.setBounds(tk.getScreenSize().width / 2 - 325 + (400/handTwo.size() * i)
                        , -200, tk.getScreenSize().width/6 - tk.getScreenSize().width/40
                        , tk.getScreenSize().height/3 - tk.getScreenSize().height/30);
                MouseHandlerCard mouseHandlerCard = new MouseHandlerCard(handLabel, handTwo.get(i), 2);
                handLabel.addMouseMotionListener(mouseHandlerCard);
                handLabel.addMouseListener(mouseHandlerCard);
                add(handLabel);
            }
            for (int i = 0; i < minionOne.size(); i++) {
                MinionLabel minionLabel = new MinionLabel(minionOne.size(), 1, i, minionOne.get(i));
                MinionHandler handler = new MinionHandler(i, 1);
                minionLabel.addMouseListener(handler);
                add(minionLabel);
            }
            for (int i = 0; i < minionTwo.size(); i++) {
                MinionLabel minionLabel = new MinionLabel(minionTwo.size(), 2, i, minionTwo.get(i));
                MinionHandler handler = new MinionHandler(i, 2);
                minionLabel.addMouseListener(handler);
                add(minionLabel);
            }
            JLabel enemyManaLabel = new JLabel(manaTurnTwo + " / " + manaTwo);
            enemyManaLabel.setBounds(tk.getScreenSize().width / 2 + 250, tk.getScreenSize().height / 30,
                    50, 50);
            enemyManaLabel.setFont(new Font("myFont" , Font.BOLD , 22));
            enemyManaLabel.setForeground(Color.WHITE);
            add(enemyManaLabel);

            JLabel manaLabel = new JLabel(manaTurnOne + " / " + manaOne);
            manaLabel.setBounds(tk.getScreenSize().width / 2 + 285, tk.getScreenSize().height - 89,
                    50, 50);
            manaLabel.setFont(new Font("myFont" , Font.BOLD , 22));
            manaLabel.setForeground(Color.WHITE);
            add(manaLabel);

            JLabel enemyHero = new JLabel(new ImageIcon(ImageLoader.heroesImage[heroTwo.getId()]));
            enemyHero.setLocation(tk.getScreenSize().width/2 - tk.getScreenSize().width / 14 - tk.getScreenSize().width/200,
                    tk.getScreenSize().height / 15);
            enemyHero.setSize(tk.getScreenSize().width/6 , tk.getScreenSize().height/4 - tk.getScreenSize().height/60);
            add(enemyHero);

            JLabel myHero = new JLabel(new ImageIcon(ImageLoader.heroesImage[heroOne.getId()]));
            myHero.setLocation(tk.getScreenSize().width/2 - tk.getScreenSize().width / 14 - tk.getScreenSize().width/200,
                    tk.getScreenSize().height / 4 * 3 - tk.getScreenSize().height / 10);
            myHero.setSize(tk.getScreenSize().width/6 , tk.getScreenSize().height/4 - tk.getScreenSize().height/60);
            add(myHero);

            JButton endTurn = new JButton("");
            endTurn.setBounds(tk.getScreenSize().width/6 + tk.getScreenSize().width/60 + tk.getScreenSize().width/3 * 2
                    , tk.getScreenSize().height/2 - 60, 140, 50);
            endTurn.setContentAreaFilled(false);
            endTurn.setBorder(null);
            endTurn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            endTurn.setActionCommand("endTurn");
            endTurn.addActionListener(playController);
            add(endTurn);
        }
        repaint();
        revalidate();
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public static PlayPanel getPlayPanel() {
        return playPanel;
    }

    private class PlayMenu extends JPanel {

        private final int width = 350;
        private final int height = 300;

        PlayMenu() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
            setSize(new Dimension(width, height));
            setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - width / 2,
                    Toolkit.getDefaultToolkit().getScreenSize().height / 2 - height / 2);
            setBackground(new Color(252, 223, 173));
            setVisible(false);

            add(Box.createRigidArea(new Dimension(10, 50)));

            JButton resume = new JButton("Resume");
            resume.setBorder(null);
            resume.setBackground(Color.ORANGE);
            resume.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PlayPanel.getPlayPanel().requestFocusInWindow();
                    PlayPanel.getPlayPanel().requestFocus();
                    setVisible(false);
                    setRunning(true);
                }
            });
            resume.setPreferredSize(new Dimension(176, 50));

            add(resume);

            JButton setting = new JButton("Setting");
            setting.setBorder(null);
            setting.setBackground(Color.ORANGE);
            setting.setPreferredSize(new Dimension(176, 50));

            add(setting);

            JButton exit = new JButton("Exit");
            exit.setBorder(null);
            exit.setBackground(Color.ORANGE);
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(1);
                }
            });
            exit.setPreferredSize(new Dimension(176, 50));

            add(exit);
        }

        void changeVisibility() {
            if (isVisible()) {
                setVisible(false);
            } else {
                setVisible(true);
            }
        }
    }
}
