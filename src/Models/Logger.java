package Models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Logger {

    private static Logger logger = new Logger();

    public boolean createPlayerLog(Player player){
        File file = new File("Logs/" + player.getPlayerName() + "-" + player.getPlayerId() + ".log");
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file , true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("USER:" + player.getPlayerName() + "\n");
            bufferedWriter.write("CREATED_AT:" + LocalDate.now() + " " + ZonedDateTime.now().getHour() + ":"
                    + ZonedDateTime.now().getMinute() + ":" + ZonedDateTime.now().getSecond() + "\n");
            bufferedWriter.write("PASSWORD:" + player.getPlayerPasswor() + "\n" + "\n");
            bufferedWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean log(String event , String eventDescription , Player player){
        File file = new File("Logs/" + player.getPlayerName() + "-" + player.getPlayerId() + ".log");
        if(file.exists()){
            try {
                FileWriter fileWriter = new FileWriter(file , true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(event + " " + LocalDate.now() + " " + ZonedDateTime.now().getHour() + ":"
                        + ZonedDateTime.now().getMinute() + ":" + ZonedDateTime.now().getSecond() + " "
                        + eventDescription + "\n");
                bufferedWriter.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean endOfLog(Player player){
        File file = new File("Logs/" + player.getPlayerName() + "-" + player.getPlayerId() + ".log");
        List<String> lines = new ArrayList<String>(0);
        if(file.exists()){
            try {
                Scanner sc = new Scanner(file);
                while(sc.hasNext()){
                    String line = sc.nextLine();
                    if(!line.equals("")){
                        line += "\n";
                    }
                    if(!line.equals("")){
                        lines.add(line);
                    }else{
                        String delete = "DELETED_AT: " + LocalDate.now() + " " + ZonedDateTime.now().getHour() + ":"
                                + ZonedDateTime.now().getMinute() + ":" + ZonedDateTime.now().getSecond() + "\n\n";
                        lines.add(delete);
                    }
                }
                FileWriter fileWriter = new FileWriter(file , false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                for(String line : lines){
                    bufferedWriter.write(line);
                }
                bufferedWriter.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }

    public static Logger getLogger(){
        return logger;
    }

}
