package Controllers.Shop;

import Models.FileManagers.CardsFileManager;
import Models.States.ShopState;
import Models.States.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopController implements ActionListener {

    private JButton sellButton , buyButton;
    private JLabel cardName;

    public ShopController(JButton sellButton , JButton buyButton , JLabel cardName){
        this.sellButton = sellButton;
        this.buyButton = buyButton;
        this.cardName = cardName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ShopState state = (ShopState) State.getState();
        if(e.getSource() == sellButton){
            System.out.println(state.sellCard(CardsFileManager.getCardsFileManager().getCard(cardName.getText())));
        }
        if(e.getSource() == buyButton){
            System.out.println(state.buyCard(CardsFileManager.getCardsFileManager().getCard(cardName.getText())));
        }

    }
}
