package Models.Hero;

import Models.Interfaces.Observer;
import Models.States.State;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SpecialPower implements Observer {

    private JsonObject mechanic;

    SpecialPower(JsonObject mechanic){
        this.mechanic = mechanic;
    }

    @Override
    public void update(String event, int player) {
        if (mechanic.get(event) != null) {
            for (Map.Entry m : mechanic.get(event).getAsJsonObject().entrySet()) {
                Class s = State.getState().getClass();
                for (Method method : s.getDeclaredMethods()) {
                    if (method.getName().equals(m.getKey())) {
                        try {
                            JsonArray a = (JsonArray) m.getValue();
                            if (a.size() > 0) {
                                Object[] parametrs = new Object[a.size() + 1];
                                parametrs[0] = player;
                                if (a.get(0).getAsJsonPrimitive().isString() && a.get(0).getAsString().equals("this")) {
                                    parametrs[1] = this;
                                    for (int i = 1; i < a.size(); i++) {
                                        if (a.get(i).getAsJsonPrimitive().isNumber()) {
                                            parametrs[i + 1] = a.get(i).getAsInt();
                                        } else if (a.get(i).getAsJsonPrimitive().isBoolean()) {
                                            parametrs[i + 1] = a.get(i).getAsBoolean();
                                        } else if (a.get(i).getAsJsonPrimitive().isString()) {
                                            parametrs[i + 1] = a.get(i).getAsString();
                                        }
                                    }
                                } else {
                                    for (int i = 0; i < a.size(); i++) {
                                        if (a.get(i).getAsJsonPrimitive().isNumber()) {
                                            parametrs[i + 1] = a.get(i).getAsInt();
                                        } else if (a.get(i).getAsJsonPrimitive().isBoolean()) {
                                            parametrs[i + 1] = a.get(i).getAsBoolean();
                                        } else if (a.get(i).getAsJsonPrimitive().isString()) {
                                            parametrs[i + 1] = a.get(i).getAsString();
                                        }
                                    }
                                }
                                method.invoke(State.getState(), parametrs);
                            } else {
                                method.invoke(State.getState(), player);
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public JsonObject getMechanic() {
        return mechanic;
    }
}
