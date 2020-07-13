package Models.Cards;

import java.util.ArrayList;
import java.util.Map;

public class Mechanic {

    private String event;
    private Map<String, Integer> actions;
    private ArrayList<Integer> attacks = new ArrayList<>(0);
    private ArrayList<Integer> healths = new ArrayList<>(0);

    public Mechanic(String event) {
        this.event = event;
    }

    public boolean checkEvent(String event) {
        return event.equals(this.event);
    }

    public void addToAction(String method, int i) {
        actions.put(method, i);
    }

    public Map<String, Integer> getActions() {
        return actions;
    }

    public ArrayList<Integer> getAttacks() {
        return attacks;
    }

    public ArrayList<Integer> getHealths() {
        return healths;
    }
}
