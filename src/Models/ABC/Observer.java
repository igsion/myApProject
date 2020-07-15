package Models.ABC;

import Models.Cards.Minion;

public interface Observer {
    public void update(String event, int player);
}
