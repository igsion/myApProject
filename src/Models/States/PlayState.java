package Models.States;

import Models.ABC.Observer;
import Models.ABC.Subject;
import Models.ABC.Targetable;
import Models.Cards.Card;
import Models.Cards.Minion;
import Models.Cards.Quest;
import Models.Cards.Weapon;
import Models.FileManagers.CardsFileManager;
import Models.FileManagers.HeroesFileManager;
import Models.FileManagers.InfoPassiveFileManager;
import Models.Game;
import Models.Hero.Hero;
import Models.InfoPassive;
import Models.Player;

import java.util.*;

public class PlayState extends State implements Subject {

    private int turn;
    private boolean[] isTurn;
    private Player one, two;
    private ArrayList<Card>[] hands;
    private ArrayList<Card>[] decks;
    private ArrayList<Minion>[] boardMinions;
    private int[] mana, manaTurn;
    private Hero[] heroes;
    private Quest questOne, questTwo;
    private Weapon[] weapons;
    private transient ArrayList<Targetable> targets;
    private transient boolean targetSearch = false;
    private transient Minion selectedMinion;
    private transient Map<Minion, Boolean>[] attackMaps;
    private transient ArrayList<Observer> observers = new ArrayList<>();
    private transient Random random;

    public PlayState(Game game) {
        super(game);
        boardMinions = new ArrayList[3];
        boardMinions[1] = new ArrayList<>(0);
        boardMinions[2] = new ArrayList<>(0);
        hands = new ArrayList[3];
        hands[1] = new ArrayList<>(0);
        hands[2] = new ArrayList<>(0);
        decks = new ArrayList[3];
        decks[1] = new ArrayList<>(0);
        decks[2] = new ArrayList<>(0);
        attackMaps = new HashMap[3];
        attackMaps[1] = new HashMap<>();
        attackMaps[2] = new HashMap<>();
        isTurn = new boolean[3];
        isTurn[1] = true;
        isTurn[2] = false;
        weapons = new Weapon[3];
        targets = new ArrayList<>(0);
        random = new Random();
        turn = 0;
        mana = new int[3];
        manaTurn = new int[3];
//        myTimer timer = new myTimer();
//        Thread thread = new Thread(timer);
//        thread.start();
    }

    public void start() {
        one = this.game.getPlayer();
        two = this.game.getPlayer();
        heroes = new Hero[3];
        heroes[1] = HeroesFileManager.getHeroesFileManager().getHero(this.game.getDeck().getHero());
        heroes[2] = HeroesFileManager.getHeroesFileManager().getHero(this.game.getDeck().getHero());
        decks[1].addAll(this.game.getDeck().getCards());
        decks[2].addAll(this.game.getDeck().getCards());
        mana[1] = 1;
        manaTurn[1] = 1;
        mana[2] = 1;
        manaTurn[2] = 1;
        drawCard(decks[1], 1);
        drawCard(decks[1], 1);
        drawCard(decks[1], 1);
        drawCard(decks[1], 2);
        drawCard(decks[1], 2);
        drawCard(decks[1], 2);
    }

    public void shuffle(ArrayList<Card> deck, Card card) {
        deck.add(card);
        hands[1].remove(card);
        drawCard(deck, 1);
    }

    public void playCard(Card card, int player) {
        if (isTurn[player]) {
            switch (card.type) {
                case "minion":
                    playMinion(card, player);
                    break;
                case "spell":
                    playSpell(card, player);
                    break;
                case "weapon":
                    playWeapon(card, player);
                    break;
                case "quest":
                    playQuest(card, player);
                    break;
            }
        }
    }

    private String playMinion(Card card, int player) {
        if (card.mana <= manaTurn[player]) {
            if (boardMinions[player].size() < 7) {
                manaTurn[player] -= card.mana;
                for (int i = 0; i < hands[player].size(); i++) {
                    if (hands[player].get(i).name.equals(card.name)) {
                        hands[player].remove(i);
                        break;
                    }
                }
                Minion minion = CardsFileManager.getCardsFileManager().getMinion(card.name);
                boardMinions[player].add(minion);
                attackMaps[player].put(minion, false);
                attach(minion);
                notifyUpdate("playMinion", minion);
                return "Successful";
            } else {
                return "Board is full";
            }
        } else {
            return "You don't have the mana";
        }
    }

    private String playSpell(Card card, int player) {
        if (card.mana <= manaTurn[player]) {
            manaTurn[player] -= card.mana;
            for (int i = 0; i < hands[player].size(); i++) {
                if (hands[player].get(i).name.equals(card.name)) {
                    hands[player].remove(i);
                    break;
                }
            }
            return "Successful";
        } else {
            return "You don't have the mana";
        }
    }

    private String playWeapon(Card card, int player) {
        if (card.mana <= mana[player]) {
            for (int i = 0; i < hands[player].size(); i++) {
                if (hands[player].get(i).name.equals(card.name)) {
                    hands[player].remove(i);
                    break;
                }
            }
            Weapon weapon = CardsFileManager.getCardsFileManager().getWeapon(card.name);
            weapons[player] = weapon;
            manaTurn[player] -= card.mana;
            return "Successful";
        } else {
            return "You don't have the mana";
        }
    }

