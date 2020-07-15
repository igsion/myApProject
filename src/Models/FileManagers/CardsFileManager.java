package Models.FileManagers;

import Models.Cards.*;
import com.google.gson.*;

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
        cards = new HashSet<>();
        minions = new HashSet<>();
        spells = new HashSet<>();
        weapons = new HashSet<>();
        quests = new HashSet<>();
    }

    public Set<Card> getCardsSet() {
        cards.clear();
        minions.clear();
        spells.clear();
        weapons.clear();
        quests.clear();
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

        JsonObject dreadscale = new JsonObject();
        JsonObject dreadscaleMech = new JsonObject();
        JsonArray dreadscaleArray = new JsonArray();
        dreadscaleArray.add("this");
        dreadscaleArray.add(1);
        dreadscaleMech.add("damageAllOtherMinions", dreadscaleArray);
        dreadscale.add("endFriendlyTurn", dreadscaleMech);

        Card Dreadscale = new Minion("Dreadscale", "warlock", 3, "legendary",
                "At the end of your turn, deal 1 damage to all other minions.", "minion"
                , "beast", dreadscale, 4, 2, 1);

        JsonObject crazedNetherwing = new JsonObject();
        JsonObject crazedNetherwingMech = new JsonObject();
        JsonArray crazedNetherwingArray = new JsonArray();
        crazedNetherwingArray.add("this");
        crazedNetherwingArray.add(3);
        crazedNetherwingMech.add("damageAllOtherCharacters", crazedNetherwingArray);
        crazedNetherwing.add("battlecry", crazedNetherwingMech);

        Card CrazedNetherwing = new Minion("Crazed Netherwing", "warlock", 5, "free",
                "Battlecry: deal 3 damage to all other characters.", "minion"
                , "dragon", crazedNetherwing, 5, 5, 2);

        JsonObject polymorph = new JsonObject();
        polymorph.addProperty("cast", "transform");

        Card Polymorph = new Spell("Polymorph", "mage", 4, "rare",
                "Transform a minion into a 1/1 Sheep.", "spell", polymorph, 3);

        JsonObject arcaneIntellect = new JsonObject();
        JsonObject arcaneIntellectMech = new JsonObject();
        arcaneIntellectMech.addProperty("drawCard", 2);
        arcaneIntellect.add("cast", arcaneIntellectMech);

        Card ArcaneIntellect = new Spell("Arcane Intellect", "mage", 3, "free",
                "Draw 2 cards.", "spell", arcaneIntellect, 4);

        // NOT YET

        JsonObject friendlySmith = new JsonObject();
        JsonObject friendlySmithMech = new JsonObject();
        friendlySmithMech.addProperty("discoverWeapon", "");
        friendlySmith.add("cast", friendlySmithMech);

        Card FriendlySmith = new Spell("Friendly Smith", "rogue", 1, "common",
                "Discover a weapon from any class. Add it to your Adventure Deck with +2/+2"
                , "spell", friendlySmithMech, 5);

        JsonObject blinkFox = new JsonObject();
        JsonObject blinkFoxMech = new JsonObject();
        JsonArray blinkFoxArray = new JsonArray();
        blinkFoxMech.add("randomCardToHand", blinkFoxArray);
        blinkFox.add("battlecry", blinkFoxMech);

        Card BlinkFox = new Minion("Blink Fox", "rogue", 3, "free",
                "Battlecry: Add a random card to your hand (from your opponent's class).", "minion"
                , "beast", blinkFoxMech, 3, 3, 6);

        // NOT YET

        JsonObject silence = new JsonObject();
        JsonObject silenceMech = new JsonObject();
        JsonArray silenceArray = new JsonArray();
        silenceMech.add("silenceMinion" , silenceArray);
        silence.add("cast", silenceMech);

        Card Silence = new Spell("Silence", "neutral", 0, "free",
                "Silence a minion.", "spell", silence, 7);

        JsonObject deadlyShot = new JsonObject();
        JsonObject deadlyShotMech = new JsonObject();
        JsonArray deadlyShotArray = new JsonArray();
        deadlyShotMech.add("destroyRandomMinion", deadlyShotArray);
        deadlyShot.add("cast", deadlyShotMech);

        Card DeadlyShot = new Spell("Deadly Shot", "neutral", 3, "free",
                "Destroy a random enemy minion.", "spell", deadlyShot, 8);

        // NOT YET

        JsonObject massDispel = new JsonObject();
        JsonObject massDispelMech = new JsonObject();
        JsonArray massDispelArray1 = new JsonArray();
        JsonArray massDispelArray2 = new JsonArray();
        massDispelMech.add("silenceAllEnemy", massDispelArray1);
        massDispelMech.add("drawCard", massDispelArray2);
        massDispel.add("cast", massDispelMech);

        Card MassDispel = new Spell("Mass Dispel", "neutral", 4, "rare",
                "Silence all enemy minions. Draw a card.", "spell", massDispel, 9);

        JsonObject flamestrike = new JsonObject();
        JsonObject flamestrikeMech = new JsonObject();
        JsonArray flamestrikeArray = new JsonArray();
        flamestrikeArray.add(4);
        flamestrikeMech.add("damageAllEnemyMinion", flamestrikeArray);
        flamestrike.add("cast", flamestrikeMech);

        Card Flamestrike = new Spell("Flamestrike", "neutral", 7, "free",
                "Deal 4 damage to all enemy minions.", "spell", flamestrike, 10);

//        // NOT YET
//        Card PuzzleBoxofYoggSaron = new Spell("Puzzle Box of Yogg-Saron", "neutral", 10, "epic",
//                "Cast 10 random spells (targets chosen randomly).", "spell",
//                "castRandomSpell", 11);

        JsonObject frostwolfWarlord = new JsonObject();
        JsonObject frostwolfWarlordMech = new JsonObject();
        JsonArray frostwolfWarlordArray = new JsonArray();
        frostwolfWarlordArray.add("this");
        frostwolfWarlordMech.add("gainBuffFriendly", frostwolfWarlordArray);
        frostwolfWarlord.add("battlecry", frostwolfWarlordMech);

        Card FrostwolfWarlord = new Minion("Frostwolf Warlord", "neutral", 5, "free",
                "Battlecry: Gain +1/+1 for each other friendly minion on the battlefield.", "minion"
                , "", frostwolfWarlord, 4, 4, 12);

        JsonObject lightspawn = new JsonObject();
        JsonObject lightspawnMech = new JsonObject();
        JsonArray lightspawnArray = new JsonArray();
        lightspawnArray.add("this");
        lightspawnMech.add("minionAttackToHealth", lightspawnArray);
        lightspawn.add("healthChange", lightspawnMech);

        Card Lightspawn = new Minion("Lightspawn", "neutral", 4, "common",
                "This minion's Attack is always equal to its Health.", "minion", "elemental",
                lightspawn, 0, 5, 13);

        JsonObject amaniWarBear = new JsonObject();
        JsonArray amaniWarBearMech = new JsonArray();
        amaniWarBearMech.add("rush");
        amaniWarBearMech.add("taunt");
        amaniWarBear.add("attributes", amaniWarBearMech);

        Card AmaniWarBear = new Minion("Amani War Bear", "neutral", 7, "common",
                "Rush Taunt", "minion", "beast", amaniWarBear, 5, 7, 14);

        JsonObject alexstrasza = new JsonObject();
        JsonObject alexstraszaMech = new JsonObject();
        JsonArray alexstraszaArray = new JsonArray();
        alexstraszaArray.add(15);
        alexstraszaMech.add("setHeroHealth", alexstraszaArray);
        alexstrasza.add("battlecry", alexstraszaMech);

        Card Alexstrasza = new Minion("Alexstrasza", "neutral", 9, "legendary",
                "Battlecry: Set a hero's remaining Health to 15.", "minion", "dragon",
                alexstrasza, 8, 8, 15);

        JsonObject wildPyromancer = new JsonObject();
        JsonObject wildPyromancerMech = new JsonObject();
        JsonArray wildPyromancerArray = new JsonArray();
        wildPyromancerArray.add(1);
        wildPyromancerMech.add("damageAllMinion", wildPyromancerArray);
        wildPyromancer.add("spellCasted", wildPyromancerMech);

        Card WildPyromancer = new Minion("Wild Pyromancer", "neutral", 2, "rare",
                "After you cast a spell, deal 1 damage to ALL minions.", "minion"
                , "", wildPyromancer, 3, 2, 16);

        JsonObject chillwindYeti = new JsonObject();
        chillwindYeti.addProperty("", "");

        Card ChillwindYeti = new Minion("Chillwind Yeti", "neutral", 4, "free",
                "", "minion", "", chillwindYeti, 4, 5, 17);

        JsonObject murlocTidehunter = new JsonObject();
        JsonObject murlocTidehunterMech = new JsonObject();
        JsonArray murlockTidehunterArray = new JsonArray();
        murlockTidehunterArray.add("Murloc Scout");
        murlockTidehunterArray.add(1);
        murlocTidehunterMech.add("summon", murlockTidehunterArray);
        murlocTidehunter.add("battlecry", murlocTidehunterMech);

        // NOT YET
        Card MurlocTidehunter = new Minion("Murloc Tidehunter", "neutral", 2, "free",
                "Battlecry: Summon a 1/1 Murloc Scout.", "minion"
                , "murloc", murlocTidehunter, 2, 1, 18);

        JsonObject acidicSwampOoze = new JsonObject();
        JsonObject acidicSwampOozeMech = new JsonObject();
        JsonArray acidicSwampOozeArray = new JsonArray();
        acidicSwampOozeMech.add("destroyEnemyWeapon", acidicSwampOozeArray);
        acidicSwampOoze.add("battlecry", acidicSwampOozeMech);

        Card AcidicSwampOoze = new Minion("Acidic Swamp Ooze", "neutral", 2, "free",
                "Battlecry: Destroy your opponent's weapon.", "minion", "",
                acidicSwampOoze, 3, 2, 19);

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
//        addCard(PuzzleBoxofYoggSaron);
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

        JsonObject sprint = new JsonObject();
        JsonObject sprintMech = new JsonObject();
        JsonArray sprintArray = new JsonArray();
        sprintArray.add(4);
        sprintMech.add("drawCard", sprintArray);
        sprint.add("cast", sprintMech);

        Card Sprint = new Spell("Sprint", "neutral", 7, "free",
                "Draw 4 cards.", "spell", sprint, 21);

        JsonObject swarmofLocusts = new JsonObject();
        JsonObject swarmofLocustsMech = new JsonObject();
        JsonArray swarmofLocustsArray = new JsonArray();
        swarmofLocustsArray.add("Locust");
        swarmofLocustsArray.add(7);
        swarmofLocustsMech.add("summon", swarmofLocustsArray);
        swarmofLocusts.add("cast", swarmofLocustsMech);

        Card SwarmofLocusts = new Spell("Swarm of Locusts", "neutral", 6, "rare",
                "Summon seven 1/1 Locusts with Rush.", "spell", swarmofLocusts, 22);

        // NOT YET

        JsonObject pharaohsBlessing = new JsonObject();
        JsonObject pharaohsBlessingMech = new JsonObject();
        JsonArray pharaohsBlessingArray1 = new JsonArray();
        pharaohsBlessingMech.add("buffAttack", pharaohsBlessingArray1);
        pharaohsBlessingMech.addProperty("buffHealth", "4");
        pharaohsBlessingMech.addProperty("buff", "divineShield");
        pharaohsBlessingMech.addProperty("buff", "Taunt");
        pharaohsBlessing.add("cast", pharaohsBlessingMech);

        Card PharaohsBlessing = new Spell("Pharaoh's Blessing", "neutral", 6, "rare",
                "Give a minion +4/+4, Divine Shield and Taunt.", "spell", pharaohsBlessing, 23);



        JsonObject bookofSpecters = new JsonObject();
        JsonObject bookofSpectersMech = new JsonObject();
        JsonArray bookofSpectersArray = new JsonArray();
        bookofSpectersArray.add(3);
        bookofSpectersMech.add("drawNotSpell", bookofSpectersArray);
        bookofSpecters.add("cast", bookofSpectersMech);

        Card BookofSpecters = new Spell("Book of Specters", "neutral", 2, "epic",
                "Draw 3 cards. Discard any spells drawn.", "spell", bookofSpecters, 24);

        // NOT YET

        JsonObject sathrovarr = new JsonObject();
        JsonObject sathrovarrMech = new JsonObject();
        JsonArray sathrovarrArray = new JsonArray();
        sathrovarrMech.add("chooseFriendlyMinion", sathrovarrArray);
        sathrovarr.add("battlecry", sathrovarrMech);

        Card Sathrovarr = new Minion("Sathrovarr", "neutral", 9, "legendary",
                "Battlecry: Choose a friendly minion. Add a copy of it to your hand, deck and battlefield.",
                "minion", "demon", sathrovarr, 5, 5, 25);

        JsonObject tombWarden = new JsonObject();
        JsonObject tombWardenMech = new JsonObject();
        JsonArray tombWardenAttributes = new JsonArray();
        JsonArray tombWardenArray = new JsonArray();
        tombWardenArray.add("this");
        tombWardenMech.add("summon", tombWardenArray);
        tombWardenAttributes.add("taunt");
        tombWarden.add("battlecry", tombWardenMech);
        tombWarden.add("attributes", tombWardenAttributes);

        Card TombWarden = new Minion("Tomb Warden", "neutral", 8, "rare",
                "Taunt Battlecry: Summon a copy of this minion.", "minion", "mech",
                tombWarden, 3, 6, 26);

        JsonObject securityRover = new JsonObject();
        JsonObject securityRoverMech = new JsonObject();
        JsonArray securityRoverArray = new JsonArray();
        securityRoverArray.add("Gaurd Bot");
        securityRoverArray.add(1);
        securityRoverMech.add("summon", securityRoverArray);
        securityRover.add("onDamage", securityRoverMech);

        Card SecurityRover = new Minion("Security Rover", "neutral", 6, "rare",
                "Whenever this minion takes damage, summon a 2/3 Mech with Taunt.", "minion"
                , "mech", securityRover, 2, 6, 27);

        JsonObject curioCollector = new JsonObject();
        JsonObject curioCollectorMech = new JsonObject();
        JsonArray curioCollectorArray1 = new JsonArray();
        curioCollectorArray1.add(1);
        curioCollectorMech.add("buffHealth", curioCollectorArray1);
        JsonArray curioCollectorArray2 = new JsonArray();
        curioCollectorArray2.add(1);
        curioCollectorMech.add("buffAttack", curioCollectorArray2);
        curioCollector.add("drawCard", curioCollectorMech);

        Card CurioCollector = new Minion("Curio Collector", "neutral", 5, "rare",
                "Whenever you draw a card, gain +1/+1.", "minion", "", curioCollector,
                4, 4, 28);

        // NOT YET

        Card StrengthinNumbers = new Quest("Strength in Numbers", "neutral", 1, "common",
                "Sidequest: Spend 10 Mana on minions. Reward: Summon a minion from your deck.", "quest",
                10, "spendMana", "summon", 29);

        // NOT YET

        Card LearnDraconic = new Quest("Learn Draconic", "neutral", 1, "common",
                "Sidequest: Spend 8 Mana on spells. Reward: Summon a 6/6 Dragon.", "quest",
                8, "spendMana", "summon", 30);

        JsonObject caveHydra = new JsonObject();
        JsonObject caveHydraMech = new JsonObject();
        caveHydraMech.addProperty("damageSideMinions", 1);
        caveHydra.add("onAttack", caveHydraMech);

        Card CaveHydra = new Minion("Cave Hydra", "hunter", 3, "free",
                "Also damages the minions next to whomever this attacks.", "minion", "beast",
                caveHydra, 2, 4, 31);

        JsonObject swampKingDred = new JsonObject();
        JsonObject swampKingDredMech = new JsonObject();
        swampKingDredMech.addProperty("attackMinion", 1);
        swampKingDred.add("onMinionSummon", swampKingDredMech);

        Card SwampKingDred = new Minion("Swamp King Dred", "hunter", 7, "legendary",
                "After your opponent plays a minion, attack it.", "minion", "beast",
                swampKingDred, 9, 9, 32);

        JsonObject highPriestAmet = new JsonObject();
        JsonObject highPriestAmetMech = new JsonObject();
        highPriestAmetMech.addProperty("setHealthEqual", 1);
        highPriestAmet.add("onMinionSummon", highPriestAmetMech);

        Card HighPriestAmet = new Minion("High Priest Amet", "priest", 4, "legendary",
                "Whenever you summon a minion, set its Health equal to this minion's.", "minion",
                "", highPriestAmet, 2, 7, 33);

        JsonObject acolyteofAgony = new JsonObject();
        JsonArray acolyteofAgonyArray = new JsonArray();
        acolyteofAgonyArray.add("lifesteal");
        acolyteofAgony.add("attributes", acolyteofAgonyArray);

        Card AcolyteofAgony = new Minion("Acolyte of Agony", "priest", 3, "free",
                "Lifesteal", "minion", "", acolyteofAgony, 3, 3, 34);

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
