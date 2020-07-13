package Views;

import Views.PlayPanels.ChooseDeckPanel;
import Views.PlayPanels.InfoPassivePanel;
import Views.PlayPanels.PlayPanel;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private static Display display = new Display("Hearthstone "
            , tk.getScreenSize().width, tk.getScreenSize().height);

    private Container c;

    private CardLayout card;

    private String title;
    private int width, height;

    private Display(String title, int width, int height) {

        this.title = title;
        this.width = width;
        this.height = height;

        createDisplay();
    }

    private void createDisplay() {
        this.setSize(this.width, this.height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        card = new CardLayout();
        this.setLayout(card);

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle(title);
        this.setUndecorated(true);

        c = this.getContentPane();
        c.add(LoginPanel.getLoginPanel(), "login");
        c.add(MenuPanel.getMenuPanel(), "menu");
        c.add(CollectionPanel.getCollectionPanel(), "collection");
        c.add(ShopPanel.getShopPanel(), "shop");
        c.add(PlayPanel.getPlayPanel(), "play");
        c.add(StatusPanel.getStatusPanel(), "status");
        c.add(ChooseDeckPanel.getChooseDeckPanel(), "chooseDeck");
        c.add(InfoPassivePanel.getInfoPassivePanel(), "choosePassive");

        changePage("login");

        this.setVisible(true);

//        frame.pack();
    }

    public void changePage(String page) {
        card.show(c, page);
    }

    public static Display getDisplay() {
        return display;
    }
}