    private String playQuest(Card card, int player) {
        if (card.mana <= mana[player]) {
            for (int i = 0; i < hands[player].size(); i++) {
                if (hands[player].get(i).name.equals(card.name)) {
                    hands[player].remove(i);
                    break;
                }
            }
            manaTurn[player] -= card.mana;
            return "Successful";
        } else {
            return "You don't have the mana";
        }
    }

    private void summon(Minion minion, int player) {
        if (boardMinions[player].size() < 7) {
            boardMinions[player].add(minion);
        }
    }

    public void nextTurn() {
        deSelect();
        notifyUpdate("endTurn", null);
        if (isTurn[1]) {
            isTurn[1] = false;
            isTurn[2] = true;
            if (mana[2] < 10) {
                mana[2]++;
            }
            int c = mana[2];
            manaTurn[2] = c;
            drawCard(decks[2], 2);
            for (Map.Entry<Minion, Boolean> entry : attackMaps[1].entrySet()) {
                entry.setValue(false);
            }
            for (Map.Entry<Minion, Boolean> entry : attackMaps[2].entrySet()) {
                entry.setValue(true);
            }
        } else {
            isTurn[1] = true;
            isTurn[2] = false;
            if (mana[1] < 10) {
                mana[1]++;
            }
            int c = mana[1];
            manaTurn[1] = c;
            drawCard(decks[1], 1);
            for (Map.Entry<Minion, Boolean> entry : attackMaps[1].entrySet()) {
                entry.setValue(true);
            }
            for (Map.Entry<Minion, Boolean> entry : attackMaps[2].entrySet()) {
                entry.setValue(false);
            }
        }
        turn++;
        notifyUpdate("startTurn", null);
    }

    private void drawCard(ArrayList<Card> cards, int player) {
        if (cards.size() > 0) {
            int i = random.nextInt(cards.size());
            if (hands[player].size() < 10) {
                hands[player].add(cards.get(i));
                notifyUpdate("", null);
            }
            cards.remove(i);
            cards.trimToSize();
        }
    }

    public void deSelect() {
        targetSearch = false;
        targets.clear();
    }

    public void minionSelected(int i, int player) {
        if (targetSearch) {
            if (this.targets.contains(boardMinions[player].get(i))) {
                minionAttack(this.selectedMinion, boardMinions[player].get(i));
            }
            targetSearch = false;
        } else if (isTurn[player]) {
            this.targets = minionTargets(boardMinions[player].get(i));
            this.selectedMinion = boardMinions[player].get(i);
            targetSearch = true;
        }
    }

    private ArrayList<Targetable> minionTargets(Minion myMinion) {
        ArrayList<Targetable> targetables = new ArrayList<>(0);
        if (boardMinions[1].contains(myMinion)) {
            for (Minion minions : boardMinions[2]) {
                if (minions.getAttributes().contains("taunt")) {
                    targetables.add(minions);
                }
            }
            if (targetables.size() == 0) {
                targetables.addAll(boardMinions[2]);
                targetables.add(heroes[2]);
            }
        } else {
            for (Minion minions : boardMinions[1]) {
                if (minions.getAttributes().contains("taunt")) {
                    targetables.add(minions);
                }
            }
            if (targetables.size() == 0) {
                targetables.addAll(boardMinions[1]);
                targetables.add(heroes[1]);
            }
        }
        return targetables;
    }

//    private Targetable chooseTarget() {
//
//    }

    private void buffMinionAttack() {
//        Minion minion = (Minion) chooseTarget();
//        minion.setAttack(minion.getAttack() + 1);
    }

    private void buffMinionHealth(Minion minion) {
        minion.setHp(minion.getHp() + 1);
    }

    private void weaponAttack(Weapon weapon, Minion defender) {
        damageMinion(weapon.damage, defender);
        if (isOneTurn()) {
            damageHero(defender.getAttack(), heroes[1]);
        } else {
            damageHero(defender.getAttack(), heroes[2]);
        }
        weapon.durability += -1;
    }

    private void weaponAttack(Weapon weapon, Hero hero) {
        damageHero(weapon.damage, hero);
        weapon.durability += -1;
    }

    private void minionAttack(Minion attacker, Minion defender) {
        damageMinion(attacker.getAttack(), defender);
        damageMinion(defender.getAttack(), attacker);
    }

    private void minionAttack(Minion attacker, Hero hero) {
        damageHero(attacker.getAttack(), hero);
    }

    private void damageMinion(int i, Minion minion) {
        if (minion.getHp() - i < 1) {
            minionDie(minion);
        } else {
            minion.setHp(minion.getHp() - i);
        }
    }

    private void damageHero(int i, Hero hero) {
        hero.setHp(hero.getHp() - i);
    }

