package Views;

import Controllers.Collection.*;
import Models.Cards.Card;
import Models.Deck;
import Models.FileManagers.CardsFileManager;
import Models.FileManagers.HeroesFileManager;
import Models.Hero.Hero;
import Models.Images.ImageLoader;
import Models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class CollectionPanel extends JPanel {

    private Set<Card> cardSet;
    private Set<Hero> heroSet;
    private JPanel classPanel, cardPanel, mainCardPanel , deckPanel, filterPanel ;
    private JPanel[] cardPanels;
    private CardLayout cardLayout;
    private GridBagConstraints gbc;

    private static CollectionPanel collectionPanel = new CollectionPanel();

    private CollectionPanel() {
        setLayout(new BorderLayout());

        cardSet = CardsFileManager.getCardsFileManager().getCardsSet();
        heroSet = HeroesFileManager.getHeroesFileManager().getHeroesSet();

        // ------- Class Bar Panel -------

        classPanel = new JPanel();
        classPanel.setBackground(Color.BLACK);
        classPanel.setLayout(new GridLayout(1, 0));

        BarController barController = new BarController();

        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("exit");
        exitButton.addActionListener(barController);
        exitButton.setHorizontalAlignment(JLabel.CENTER);
        classPanel.add(exitButton);

        JButton backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(barController);
        backButton.setHorizontalAlignment(JLabel.CENTER);
        classPanel.add(backButton);

        for (Hero hero : heroSet) {
            JButton label1 = new JButton(hero.getName());
            label1.setActionCommand(hero.getName());
            label1.addActionListener(barController);
            label1.setOpaque(true);
            label1.setBackground(Color.YELLOW);
            label1.setHorizontalAlignment(JLabel.CENTER);
            classPanel.add(label1);
        }

        JButton label1 = new JButton("Neutral");
        label1.setActionCommand("neutral");
        label1.addActionListener(barController);
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


        // ------- Decks Panel -------

        deckPanel = new JPanel();
        deckPanel.setBackground(Color.YELLOW);
        deckPanel.setLayout(new GridBagLayout());

        // ------------------ Layout Config ------------------

        gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0;
        gbc.weightx = 1;

        // ------------------ /Layout Config ------------------

        add(deckPanel, BorderLayout.EAST);

        // ------- Filter Panel -------

        filterPanel = new JPanel();
        filterPanel.setBackground(Color.BLUE);

        FlowLayout fl = new FlowLayout(0);
        filterPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        filterPanel.setLayout(fl);

        JLabel searchLabel = new JLabel("Search :");
        searchLabel.setForeground(Color.WHITE);
        filterPanel.add(searchLabel);

        JTextField searchField = new JTextField("");
        searchField.setPreferredSize(new Dimension(100 , 30));
        searchField.setBorder(BorderFactory.createEmptyBorder());
        filterPanel.add(searchField);

        JLabel manaLabel = new JLabel("Mana :");
        manaLabel.setForeground(Color.WHITE);
        filterPanel.add(manaLabel);

        JComboBox manaFilter = new JComboBox();
        manaFilter.addItem("Any");
        for(int i = 0 ; i <= 10 ; i++){
            manaFilter.addItem(i);
        }
        filterPanel.add(manaFilter);

        JCheckBox availableCards = new JCheckBox("Available Cards");
        filterPanel.add(availableCards);
        JCheckBox unavailableCards = new JCheckBox("Unavailable Cards");
        filterPanel.add(unavailableCards);

        FilterController filterController = new FilterController(manaFilter ,availableCards
                ,unavailableCards ,searchField);

        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(filterController);
        filterButton.setPreferredSize(new Dimension(70 , 30));
        filterButton.setBorder(BorderFactory.createEmptyBorder());
        filterPanel.add(filterButton);

        add(filterPanel, BorderLayout.SOUTH);
    }

    public void updateCardPanel(String hero) {
        cardPanel.removeAll();

        CardController cardController = new CardController();

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
        }else if(i == 1){

        }else{
            cardPanel.add(cardPanels[(i-2)/8]);
        }

        cardPanel.repaint();
        cardPanel.revalidate();
    }

    public void updateDeckPanel(Player player) {

        deckPanel.removeAll();

        // ------------------ Layout Config ------------------

        gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0;
        gbc.weightx = 1;

        // ------------------ /Layout Config ------------------

        int i = 0;

        CurrentDeckController currentDeckController = new CurrentDeckController();

        for (Deck deck : player.getAvailableDecks()) {
            gbc.gridy = i;
            JButton deckButton = new JButton(deck.getName());
            deckButton.setActionCommand(deck.getName());
            deckButton.addActionListener(currentDeckController);
            deckButton.addMouseListener(currentDeckController);
            deckButton.setOpaque(true);
            deckButton.setBackground(Color.RED);
            deckButton.setPreferredSize(new Dimension(100, 50));
            deckPanel.add(deckButton, gbc);
            i++;
        }

        i++;
        gbc.gridy = i;

        DeckController deckController = new DeckController();

        JButton newDeck = new JButton("New Deck");
        newDeck.setActionCommand("newDeck");
        newDeck.addActionListener(deckController);
        newDeck.setOpaque(true);
        newDeck.setBackground(Color.GREEN);
        newDeck.setPreferredSize(new Dimension(150, 50));
        deckPanel.add(newDeck, gbc);

        gbc.weighty = 1;

        deckPanel.add(Box.createGlue(), gbc);

        deckPanel.repaint();
        deckPanel.revalidate();
    }

    public void chooseClass(Player player) {
        deckPanel.removeAll();

        // ------------------ Layout Config ------------------

        gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0;
        gbc.weightx = 1;

        // ------------------ /Layout Config ------------------

        int i = 0;

        ClassChooseController classController = new ClassChooseController();

        for (Hero hero : player.getAvailableHeros()) {
            gbc.gridy = i;
            JButton deckButton = new JButton(hero.getName());
            deckButton.setActionCommand(hero.getName());
            deckButton.addActionListener(classController);
            deckButton.setOpaque(true);
            deckButton.setBackground(Color.RED);
            deckButton.setPreferredSize(new Dimension(150, 50));
            deckPanel.add(deckButton, gbc);
            i++;
        }

        i++;
        gbc.gridy = i;

        gbc.weighty = 1;

        deckPanel.add(Box.createGlue(), gbc);

        deckPanel.repaint();
        deckPanel.revalidate();

    }

    public void deckCreating(Deck deck) {

        deckPanel.removeAll();

        // ------------------ Layout Config ------------------

        gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0;
        gbc.weightx = 1;

        // ------------------ /Layout Config ------------------
        int i = 0;

        DeckController deckController = new DeckController();

        for (Card card : deck.getCards()) {
            gbc.gridy = i;
            JButton deckButton = new JButton(card.name);
            deckButton.setActionCommand(card.name);
            deckButton.addActionListener(deckController);
            deckButton.setOpaque(true);
            deckButton.setBackground(Color.RED);
            deckButton.setPreferredSize(new Dimension(150, 50));
            deckPanel.add(deckButton, gbc);
            i++;
        }

        i++;
        gbc.gridy = i;

        JButton finishButton = new JButton("Finish");
        finishButton.setActionCommand("finish");
        finishButton.addActionListener(deckController);
        finishButton.setOpaque(true);
        finishButton.setBackground(Color.RED);
        finishButton.setPreferredSize(new Dimension(150, 50));
        deckPanel.add(finishButton, gbc);

        i++;
        gbc.gridy = i;

        gbc.weighty = 1;

        deckPanel.add(Box.createGlue(), gbc);

        deckPanel.repaint();
        deckPanel.revalidate();
    }

    public void setCardSet(Set<Card> cards){
        this.cardSet = cards;
    }

    public static CollectionPanel getCollectionPanel() {
        return collectionPanel;
    }
}
