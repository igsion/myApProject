package Views;

import Controllers.Shop.ShopBarController;
import Controllers.Shop.ShopController;
import Models.Cards.Card;
import Models.FileManagers.CardsFileManager;
import Models.FileManagers.HeroesFileManager;
import Models.Hero.Hero;
import Models.Images.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class ShopPanel extends JPanel {

    private Set<Card> cardSet;
    private Set<Hero> heroSet;
    private JPanel classPanel, cardPanel , mainCardPanel , tradePanel;
    private JLabel cardName , cardNameLabel;
    private JButton sellButton , buyButton;
    private JPanel[] cardPanels;
    private CardLayout cardLayout;
    private GridBagConstraints gbc;

    private static ShopPanel shopPanel = new ShopPanel();

    private ShopPanel() {
        setLayout(new BorderLayout());

        cardSet = CardsFileManager.getCardsFileManager().getCardsSet();
        heroSet = HeroesFileManager.getHeroesFileManager().getHeroesSet();

        // ------- Class Bar Panel -------

        classPanel = new JPanel();
        classPanel.setBackground(Color.BLACK);
        classPanel.setLayout(new GridLayout(1, 0));

        ShopBarController shopBarController = new ShopBarController();

        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("exit");
        exitButton.addActionListener(shopBarController);
        exitButton.setHorizontalAlignment(JLabel.CENTER);
        classPanel.add(exitButton);

        JButton backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(shopBarController);
        backButton.setHorizontalAlignment(JLabel.CENTER);
        classPanel.add(backButton);

        for (Hero hero : heroSet) {
            JButton label1 = new JButton(hero.getName());
            label1.setActionCommand(hero.getName());
            label1.addActionListener(shopBarController);
            label1.setOpaque(true);
            label1.setBackground(Color.YELLOW);
            label1.setHorizontalAlignment(JLabel.CENTER);
            classPanel.add(label1);
        }

        JButton label1 = new JButton("neutral");
        label1.setActionCommand("neutral");
        label1.addActionListener(shopBarController);
        label1.setOpaque(true);
        label1.setBackground(Color.YELLOW);
        label1.setHorizontalAlignment(JLabel.CENTER);
        classPanel.add(label1);

        add(classPanel, BorderLayout.NORTH);

        // ------- Cards Panel -------

        mainCardPanel = new JPanel();
        mainCardPanel.setLayout(new BorderLayout());

        cardPanel = new JPanel();
        cardPanel.setBackground(Color.BLACK);
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        JButton next = new JButton("next");
        JButton prev = new JButton("prev");

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.next(cardPanel);
            }
        });

        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.previous(cardPanel);
            }
        });

        cardPanels = new JPanel[6];

        updateCardPanel("mage");

        mainCardPanel.add(cardPanel , BorderLayout.CENTER);
        mainCardPanel.add(next , BorderLayout.EAST);
        mainCardPanel.add(prev , BorderLayout.WEST);
        add(mainCardPanel, BorderLayout.CENTER);

        // ------- Trade Panel -------

        tradePanel = new JPanel();
        tradePanel.setBackground(Color.RED);
        tradePanel.setLayout(new GridLayout(0, 2));

        for(int i = 0 ; i < 10 ; i++){
            JLabel dummyLabel = new JLabel("");
            tradePanel.add(dummyLabel);
        }

        cardNameLabel = new JLabel("Card :");
        cardNameLabel.setHorizontalAlignment(JLabel.CENTER);
        tradePanel.add(cardNameLabel);

        cardName = new JLabel("");
        cardName.setHorizontalAlignment(JLabel.CENTER);
        tradePanel.add(cardName);

        sellButton = new JButton("Sell");
        sellButton.setBackground(Color.WHITE);
        sellButton.setPreferredSize(new Dimension(100 , 50));
        sellButton.setEnabled(false);

        buyButton = new JButton("Buy");
        buyButton.setBackground(Color.WHITE);
        buyButton.setEnabled(false);

        ShopController shopController = new ShopController(sellButton , buyButton , cardName);

        sellButton.addActionListener(shopController);
        buyButton.addActionListener(shopController);
        tradePanel.add(sellButton);
        tradePanel.add(buyButton);

        for(int i = 0 ; i < 10 ; i++){
            JLabel dummyLabel = new JLabel("");
            tradePanel.add(dummyLabel);
        }
        add(tradePanel, BorderLayout.EAST);
    }

    public void updateCardPanel(String hero) {
        cardPanel.removeAll();

        CardHandler cardController = new CardHandler();

        int i = 1;

        for (Card card : cardSet) {
            if (card.hero.equals(hero)) {
                if(i%8 == 1){
                    if(i/8 > 0){
                        cardPanel.add(cardPanels[(i-2)/8]);
                    }
                    cardPanels[i/8] = new JPanel();
                    cardPanels[i/8].setBackground(Color.BLACK);
                    cardPanels[i/8].setLayout(new GridLayout(0, 4));
                }
                JButton cardButton = new JButton(new ImageIcon(ImageLoader.cardsImage[card.id]));
                cardButton.setBorderPainted(false);
                cardButton.setBorder(null);
                cardButton.setMargin(new Insets(0, 0, 0, 0));
                cardButton.setContentAreaFilled(false);
                cardButton.addActionListener(cardController);
                cardButton.setActionCommand(card.name);
                cardButton.setHorizontalAlignment(JLabel.CENTER);
                cardPanels[(i-1)/8].add(cardButton);
                i++;
            }
        }

        if(i%8 != 1){
            for(int j = 0 ; j <= 8 - i ; j++){
                cardPanels[i/8].add(new JLabel(""));
            }
            cardPanel.add(cardPanels[(i-1)/8]);
        }else if(i != 1){
            cardPanel.add(cardPanels[(i-2)/8]);
        }

        cardPanel.repaint();
        cardPanel.revalidate();
    }

    public static ShopPanel getShopPanel() {
        return shopPanel;
    }

    private class CardHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (Card card : cardSet) {
                if (card.name.equals(e.getActionCommand())) {
                    cardName.setText(card.name);
                    if(card.cost() != 0) {
                        sellButton.setText("Sell +" + card.cost());
                        buyButton.setText("Buy -" + card.cost());
                        sellButton.setEnabled(true);
                        buyButton.setEnabled(true);
                    }else{
                        sellButton.setText("Sell");
                        buyButton.setText("Buy");
                        sellButton.setEnabled(false);
                        buyButton.setEnabled(false);
                    }
                    break;
                }
            }
            cardPanel.revalidate();
            cardPanel.repaint();
        }
    }
}
