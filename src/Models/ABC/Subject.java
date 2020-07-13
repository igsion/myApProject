package Models.ABC;

import Models.Cards.Minion;

public interface Subject {
    public void attach(Observer o);

    public void detach(Observer o);

    public void notifyUpdate(String event, Minion minion);
}