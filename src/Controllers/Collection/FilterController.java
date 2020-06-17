package Controllers.Collection;

import Models.Cards.Card;
import Models.FileManagers.CardsFileManager;
import Models.States.CollectionState;
import Models.States.State;
import Views.CollectionPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class FilterController implements ActionListener {

    private JComboBox manaBox;
    private JCheckBox availableBox, unavailableBox;
    private JTextField searchBox;

    public FilterController(JComboBox manaBox, JCheckBox availableBox, JCheckBox unavailableBox, JTextField searchBox) {
        this.manaBox = manaBox;
        this.availableBox = availableBox;
        this.unavailableBox = unavailableBox;
        this.searchBox = searchBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CollectionState collectionState = (CollectionState) State.getState();
        Set<Card> newCards = CardsFileManager.getCardsFileManager().getCardsSet();
        if (availableBox.isSelected()) {
            newCards = collectionState.availableCards(newCards);
        }
        if (unavailableBox.isSelected()) {
            newCards = collectionState.unavailableCards(newCards);
        }
        if(!manaBox.getSelectedItem().equals("Any")){
            newCards = collectionState.manaCards((int) manaBox.getSelectedItem() , newCards);
        }
        if(!searchBox.getText().equals("")){
            newCards = collectionState.nameSearch(searchBox.getText() , newCards);
        }
        CollectionPanel.getCollectionPanel().setCardSet(newCards);
        CollectionPanel.getCollectionPanel().updateCardPanel("mage");
    }
}
