package Models.Cards;

import Models.ABC.Observer;
import Models.ABC.Targetable;
import Models.States.PlayState;
import Models.States.State;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class Minion extends Card implements Observer, Targetable {

    private String race;
    private ArrayList<String> attributes;
    private JsonObject mechanic;
    private int hp;
    private int attack;

    public Minion(String name, String hero, int mana, String rarity, String description, String type,
                  String race, JsonObject mechanic, int attack, int hp, int id) {
        this.race = race;
        this.hp = hp;
        this.attack = attack;
        this.mechanic = mechanic;
        this.mana = mana;
        this.hero = hero;
        this.name = name;
        this.rarity = rarity;
        this.description = description;
        this.type = type;
        this.id = id;
        this.attributes = new ArrayList<>(0);
    }

    public String getRace() {
        return race;
    }

    public JsonObject getMechanic() {
        return mechanic;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    @Override
    public void update(String event) {
        if (mechanic.get(event) != null) {
            for (Map.Entry m : mechanic.get(event).getAsJsonObject().entrySet()) {
                Class s = State.getState().getClass();
                for (Method method : s.getDeclaredMethods()) {
                    if (method.getName().equals(m.getKey())) {
                        try {
                            JsonArray a = (JsonArray) m.getValue();
                            Object[] parametrs;
                            if (a.get(0).getAsString().equals("this")) {
                                parametrs = new Object[a.size()];
                                parametrs[0] = this;
                                for (int i = 1; i < a.size(); i++) {
                                    parametrs[i] = a.get(i).getAsInt();
                                }
                            } else {
                                parametrs = new Object[a.size() - 1];
                                for (int i = 0; i < a.size() - 1; i++) {
                                    parametrs[i] = a.get(i).getAsInt();
                                }
                            }
                            method.invoke(State.getState(), parametrs);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update(String event, Minion minion) {
        if (mechanic.get(event) != null) {
            if (mechanic.get(event).isJsonObject()) {
                for (Map.Entry m : mechanic.get(event).getAsJsonObject().entrySet()) {
                    Class s = State.getState().getClass();
                    for (Method method : s.getDeclaredMethods()) {
                        if (method.getName().equals(m.getKey())) {
//                            try {
                            JsonArray a = (JsonArray) m.getValue();
                            System.out.println(a);
//                                method.invoke();
//                            } catch (IllegalAccessException | InvocationTargetException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }
                }
            } else {

            }
        }
    }
}
