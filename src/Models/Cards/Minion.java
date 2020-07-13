package Models.Cards;

import Models.ABC.Observer;
import Models.ABC.Targetable;
import Models.States.PlayState;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Minion extends Card implements Observer, Targetable {

    private String race;
    private String mechanic;
    private ArrayList<String> attributes;
    private Mechanic mech;
    private int hp;
    private int attack;

    public Minion(String name, String hero, int mana, String rarity, String description, String type,
                  String race, String mechanic, int attack, int hp, int id) {
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

    public String getMechanic() {
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

    public ArrayList<String> getAttributes(){
        return attributes;
    }

    @Override
    public void update(Minion minion) {

    }

    @Override
    public void update(String event, Minion minion) {
//        if (mech.checkEvent(event)) {
//            if (minion.equals(this)) {
//                Class playstate = PlayState.getState().getClass();
//                Method[] a = playstate.getMethods();
//                for(Method method : a){
//                    if(method.getName().equals("yo")){
//                        try {
//                            method.invoke(null, mech.getActions());
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
    }
}
