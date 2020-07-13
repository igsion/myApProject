package Models.FileManagers;

import Models.Cards.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CardsFileManager {

    private File file;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Set<Card> cards;
    private Set<Minion> minions;
    private Set<Spell> spells;
    private Set<Weapon> weapons;
    private Set<Quest> quests;

    private static CardsFileManager cardsFileManager = new CardsFileManager();

    private CardsFileManager() {
        file = new File("Cards.txt");
        file.delete();
        minions = new HashSet<>();
        spells = new HashSet<>();
        weapons = new HashSet<>();
        quests = new HashSet<>();
    }

    public Set<Card> getCardsSet() {
        if (cards == null) {
            cards = new HashSet<>();
            String card = "";
            try {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String nextline = sc.nextLine();
                    if (!nextline.equals("---")) {
                        card += nextline;
                    } else {
                        Card myCard = gson.fromJson(card, Card.class);
                        if (myCard.type.equals("minion")) {
                            Minion myMinion = gson.fromJson(card, Minion.class);
                            minions.add(myMinion);
                        } else if (myCard.type.equals("spell")) {
                            Spell mySpell = gson.fromJson(card, Spell.class);
                            spells.add(mySpell);
                        } else if (myCard.type.equals("weapon")) {
                            Weapon myWeapon = gson.fromJson(card, Weapon.class);
                            weapons.add(myWeapon);
                        } else if (myCard.type.equals("quest")) {
                            Quest myQuest = gson.fromJson(card, Quest.class);
                            quests.add(myQuest);
                        }
                        cards.add(myCard);
                        card = "";
                    }
                }
                return cards;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return cards;
        }
    }

    private void addCard(Card card) {
        String myCard = gson.toJson(card);
        myCard = myCard + "\n";
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(myCard);
            bufferedWriter.write("---\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean cardExists(String cardName) {
        for (Card card : getCardsSet()) {
            if (card.name.equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    public Card getCard(String cardName) {
        if (cardExists(cardName)) {
            for (Card card : getCardsSet()) {
                if (card.name.equals(cardName)) {
                    return card;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public Minion getMinion(String cardName) {
        if (cardExists(cardName)) {
            for (Minion minion : this.minions) {
                if (minion.name.equals(cardName)) {
                    return minion;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public Spell getSpell(String cardName) {
        if (cardExists(cardName)) {
            for (Spell spell : this.spells) {
                if (spell.name.equals(cardName)) {
                    return spell;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public Weapon getWeapon(String cardName) {
        if (cardExists(cardName)) {
            for (Weapon weapon : this.weapons) {
                if (weapon.name.equals(cardName)) {
                    return weapon;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public Quest getQuest(String cardName) {
        if (cardExists(cardName)) {
            for (Quest quest : this.quests) {
                if (quest.name.equals(cardName)) {
                    return quest;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public void addCards() {

        // ----------------------- Phase one -----------------------

        Card Dreadscale = new Minion("Dreadscale", "warlock", 3, "legendary",
                "At the end of your turn, deal 1 damage to all other minions.", "minion"
                , "beast", "{ endOfYourTurn : { damageAllOtherMinions : 1 } }", 4, 2, 1);

        Card CrazedNetherwing = new Minion("Crazed Netherwing", "warlock", 5, "free",
                "Battlecry: If you're holding a Dragon, deal 3 damage to all other characters.", "minion"
                , "dragon", "battlecry", 5, 5, 2);

        Card Polymorph = new Spell("Polymorph", "mage", 4, "rare",
                "Transform a minion into a 1/1 Sheep.", "spell", "transform", 3);

        Card ArcaneIntellect = new Spell("Arcane Intellect", "mage", 3, "free",
                "Draw 2 cards.", "spell", "drawCard", 4);

        // NOT YET
        Card FriendlySmith = new Spell("Friendly Smith", "rogue", 1, "common",
                "Discover a weapon from any class. Add it to your Adventure Deck with +2/+2"
                , "spell", "discoverWeaponAllClass", 5);

        Card BlinkFox = new Minion("Blink Fox", "rogue", 3, "free",
                "Battlecry: Add a random card to your hand (from your opponent's class).", "minion"
                , "beast", "battlecry", 3, 3, 6);

        // NOT YET
        Card Silence = new Spell("Silence", "neutral", 0, "free",
                "Silence a minion.", "spell", "silence", 7);

        Card DeadlyShot = new Spell("Deadly Shot", "neutral", 3, "free",
                "Destroy a random enemy minion.", "spell", "destroyRandomEnemy", 8);

        // NOT YET
        Card MassDispel = new Spell("Mass Dispel", "neutral", 4, "rare",
                "Silence all enemy minions. Draw a card.", "spell", "silenceAllEnemy,drawCard", 9);

        Card Flamestrike = new Spell("Flamestrike", "neutral", 7, "free",
                "Deal 4 damage to all enemy minions.", "spell", "damageAllEnemy", 10);

        // NOT YET
        Card PuzzleBoxofYoggSaron = new Spell("Puzzle Box of Yogg-Saron", "neutral", 10, "epic",
                "Cast 10 random spells (targets chosen randomly).", "spell",
                "castRandomSpell", 11);

        Card FrostwolfWarlord = new Minion("Frostwolf Warlord", "neutral", 5, "free",
                "Battlecry: Gain +1/+1 for each other friendly minion on the battlefield.", "minion"
                , "", "battlecry", 4, 4, 12);

        Card Lightspawn = new Minion("Lightspawn", "neutral", 4, "common",
                "This minion's Attack is always equal to its Health.", "minion", "elemental",
                "attackEqualHealth", 0, 5, 13);

        Card AmaniWarBear = new Minion("Amani War Bear", "neutral", 7, "common",
                "Rush Taunt", "minion", "beast", "rushTaunt", 5, 7, 14);

        Card Alexstrasza = new Minion("Alexstrasza", "neutral", 9, "legendary",
                "Battlecry: Set a hero's remaining Health to 15.", "minion", "dragon",
                "battlecry", 8, 8, 15);

        Card WildPyromancer = new Minion("Wild Pyromancer", "neutral", 2, "rare",
                "After you cast a spell, deal 1 damage to ALL minions.", "minion"
                , "", "damageAllMinions", 3, 2, 16);

        Card ChillwindYeti = new Minion("Chillwind Yeti", "neutral", 4, "free",
                "", "minion", "", "", 4, 5, 17);

        // NOT YET
        Card MurlocTidehunter = new Minion("Murloc Tidehunter", "neutral", 2, "free",
                "Battlecry: Summon a 1/1 Murloc Scout.", "minion"
                , "murloc", "battlecry,summon", 2, 1, 18);

        Card AcidicSwampOoze = new Minion("Acidic Swamp Ooze", "neutral", 2, "free",
                "Battlecry: Destroy your opponent's weapon.", "minion", "",
                "battlecry,destroyEnemyWeapon", 3, 2, 19);

        Card AssassinsBlade = new Weapon("Assassin's Blade", "neutral", 5, "free",
                "", "weapon", "", 3, 4, 20);

        addCard(Dreadscale);
        addCard(CrazedNetherwing);
        addCard(Polymorph);
        addCard(ArcaneIntellect);
        addCard(FriendlySmith);
        addCard(BlinkFox);
        addCard(Silence);
        addCard(DeadlyShot);
        addCard(MassDispel);
        addCard(Flamestrike);
        addCard(PuzzleBoxofYoggSaron);
        addCard(FrostwolfWarlord);
        addCard(Lightspawn);
        addCard(AmaniWarBear);
        addCard(Alexstrasza);
        addCard(WildPyromancer);
        addCard(ChillwindYeti);
        addCard(MurlocTidehunter);
        addCard(AcidicSwampOoze);
        addCard(AssassinsBlade);

        // ----------------------- Phase two -----------------------

        Card Sprint = new Spell("Sprint", "neutral", 7, "free",
                "Draw 4 cards.", "spell", "drawCard", 21);

        Card SwarmofLocusts = new Spell("Swarm of Locusts", "neutral", 6, "rare",
                "Summon seven 1/1 Locusts with Rush.", "spell", "summon", 22);

        Card PharaohsBlessing = new Spell("Pharaoh's Blessing", "neutral", 6, "rare",
                "Give a minion +4/+4, Divine Shield and Taunt.", "spell",
                "buffMinion", 23);

        Card BookofSpecters = new Spell("Book of Specters", "neutral", 2, "epic",
                "Draw 3 cards. Discard any spells drawn.", "spell", "drawCard", 24);

        Card Sathrovarr = new Minion("Sathrovarr", "neutral", 9, "legendary",
                "Battlecry: Choose a friendly minion. Add a copy of it to your hand, deck and battlefield.",
                "minion", "demon", "battlecry", 5, 5, 25);

        Card TombWarden = new Minion("Tomb Warden", "neutral", 8, "rare",
                "Taunt Battlecry: Summon a copy of this minion.", "minion", "mech",
                "battlecry", 3, 6, 26);

        Card SecurityRover = new Minion("Security Rover", "neutral", 6, "rare",
                "Whenever this minion takes damage, summon a 2/3 Mech with Taunt.", "minion"
                , "mech", "summonOnDamage", 2, 6, 27);

        Card CurioCollector = new Minion("Curio Collector", "neutral", 5, "rare",
                "Whenever you draw a card, gain +1/+1.", "minion", "", "drawCard",
                4, 4, 28);

        Card StrengthinNumbers = new Quest("Strength in Numbers", "neutral", 1, "common",
                "Sidequest: Spend 10 Mana on minions. Reward: Summon a minion from your deck.", "quest",
                10, "spendMana", "summon", 29);

        Card LearnDraconic = new Quest("Learn Draconic", "neutral", 1, "common",
                "Sidequest: Spend 8 Mana on spells. Reward: Summon a 6/6 Dragon.", "quest",
                8, "spendMana", "summon", 30);

        Card CaveHydra = new Minion("Cave Hydra", "hunter", 3, "free",
                "Also damages the minions next to whomever this attacks.", "minion", "beast",
                "", 2, 4, 31);

        Card SwampKingDred = new Minion("Swamp King Dred", "hunter", 7, "legendary",
                "After your opponent plays a minion, attack it.", "minion", "beast",
                "triggerOnMinion", 9, 9, 32);

        Card HighPriestAmet = new Minion("High Priest Amet", "priest", 4, "legendary",
                "Whenever you summon a minion, set its Health equal to this minion's.", "minion",
                "", "inPlay", 2, 7, 33);

        Card AcolyteofAgony = new Minion("Acolyte of Agony", "priest", 3, "free",
                "Lifesteal", "minion", "", "lifeSteal", 3, 3, 34);

        Card HeavyAxe = new Weapon("Heavy Axe", "neutral", 1, "free",
                "", "weapon", "", 1, 3, 35);

        Card BattleAxe = new Weapon("Battle Axe", "neutral", 1, "free",
                "", "weapon", "", 2, 2, 36);

        Card Ashbringer = new Weapon("Ashbringer", "neutral", 5, "free",
                "", "weapon", "", 5, 3, 37);

        addCard(Sprint);
        addCard(SwarmofLocusts);
        addCard(PharaohsBlessing);
        addCard(BookofSpecters);
        addCard(Sathrovarr);
        addCard(TombWarden);
        addCard(SecurityRover);
        addCard(CurioCollector);
        addCard(StrengthinNumbers);
        addCard(LearnDraconic);
        addCard(CaveHydra);
        addCard(SwampKingDred);
        addCard(HighPriestAmet);
        addCard(AcolyteofAgony);
        addCard(HeavyAxe);
        addCard(BattleAxe);
        addCard(Ashbringer);
    }

    public static CardsFileManager getCardsFileManager() {
        return cardsFileManager;
    }
}
