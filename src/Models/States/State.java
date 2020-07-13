package Models.States;

import Models.Game;

public abstract class State {

    transient Game game;
    private transient static State currentState = null;

    State(Game game){
        this.game = game;
    }

    public static void setState(State state){
        currentState = state;
    }

    public static State getState(){
        return currentState;
    }
}
