package top.dreamcity.AntiCheat.Cheat.Study;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.TextFormat;
import top.dreamcity.AntiCheat.AntiCheat;
import top.dreamcity.AntiCheat.Cheat.move.AntiSpeedThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2016 WetABQ&DreamCityAdminGroup All right reserved.
 * Welcome to DreamCity Server Address:dreamcity.top:19132
 * Created by WetABQ(Administrator) on 2017/12/23.
 * |||    ||    ||||                           ||        ||||||||     |||||||
 * |||   |||    |||               ||         ||  |      |||     ||   |||    |||
 * |||   |||    ||     ||||||  ||||||||     ||   ||      ||  ||||   |||      ||
 * ||  |||||   ||   |||   ||  ||||        ||| |||||     ||||||||   |        ||
 * ||  || ||  ||    ||  ||      |        |||||||| ||    ||     ||| ||      ||
 * ||||   ||||     ||    ||    ||  ||  |||       |||  ||||   |||   ||||||||
 * ||     |||      |||||||     |||||  |||       |||| ||||||||      |||||    |
 * ||||
 */
public class StudySpeedThread implements Runnable {
    private Thread thread;
    private Player player;
    private boolean isCheating;

    /**
     * I tried to use machine learning to make a speed test, but it's still under development.
     */

    public StudySpeedThread(Player player,boolean isCheating) {
        this.isCheating = isCheating;
        this.thread = new Thread(this);
        this.player = player;
        thread.start();
    }

    public void run() {
        try {
            ArrayList<Double> speedList = new ArrayList<>();
            for (int i = 0; i < 10&&player.isOnline(); i++) {
                speedList.add((double) AntiSpeedThread.getMove(player.getName()));
                player.setSubtitle(TextFormat.colorize("&bDemo&6: &a"+isCheating+" &eCount&6: &a"+i));
                player.sendTitle("");
                thread.sleep(1000);
            }
            if(player.isOnline()) {
                double maxSpeed = Collections.max(speedList);
                double allSpeed = 0;
                for (double speed : speedList) {
                    allSpeed = allSpeed + speed;
                }
                double avgSpeed = allSpeed / (double) speedList.size();
                Server.getInstance().getLogger().warning("StudyData: MaxSpeed: " + maxSpeed + " AvgSpeed: " + avgSpeed + " isCheating: " + isCheating+" Player: "+player.getName());
                Study.SpeedStudy(maxSpeed, avgSpeed, isCheating);
                player.sendMessage("StudyData: MaxSpeed: " + maxSpeed + " AvgSpeed: " + avgSpeed + " isCheating: " + isCheating+" Player: "+player.getName());
            }
            AntiCheat.DemoPlayer.remove(player.getName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
