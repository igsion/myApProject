package Models.ABC;

import Models.Cards.Minion;

public interface Observer {
    public void update(String event);
    public void update(String event, Minion minion);
}
