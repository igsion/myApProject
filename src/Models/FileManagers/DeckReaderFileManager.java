package Models.FileManagers;

import Models.Cards.Card;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DeckReaderFileManager {

    private File file;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private ArrayList<Card> player1, player2;

    private static DeckReaderFileManager deckReaderFileManager = new DeckReaderFileManager();

    private DeckReaderFileManager() {
        file = new File("deckReader.txt");
        player1 = new ArrayList<>(0);
        player2 = new ArrayList<>(0);
    }

    public void setDecks() {
        String str = "";
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                str += sc.nextLine();
            }
            JsonObject object = gson.fromJson(str, JsonObject.class);
            for (JsonElement element : object.get("enemy").getAsJsonArray()) {
                player2.add(CardsFileManager.getCardsFileManager().getCard(element.getAsJsonPrimitive().getAsString()));
            }
            for (JsonElement element : object.get("friend").getAsJsonArray()) {
                player1.add(CardsFileManager.getCardsFileManager().getCard(element.getAsJsonPrimitive().getAsString()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Card> getPlayer1() {
        return player1;
    }

    public ArrayList<Card> getPlayer2() {
        return player2;
    }

    public static DeckReaderFileManager getDeckReaderFileManager() {
        return deckReaderFileManager;
    }

}
