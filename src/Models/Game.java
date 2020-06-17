package Models;

import Models.FileManagers.CardsFileManager;
import Models.FileManagers.HeroesFileManager;
import Models.Images.ImageLoader;
import Models.States.*;
import Views.Display;

public class Game implements Runnable {

    private boolean running = false;

    private Thread thread;

    private Player player;
    private Deck deck;

    private PlayState playState;
    private ShopState shopState;
    private LoginState loginState;
    private MenuState menuState;
    private CollectionState collectionState;
    private StatusState statusState;
    private SettingState settingState;

    private void init() {

        CardsFileManager.getCardsFileManager().addCards();
        HeroesFileManager.getHeroesFileManager().addHeroes();

        ImageLoader.loadImages();

        Display.getDisplay();

        loginState = new LoginState(this);
        menuState = new MenuState(this);
        playState = new PlayState(this);
        shopState = new ShopState(this);
        collectionState = new CollectionState(this);
        statusState = new StatusState(this);
        settingState = new SettingState(this);

        State.setState(loginState);
    }

    public void setState(State state){
        State.setState(state);
    }

    public PlayState getPlayState(){
        return playState;
    }

    public LoginState getLoginState() {
        return loginState;
    }

    public MenuState getMenuState(){
        return menuState;
    }

    public ShopState getShopState(){
        return shopState;
    }

    public CollectionState getCollectionState() {
        return collectionState;
    }

    public SettingState getSettingState(){
        return settingState;
    }

    public StatusState getStatusState(){
        return statusState;
    }

    //    private void tick() {
//        if(State.getState() != null){
//            State.getState().tick();
//        }
//    }
//
//    private void render() {
//
////      START DRAWING
//        if(State.getState() != null){
//            State.getState().render(g);
//        }
////      END DRAWING
//    }

    public void run() {

        init();

        double fps = 30;
        double timerPerTick = 1000000000/fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now-lastTime) / timerPerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1) {
//                tick();
//                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000){
//                System.out.println("FPS: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Deck getDeck(){
        return deck;
    }

    public void setDeck(Deck deck){
        this.deck = deck;
    }
}