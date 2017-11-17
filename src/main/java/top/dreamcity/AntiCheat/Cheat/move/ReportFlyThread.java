package top.dreamcity.AntiCheat.Cheat.move;

import cn.nukkit.Player;
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
public class ReportFlyThread extends Report implements Runnable{
    private Thread thread;

    public ReportFlyThread(Player player){
        super(player);
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        try {
            if (player.isOnline() && !player.isOp() && player.getGamemode() == 0) {
                boolean flag = false;
                if (player.isOnGround()) {
                    if(player.getLevel().getBlock(player.add(0,-1,0)).getId() == 0 && player.getLevel().getBlock(player).getId() != 0){
                        double y = player.y;
                        thread.sleep(5*1000);
                        if(player.getLevel().getBlock(player.add(0,-1,0)).getId() == 0 && player.getLevel().getBlock(player).getId() != 0 && y<=player.y){
                            flag = true;
                        }else{
                            y = player.y;
                            if(player.move(0,-3,0)) {
                                if(player.move(0,-3,0)) {
                                    if (player.y + 6 == y) {
                                        flag = true;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    double y = player.y;
                    thread.sleep(5*1000);
                    //Player Jump : Normal[1.25] Jump_Effect_Level1[1+15/16] Jump_Effect_Level2[2.5]
                    if(!player.hasEffect(Effect.JUMP)) {
                        if (player.y > y + 1.3) {
                            flag = true;
                        }
                    }else if(player.getEffect(Effect.JUMP) != null){
                        double jump_high = Math.pow((player.getEffect(Effect.JUMP).getAmplifier()+1+4.2),0.125);
                        if (player.y > y + jump_high) {
                            flag = true;
                        }
                    }
                    if(!flag) {
                        thread.sleep(8*1000);
                        if(!player.isOnGround()){
                            flag = true;
                        }
                    }
                }
                if(flag){
                    player.kick(TextFormat.AQUA+"Cheat Type: "+TextFormat.RED+"Fly");
                }
            } else {
                AntiCheatAPI.getInstance().reportPlayer.remove(player.getName());
                AntiCheatAPI.getInstance().reportThread.remove(player.getName());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
