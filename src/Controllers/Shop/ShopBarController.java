package Controllers.Shop;

import Models.States.CollectionState;
import Models.States.ShopState;
import Models.States.State;
import Views.CollectionPanel;
import Views.Display;
import Views.ShopPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopBarController implements ActionListener {

    ShopState shopState;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("back")){
            Display.getDisplay().changePage("menu");
            if(shopState == null) {
                shopState = (ShopState) State.getState();
            }
            shopState.changeState();
        }else if(e.getActionCommand().equals("exit")){
            System.exit(1);
        }else{
            ShopPanel.getShopPanel().updateCardPanel(e.getActionCommand().toLowerCase());
        }
    }
}
