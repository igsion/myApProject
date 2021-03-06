package Models.FileManagers;

import Models.InfoPassive;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class InfoPassiveFileManager {

    private File file;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static InfoPassiveFileManager infoPassiveFileManager = new InfoPassiveFileManager();

    private InfoPassiveFileManager() {
        file = new File("Info.txt");
        file.delete();
    }

    public Set<InfoPassive> getInfoSet() {
        Set<InfoPassive> infoes = new HashSet<>();
        String info = "";
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String nextline = sc.nextLine();
                if (!nextline.equals("---")) {
                    info += nextline;
                } else {
                    InfoPassive myInfo = gson.fromJson(info, InfoPassive.class);
                    infoes.add(myInfo);
                    info = "";
                }
            }
            return infoes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addInfo(InfoPassive infoPassive) {
        String myInfo = gson.toJson(infoPassive);
        myInfo = myInfo + "\n";
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(myInfo);
            bufferedWriter.write("---\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addInfoes() {

        JsonObject drawTwice = new JsonObject();
        JsonObject drawTwiceMech = new JsonObject();
        JsonArray drawTwiceArray = new JsonArray();
        drawTwiceArray.add(1);
        drawTwiceMech.add("drawCard", drawTwiceArray);
        drawTwice.add("drawCard", drawTwiceMech);

        InfoPassive DrawTwice = new InfoPassive("Twice Draw", "You can draw 2 card at the " +
                "start of each turn", 1, drawTwice);

        JsonObject offCards = new JsonObject();
        JsonObject offCardsMech = new JsonObject();
        JsonArray offCardsArray = new JsonArray();
        offCardsArray.add(1);
        offCardsMech.add("lowerCardCost", offCardsArray);
        offCards.add("drawCard", offCardsMech);

        InfoPassive OffCards = new InfoPassive("Off Cards", "All cards cost 1 less", 2, offCards);

        JsonObject warriors = new JsonObject();
        JsonObject warriorsMech = new JsonObject();
        JsonArray warriorsArray = new JsonArray();
        warriorsArray.add(1);
        warriorsMech.add("lowerCardCost", warriorsArray);
        warriors.add("drawCard", warriorsMech);

        InfoPassive Warriors = new InfoPassive("Warriors", "Whenever a minion dies you get 2 " +
                "armor", 3, warriors);

        JsonObject nurse = new JsonObject();
        JsonObject nurseMech = new JsonObject();
        JsonArray nurseArray = new JsonArray();
        nurseMech.add("restoreOneMinion", nurseArray);
        nurse.add("endTurn", nurseMech);

        InfoPassive Nurse = new InfoPassive("Nurse", "At the end of your turn, restore health" +
                " of one of the damaged minions", 4, nurse);

        JsonObject freePower = new JsonObject();
        JsonObject freePowerMech = new JsonObject();
        JsonArray freePowerArray = new JsonArray();
        freePowerArray.add(1);
        freePowerArray.add(2);
        freePowerMech.add("heroPowerChange", freePowerArray);
        freePower.add("passive", freePowerMech);

        InfoPassive FreePower = new InfoPassive("Free Power", "Hero Power cost 1 less mana " +
                "and you can use it 2 times in one turn", 5, freePower);

        JsonObject manaJump = new JsonObject();
        JsonObject manaJumpMech = new JsonObject();
        JsonArray manaJumpArray = new JsonArray();
        manaJumpMech.add("oneMoreMana", manaJumpArray);
        manaJump.add("onStart", manaJumpMech);

        InfoPassive ManaJump = new InfoPassive("Mana Jump", "You start with 1 more mana", 6, manaJump);

        addInfo(DrawTwice);
        addInfo(OffCards);
        addInfo(Warriors);
        addInfo(Nurse);
        addInfo(FreePower);
        addInfo(ManaJump);
    }

    public InfoPassive getInfo(String info) {
        for (InfoPassive myInfo : getInfoSet()) {
            if (myInfo.getName().equals(info)) {
                return myInfo;
            }
        }
        return null;
    }

    public InfoPassive getInfo(int id) {
        for (InfoPassive myInfo : getInfoSet()) {
            if (myInfo.getId() == id) {
                return myInfo;
            }
        }
        return null;
    }

    public static InfoPassiveFileManager getInfoPassiveFileManager() {
        return infoPassiveFileManager;
    }

}
