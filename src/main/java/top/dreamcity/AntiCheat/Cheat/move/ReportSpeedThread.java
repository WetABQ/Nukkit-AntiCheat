package top.dreamcity.AntiCheat.Cheat.move;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import top.dreamcity.AntiCheat.AntiCheatAPI;
import top.dreamcity.AntiCheat.Cheat.Report;
import top.dreamcity.AntiCheat.Cheat.Study.Study;

import java.util.ArrayList;
import java.util.Collections;

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
public class ReportSpeedThread extends Report implements Runnable {

    public ReportSpeedThread(Player player) {
        super(player);
        Server.getInstance().getScheduler().scheduleAsyncTask(AntiCheatAPI.getInstance(), this);
    }

    public void run() {
        try {
            boolean flag = false;
            for (int f = 0; f < 3; f++) {
                if (player.isOnline() && !player.isOp() && player.getGamemode() == 0) {
                    float move = AntiSpeedThread.getMove(player.getName());
                    Thread.sleep(1000);
                    float move2 = AntiSpeedThread.getMove(player.getName());
                    float m = AntiCheatAPI.getInstance().getMasterConfig().getMaxMoveSpeed();
                    if (move >= m || move2 >= m) {
                        player.setMotion(new Vector3(0, 0, 0));
                        player.teleport(player);
                        Thread.sleep(1000 * 2);
                        move = AntiSpeedThread.getMove(player.getName());
                        Thread.sleep(1000);
                        move2 = AntiSpeedThread.getMove(player.getName());
                        if (move >= m || move2 >= m) {
                            if (move >= m && move2 >= m) {
                                flag = true;
                            }
                            if (!flag) {
                                if (Math.abs(move2 - move) >= m - Math.min(move, move2)) {
                                    flag = true;
                                }
                            }
                        }
                    }

                    if (flag) {
                        player.kick(TextFormat.AQUA + "Cheat Type: " + TextFormat.RED + "Speed");
                    }
                }
                Thread.sleep(1000);
            }
            ArrayList<Double> speedList = new ArrayList<>();
            for (int i = 0; i < 10 && player.isOnline(); i++) {
                speedList.add((double) AntiSpeedThread.getMove(player.getName()));
                Thread.sleep(1000);
            }
            if (player.isOnline()) {
                double maxSpeed = Collections.max(speedList);
                double allSpeed = 0;
                for (double speed : speedList) {
                    allSpeed = allSpeed + speed;
                }
                double avgSpeed = allSpeed / (double) speedList.size();
                boolean isCheating = Study.SpeedPredict(maxSpeed, avgSpeed);
                Server.getInstance().getLogger().warning("AntiCheat-ML System: MaxSpeed: " + maxSpeed + " AvgSpeed: " + avgSpeed + " isCheating: " + isCheating + " Player: " + player.getName());
                if (isCheating) {
                    Server.getInstance().broadcastMessage(TextFormat.colorize("&ePlayer&a" + player.getName() + "&6was detected by AntiCheat-ML machine learning system suspected cheating"));
                }
            }
            if (!flag) {
                Server.getInstance().getLogger().notice("AntiCheat System Check Player " + player.getName() + " *NO CHEAT*");
            }
            AntiCheatAPI.getInstance().reportPlayer.remove(player.getName());
            AntiCheatAPI.getInstance().reportThread.remove(player.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
