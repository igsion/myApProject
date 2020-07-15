package Models.States;

import Models.FileManagers.DeckReaderFileManager;
import Models.Interfaces.Observer;
import Models.Interfaces.Subject;
import Models.Interfaces.Targetable;
import Models.Cards.*;
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
    private Quest[] quests;
    private Weapon[] weapons;
    private transient List<Minion>[] toRemoves;
    private transient ArrayList<Targetable> targets;
    private transient boolean targetSearch = false;
    private transient Minion selectedMinion;
    private transient Map<Minion, Boolean>[] attackMaps;
    private transient ArrayList<Observer> observers = new ArrayList<>(0);
    private transient Random random;
    private transient boolean isDeckReader = false;

    public PlayState(Game game) {
        super(game);
        boardMinions = new ArrayList[3];
        boardMinions[1] = new ArrayList<>(0);
        boardMinions[2] = new ArrayList<>(0);
        toRemoves = new ArrayList[3];
        toRemoves[1] = new ArrayList<>(0);
        toRemoves[2] = new ArrayList<>(0);
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
        drawCard(1, 1);
        drawCard(1, 1);
        drawCard(1, 1);
        drawCard(2, 1);
        drawCard(2, 1);
        drawCard(2, 1);
    }

    public void startDeckReader() {
        isDeckReader = true;
        one = this.game.getPlayer();
        two = this.game.getPlayer();
        heroes = new Hero[3];
        heroes[1] = HeroesFileManager.getHeroesFileManager().getHero("Mage");
        heroes[2] = HeroesFileManager.getHeroesFileManager().getHero("Mage");
        setDecks();
        mana[1] = 1;
        manaTurn[1] = 1;
        mana[2] = 1;
        manaTurn[2] = 1;
        drawCard(1, 1);
        drawCard(1, 1);
        drawCard(1, 1);
        drawCard(2, 1);
        drawCard(2, 1);
        drawCard(2, 1);
    }

    public void shuffle(int player, ArrayList<Card> deck, Card card) {
        deck.add(card);
        hands[1].remove(card);
        drawCard(1, 1);
    }

    public void playCard(int player, Card card) {
        if (isTurn[player]) {
            switch (card.type) {
                case "minion":
                    playMinion(player, card);
                    break;
                case "spell":
                    playSpell(player, card);
                    break;
                case "weapon":
                    playWeapon(player, card);
                    break;
                case "quest":
                    playQuest(player, card);
                    break;
            }
        }
    }

    private String playMinion(int player, Card card) {
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
                minion.update("battlecry", player);
                notifyUpdate("minionPlayed", player);
                return "Successful";
            } else {
                return "Board is full";
            }
        } else {
            return "You don't have the mana";
        }
    }

    private String playSpell(int player, Card card) {
        if (card.mana <= manaTurn[player]) {
            Spell spell = CardsFileManager.getCardsFileManager().getSpell(card.name);
            spell.update("cast", player);
            notifyUpdate("spellCasted", player);
//            if (spell.getMechanic().get("cast").isJsonObject()) {
//                for (Map.Entry a : spell.getMechanic().get("cast").getAsJsonObject().entrySet()) {
//                    Class s = State.getState().getClass();
//                    for (Method method : s.getDeclaredMethods()) {
//                        if (method.getName().equals(a.getKey())) {
//                            try {
//                                int x = Integer.parseInt(a.getValue().toString());
//                                for (int i = 0; i < x; i++)
//                                    method.invoke(State.getState(), player);
//                            } catch (IllegalAccessException | InvocationTargetException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            } else {
//                Class s = State.getState().getClass();
//                for (Method method : s.getDeclaredMethods()) {
//                    if (method.getName().equals(spell.getMechanic().get("cast").getAsString())) {
//                        try {
//                            method.invoke(State.getState(), player);
//                        } catch (IllegalAccessException | InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//            }
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

    private String playWeapon(int player, Card card) {
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

    private String playQuest(int player, Card card) {
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

    public void summon(int player, String minionName, int number) {
        for (int i = 0; i < number; i++) {
            if (boardMinions[player].size() < 7) {
                System.out.println(CardsFileManager.getCardsFileManager().getMinion(minionName));
                if (CardsFileManager.getCardsFileManager().getMinion(minionName) != null) {
                    boardMinions[player].add(CardsFileManager.getCardsFileManager().getMinion(minionName));
                }
            }
        }
    }

    public void nextTurn(int player) {
        deSelect(player);
        if (isTurn[1]) {
            isTurn[1] = false;
            isTurn[2] = true;
            Iterator<Observer> iterator = observers.iterator();
            while (iterator.hasNext()) {
                iterator.next().update("endFriendlyTurn", player);
            }
            boardMinions[1].removeAll(toRemoves[1]);
            boardMinions[2].removeAll(toRemoves[2]);
            if (mana[2] < 10) {
                mana[2]++;
            }
            int c = mana[2];
            manaTurn[2] = c;
            drawCard(2, 1);
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
            drawCard(1, 1);
            for (Map.Entry<Minion, Boolean> entry : attackMaps[1].entrySet()) {
                entry.setValue(true);
            }
            for (Map.Entry<Minion, Boolean> entry : attackMaps[2].entrySet()) {
                entry.setValue(false);
            }
        }
        turn++;
        notifyUpdate("startTurn", player);
    }

//    public void drawCard(int player) {
//        if (decks[player].size() > 0) {
//            int i = random.nextInt(decks[player].size());
//            if (hands[player].size() < 10) {
//                hands[player].add(decks[player].get(i));
//                notifyUpdate("drawCard", player);
//            }
//            decks[player].remove(i);
//            decks[player].trimToSize();
//        }
//    }

    public void drawCard(int player, int number) {
        if(!isDeckReader){
            for (int j = 0; j < number; j++) {
                if (decks[player].size() > 0) {
                    int i = random.nextInt(decks[player].size());
                    if (hands[player].size() < 10) {
                        hands[player].add(decks[player].get(i));
                        notifyUpdate("drawCard", player);
                    }
                    decks[player].remove(i);
                    decks[player].trimToSize();
                }
            }
        } else {
            for (int j = 0; j < number; j++) {
                if (decks[player].size() > 0) {
                    int i = random.nextInt(decks[player].size());
                    if (hands[player].size() < 10) {
                        hands[player].add(decks[player].get(0));
                        notifyUpdate("drawCard", player);
                    }
                    decks[player].remove(0);
                    decks[player].trimToSize();
                }
            }
        }

    }

    public void drawNotSpell(int player, int number) {
        for (int j = 0; j < number; j++) {
            if (decks[player].size() > 0) {
                int i = random.nextInt(decks[player].size());
                if (!decks[player].get(i).type.equals("spell")) {
                    if (hands[player].size() < 10) {
                        hands[player].add(decks[player].get(i));
                        notifyUpdate("drawCard", player);
                    }
                }
                decks[player].remove(i);
                decks[player].trimToSize();
            }
        }
    }

    public void deSelect(int player) {
        targetSearch = false;
        targets.clear();
    }

    public void minionSelected(int player, int i) {
        if (targetSearch) {
            if (this.targets.contains(boardMinions[player].get(i))) {
                minionAttack(player, this.selectedMinion, boardMinions[player].get(i));
            }
            targetSearch = false;
        } else if (isTurn[player]) {
            this.targets = minionTargets(player, boardMinions[player].get(i));
            this.selectedMinion = boardMinions[player].get(i);
            targetSearch = true;
        }
    }

    public ArrayList<Targetable> minionTargets(int player, Minion myMinion) {
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

    public void buffAttack(int player, int number) {
//        Minion minion = (Minion) chooseTarget();
//        minion.setAttack(minion.getAttack() + 1);
    }

    public void buffHealth(int player, int number) {
//        minion.setHp(minion.getHp() + 1);
    }

    public void gainBuffFriendly(int player, Minion minion) {
        minion.setHp(minion.getHp() + boardMinions[player].size() - 1);
        minion.setAttack(minion.getAttack() + boardMinions[player].size() - 1);
    }

    public void weaponAttack(int player, Weapon weapon, Minion defender) {
        damageMinion(player, weapon.damage, defender);
        if (isOneTurn()) {
            damageHero(player, defender.getAttack(), heroes[1]);
        } else {
            damageHero(player, defender.getAttack(), heroes[2]);
        }
        weapon.durability += -1;
    }

    public void weaponAttack(int player, Weapon weapon, Hero hero) {
        damageHero(player, weapon.damage, hero);
        weapon.durability += -1;
    }

    public void minionAttack(int player, Minion attacker, Minion defender) {
        damageMinion(player, attacker.getAttack(), defender);
        damageMinion(player, defender.getAttack(), attacker);
    }

    public void minionAttack(int player, Minion attacker, Hero hero) {
        damageHero(player, attacker.getAttack(), hero);
    }

    public void damageMinion(int player, int i, Minion minion) {
        if (minion.getHp() - i < 1) {
            minionDie(player, minion);
        } else {
            minion.setHp(minion.getHp() - i);
        }
    }

    public void damageHero(int player, int i, Hero hero) {
        hero.setHp(hero.getHp() - i);
    }

    public void healMinion(int player, int i, Minion minion) {
        Minion myMinion = CardsFileManager.getCardsFileManager().getMinion(minion.name);
        if (minion.getHp() + i <= myMinion.getHp()) {
            minion.setHp(minion.getHp() + i);
        } else {
            minion.setHp(myMinion.getHp());
        }
    }

    public void healHero(int player, int i, Hero hero) {
        if (hero.getHp() + i <= 30) {
            hero.setHp(hero.getHp() + i);
        } else {
            hero.setHp(30);
        }
    }

    public void damageAllOtherMinions(int player, Minion myMinion, int damage) {
        Iterator<Minion> iterator1 = boardMinions[1].iterator();
        while (iterator1.hasNext()) {
            Minion minion = iterator1.next();
            if (!minion.equals(myMinion)) {
                damageMinion(player, damage, minion);
            }
        }
        Iterator<Minion> iterator2 = boardMinions[2].iterator();
        while (iterator2.hasNext()) {
            Minion minion = iterator2.next();
            if (!minion.equals(myMinion)) {
                damageMinion(player, damage, minion);
            }
        }
    }

    public void damageAllMinions(int player, int damage) {
        damageAllEnemyMinion(player, damage);
        damageAllEnemyMinion(3 - player, damage);
    }

    public void damageAllEnemyMinion(int player, int damage) {
        for (Minion minion : boardMinions[3 - player]) {
            damageMinion(player, damage, minion);
        }
        boardMinions[3 - player].removeAll(toRemoves[3 - player]);
    }

    public void damageAllCharacters(int player, int damage) {
        damageAllMinions(player, damage);
        damageHero(player, damage, heroes[1]);
        damageHero(player, damage, heroes[2]);
    }

    public void damageAllOtherCharacters(int player, Minion minion, int damage) {
        damageAllOtherMinions(player, minion, damage);
        damageHero(player, damage, heroes[1]);
        damageHero(player, damage, heroes[2]);
    }

    public void addRandomCardToHand(int player) {
        Card card = randomClassCard(player, heroes[3 - player].getName());
        hands[player].add(card);
    }

    public void minionAttackToHealth(int player, Minion minion) {
        minion.setAttack(minion.getHp());
    }

    public void destroyEnemyWeapon(int player) {
        System.out.println(555);
    }

    public void destroyRandomMinion(int player) {
        if (boardMinions[3 - player].size() > 0) {
            int i = random.nextInt(boardMinions[3 - player].size());
            minionDie(player, boardMinions[3 - player].get(i));
            boardMinions[3 - player].removeAll(toRemoves[3 - player]);
        }
    }

    public void setMinionHealth(int player, Minion minion, int health) {
        minion.setHp(health);
    }

    public void setHeroHealth(int player, int health) {
        heroes[player].setHp(health);
    }

    public void silenceMinion(int player, Minion minion) {

    }

    public void minionDie(int player, Minion minion) {
        if (boardMinions[1].contains(minion)) {
            toRemoves[1].add(minion);
        } else {
            toRemoves[2].add(minion);
        }
    }

    public Card randomClassCard(int player, String className) {
        ArrayList<Card> cards = new ArrayList<>(0);
        for (Card card : CardsFileManager.getCardsFileManager().getCardsSet()) {
            if (card.hero.equals(className)) {
                cards.add(card);
            }
        }
        return cards.get(random.nextInt(cards.size()));
    }

    public void lowerSpellCost(int player, int number){

    }

    public void lowerCardCost(int player, int number){

    }

    public void transform(int player, int i, Minion minion) {
        this.boardMinions[1].set(i, minion);
    }

    public void discard(int player, Card card, ArrayList<Card> hand) {
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

    public void setDecks() {
        DeckReaderFileManager.getDeckReaderFileManager().setDecks();
        decks[1].addAll(DeckReaderFileManager.getDeckReaderFileManager().getPlayer1());
        decks[2].addAll(DeckReaderFileManager.getDeckReaderFileManager().getPlayer2());
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
    public void notifyUpdate(String event, int player) {
        for (Observer o : observers) {
            o.update(event, player);
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