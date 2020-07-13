package Models;

public class Buff {

    private int[][] buffs;

    public Buff(){
        buffs = new int[1][2];
    }

    public void addBuff(int attack, int health) {
        int[][] newBuffs = new int[buffs.length + 1][2];
        for(int i = 0; i < buffs.length; i++){
            newBuffs[i][0] = buffs[i][0];
            newBuffs[i][1] = buffs[i][1];
        }
        newBuffs[buffs.length][0] = attack;
        newBuffs[buffs.length][1] = health;
        this.buffs = newBuffs;
    }

    public int[][] getBuffs(){
        return buffs;
    }
}