    private void healMinion(int i, Minion minion) {
        Minion myMinion = CardsFileManager.getCardsFileManager().getMinion(minion.name);
        if (minion.getHp() + i <= myMinion.getHp()) {
            minion.setHp(minion.getHp() + i);
        } else {
            minion.setHp(myMinion.getHp());
        }
    }

    private void healHero(int i, Hero hero) {
        if (hero.getHp() + i <= 30) {
            hero.setHp(hero.getHp() + i);
        } else {
            hero.setHp(30);
        }
    }

    private void damageAllMinions(ArrayList<Minion> exceptions, int damage) {
        for (Minion minion : boardMinions[1]) {
            if (!exceptions.contains(minion)) {
                damageMinion(damage, minion);
            }
        }
    }

    private void damageAllCharacters(ArrayList<Minion> exceptions, int damage) {
        for (Minion minion : boardMinions[1]) {
            if (!exceptions.contains(minion)) {
                damageMinion(damage, minion);
            }
        }
        damageHero(damage, heroes[1]);
        damageHero(damage, heroes[2]);
    }

    private void addRandomCardToHand(String className, int player) {
        Card card = randomClassCard(className);
        if (player == 1) {
            hands[1].add(card);
        } else {
            hands[2].add(card);
        }
    }

    private void minionAttackToHealth(Minion minion) {
        minion.setAttack(minion.getHp());
    }

    private void setMinionHealth(Minion minion, int health) {
        minion.setHp(health);
    }

    private void setHeroHealth(Hero hero, int health) {
        hero.setHp(health);
    }

    private void silenceMinion(Minion minion) {

    }

    private void minionDie(Minion minion) {
        if (boardMinions[1].contains(minion)) {
            boardMinions[1].remove(minion);
        } else {
            boardMinions[2].remove(minion);
        }
    }

    private Card randomClassCard(String className) {
        ArrayList<Card> cards = new ArrayList<>(0);
        for (Card card : CardsFileManager.getCardsFileManager().getCardsSet()) {
            if (card.hero.equals(className)) {
                cards.add(card);
            }
        }
        return cards.get(random.nextInt(cards.size()));
    }

    private void transform(int i, Minion minion) {
        this.boardMinions[1].set(i, minion);
    }

    private void discard(Card card, ArrayList<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).name.equals(card.name)) {
                hand.remove(i);
                break;
            }
        }
    }

    // ChooseDeckPanel And ChooseDeckPassive

    public String chooseDeck(String deckName) {
        this.game.setDeck(this.game.getPlayer().getDeck(deckName));
        return this.game.getDeck().getName();
    }

    public String chooseInfoPassive(String infoPassive) {
        this.game.setInfoPassive(InfoPassiveFileManager.getInfoPassiveFileManager().getInfo(infoPassive));
        return this.game.getInfoPassive().getName();
    }

    public InfoPassive[] generateRandomPassives() {
        InfoPassive[] myPassives = new InfoPassive[3];
        int x = 0, y = 0, z = 0;
        x = (random.nextInt(4) + 1);
        y = (random.nextInt(5 - x) + 1);
        z = (random.nextInt(6 - y - x) + 1);
        myPassives[0] = InfoPassiveFileManager.getInfoPassiveFileManager().getInfo(x);
        myPassives[1] = InfoPassiveFileManager.getInfoPassiveFileManager().getInfo(x + y);
        myPassives[2] = InfoPassiveFileManager.getInfoPassiveFileManager().getInfo(x + y + z);
        return myPassives;
    }

    // Subject Interface Methods

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyUpdate(String event, Minion minion) {
        for (Observer o : observers) {
            o.update(event, minion);
        }
    }

    // GETTERS

    public int getTurn() {
        return turn;
    }

    public boolean isOneTurn() {
        return isTurn[1];
    }

    public boolean isTwoTurn() {
        return isTurn[2];
    }

    public Player getOne() {
        return one;
    }

    public Player getTwo() {
        return two;
    }

    public ArrayList<Card> getHandOne() {
        return hands[1];
    }

    public ArrayList<Card> getHandTwo() {
        return hands[2];
    }

    public ArrayList<Card> getDeckOne() {
        return decks[1];
    }

    public ArrayList<Card> getDeckTwo() {
        return decks[2];
    }

    public ArrayList<Minion> getMinionOne() {
        return boardMinions[1];
    }

    public ArrayList<Minion> getMinionTwo() {
        return boardMinions[2];
    }

    public Hero getHeroOne() {
        return heroes[1];
    }

    public Hero getHeroTwo() {
        return heroes[2];
    }

    public int getManaOne() {
        return mana[1];
    }

    public int getManaTwo() {
        return mana[2];
    }

    public int getManaTurnOne() {
        return manaTurn[1];
    }

    public int getManaTurnTwo() {
        return manaTurn[2];
    }

    private class myTimer implements Runnable {

        private long start, time;

        @Override
        public void run() {
            start = System.nanoTime();
            time = 0;
            while (true) {
                try {
                    if (System.nanoTime() - start > time * 1000000000) {
//                        System.out.println(time);
                        time++;
                    }
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}