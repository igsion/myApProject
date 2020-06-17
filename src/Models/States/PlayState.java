package Models.States;

import Models.Cards.Card;
import Models.Cards.Minion;
import Models.Game;
import Models.Player;

import java.util.ArrayList;
import java.util.Random;

public class PlayState extends State {

    private int turn;
    private boolean isOneTurn , isTwoTurn;
    private Player one , two;
    private ArrayList<Card> handOne , handTwo;
    private ArrayList<Card> deckOne , deckTwo;
    private ArrayList<Minion> minionOne , minionTwo;
    private int manaOne , manaTwo , manaTurnOne , manaTurnTwo;
    private Random random;

    public PlayState(Game game){
        super(game);
        minionOne = new ArrayList<>(0);
        minionTwo = new ArrayList<>(0);
        handOne = new ArrayList<>(0);
        handTwo = new ArrayList<>(0);
        deckOne = new ArrayList<>(0);
        deckTwo = new ArrayList<>(0);
        isOneTurn = true;
        isTwoTurn = false;
        random = new Random();
        turn = 0;
    }

    public void startPlay(){
        one = this.game.getPlayer();
        deckOne.addAll(this.game.getDeck().getCards());
        drawCard(deckOne);
        drawCard(deckOne);
        drawCard(deckOne);
    }

    public String playMinion(Minion minion){
        if(minion.mana <= manaOne) {
            if (minionOne.size() < 7) {
                manaTurnOne -= minion.mana;
                minionOne.add(minion);
                return "Successful";
            } else {
                return "Board is full";
            }
        }else{
            return "You don't have the mana";
        }
    }

    public void nextTurn(){
        if(isOneTurn){
            isTwoTurn = true;
            isOneTurn = false;
            if(manaTwo < 10) {
                manaTwo++;
            }
            manaTurnTwo = manaTwo;
        }else{
            isOneTurn = true;
            isTwoTurn = false;
            if(manaOne < 10){
                manaOne++;
            }
            manaTurnOne = manaOne;
        }
        if(isOneTurn){
            drawCard(deckOne);
        }
        turn++;
    }

    public void drawCard(ArrayList<Card> cards){
        int i = random.nextInt(cards.size());
        handOne.add(cards.get(i));
        cards.remove(i);
        cards.trimToSize();
    }
}