package Models.FileManagers;

import Models.Player;
import com.google.gson.Gson;

import java.io.*;
import java.util.*;

public class ProfilesFileManager {

    private File file;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private Gson gson = new Gson();

    private static ProfilesFileManager profilesFileManager = new ProfilesFileManager();

    private ProfilesFileManager(){
        file = new File("Profiles.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("kkkkk");
        }
    }

    public void createNewPlayer(String username , String password){
        Player player = new Player(username , password);
        String jsonPlayer = gson.toJson(player);
        jsonPlayer += "\n";
        try {
            fileWriter = new FileWriter(file , true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonPlayer);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removePlayer(String username){
        Set<Player> players = new HashSet<>();
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String player ;
                player = sc.nextLine();
                Player myPlayer = gson.fromJson(player , Player.class);
                if(!myPlayer.getPlayerName().equals(username)) {
                    players.add(myPlayer);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fileWriter = new FileWriter(file , false);
            bufferedWriter = new BufferedWriter(fileWriter);
            for(Player player : players){
                String jsonPlayer = gson.toJson(player);
                jsonPlayer += "\n";
                bufferedWriter.write(jsonPlayer);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayer(Player updatedPlayer){
        Set<Player> players = new HashSet<>();
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String player ;
                player = sc.nextLine();
                Player myPlayer = gson.fromJson(player , Player.class);
                if(!myPlayer.getPlayerName().equals(updatedPlayer.getPlayerName())) {
                    players.add(myPlayer);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fileWriter = new FileWriter(file , false);
            bufferedWriter = new BufferedWriter(fileWriter);
            for(Player player : players){
                String jsonPlayer = gson.toJson(player);
                jsonPlayer += "\n";
                bufferedWriter.write(jsonPlayer);
            }
            String updatePlayer = gson.toJson(updatedPlayer);
            updatePlayer += "\n";
            bufferedWriter.write(updatePlayer);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Player> getPlayersList(){
        Set<Player> players = new HashSet<>();
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String player ;
                player = sc.nextLine();
                Player myPlayer = gson.fromJson(player , Player.class);
                players.add(myPlayer);
            }
            return players;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Player getPlayer(String username){
        for(Player player : getPlayersList()){
            if(player.getPlayerName().equals(username)){
                return player;
            }
        }
        return null;
    }

    public boolean doesPlayerNameExists(String playerName){
        for(Player player : getPlayersList()){
            if(player.getPlayerName().equals(playerName)){
                return true;
            }
        }
        return false;
    }

    public boolean checkPlayer(String username , String password){
        for(Player player : getPlayersList()){
            if(player.getPlayerName().equals(username)){
                if(player.getPlayerPasswor().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    public static ProfilesFileManager getProfilesFileManager(){
        return profilesFileManager;
    }
}
