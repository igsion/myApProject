package Models.FileManagers;

import Models.Hero.Hero;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class HeroesFileManager {

    File file;
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static HeroesFileManager heroesFileManager = new HeroesFileManager();

    private HeroesFileManager() {
        file = new File("Heroes.txt");
        file.delete();
    }

    public Set<Hero> getHeroesSet() {
        Set<Hero> heroes = new HashSet<>();
        String hero = "";
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String nextline = sc.nextLine();
                if (!nextline.equals("---")) {
                    hero += nextline;
                } else {
                    Hero myHero = gson.fromJson(hero, Hero.class);
                    heroes.add(myHero);
                    hero = "";
                }
            }
            return heroes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addHero(Hero hero) {
        String myHero = gson.toJson(hero);
        myHero = myHero + "\n";
        try {
            fileWriter = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(myHero);
            bufferedWriter.write("---\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHeroes() {
        Hero mage = new Hero("Mage", "spellCost2Less", 30);
        Hero rogue = new Hero("Rogue", "anotherClassCardCost2Less", 30);
        Hero warlock = new Hero("Warlock", "has5AdditionalHp", 30);
        Hero hunter = new Hero("Hunter", "allMinionRush", 30);
        Hero priest = new Hero("Priest", "allHealDouble", 30);
        addHero(mage);
        addHero(rogue);
        addHero(warlock);
        addHero(hunter);
        addHero(priest);
    }

    public Hero getHero(String hero){
        for(Hero myHero : getHeroesSet()){
            if(myHero.getName().equals(hero)){
                return myHero;
            }
        }
        return null;
    }

    public static HeroesFileManager getHeroesFileManager() {
        return heroesFileManager;
    }

}
