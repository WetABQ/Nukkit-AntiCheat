package top.dreamcity.AntiCheat.Cheat.move;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.TextFormat;
import top.dreamcity.AntiCheat.AntiCheatAPI;
import top.dreamcity.AntiCheat.Cheat.Report;

/**
 * Copyright © 2016 WetABQ&DreamCityAdminGroup All right reserved.
 * Welcome to DreamCity Server Address:dreamcity.top:19132
 * Created by WetABQ(Administrator) on 2017/11/17.
 * |||    ||    ||||                           ||        ||||||||     |||||||
 * |||   |||    |||               ||         ||  |      |||     ||   |||    |||
 * |||   |||    ||     ||||||  ||||||||     ||   ||      ||  ||||   |||      ||
 * ||  |||||   ||   |||   ||  ||||        ||| |||||     ||||||||   |        ||
 * ||  || ||  ||    ||  ||      |        |||||||| ||    ||     ||| ||      ||
 * ||||   ||||     ||    ||    ||  ||  |||       |||  ||||   |||   ||||||||
 * ||     |||      |||||||     |||||  |||       |||| ||||||||      |||||    |
 * ||||
 */
public class ReportSpeedThread extends Report implements Runnable{
    private Thread thread;

    public ReportSpeedThread(Player player){
        super(player);
        this.thread = new Thread(this);
        thread.start();
    }

    public void run(){
        try {
            if (player.isOnline() && !player.isOp() && player.getGamemode() == 0) {
                boolean flag = false;
                float move = AntiSpeedThread.getMove(player.getName());
                thread.sleep(1000);
                float move2 = AntiSpeedThread.getMove(player.getName());
                float m = AntiCheatAPI.getInstance().getMasterConfig().getMaxMoveSpeed();
                if(move >= m || move2 >= m){
                    player.setMotion(new Vector3(0,0,0));
                    player.teleport(player);
                    thread.sleep(1000*2);
                    move = AntiSpeedThread.getMove(player.getName());
                    thread.sleep(1000);
                    move2 = AntiSpeedThread.getMove(player.getName());
                    if(move >= m || move2 >= m){
                        if(move >= m && move2 >= m){
                            flag = true;
                        }
                        if(!flag) {
                            if (Math.abs(move2 - move) >= m - Math.min(move, move2)){
                                flag = true;
                            }
                        }
                    }
                }

                if(flag){
                    player.kick(TextFormat.AQUA+"Cheat Type: "+TextFormat.RED+"Speed");
                }
            }
            AntiCheatAPI.getInstance().reportPlayer.remove(player.getName());
            AntiCheatAPI.getInstance().reportThread.remove(player.getName());
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
