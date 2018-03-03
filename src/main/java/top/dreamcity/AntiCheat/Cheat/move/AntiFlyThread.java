package top.dreamcity.AntiCheat.Cheat.move;

import cn.nukkit.Player;
import cn.nukkit.Server;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

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
public class AntiFlyThread implements Runnable {


    private Thread thread;
    private HashSet<String> playerThread = new HashSet<>();
    //private HashMap<String,Integer> Flycount = new HashMap<>();

    public AntiFlyThread() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            try {
                Map<UUID,Player> players = new HashMap<>(Server.getInstance().getOnlinePlayers());
                for (Player player : players.values()) {
                    if (player.isOnline() && !player.isOp() && player.getGamemode() == 0) {
                        if(!playerThread.contains(player.getName())) {
                            new AntiFlyPlayerThread(player);
                            playerThread.add(player.getName());
                        }
                    }
                }
                for (String name: playerThread){
                    if(Server.getInstance().getPlayerExact(name) == null){
                        playerThread.remove(name);
                    }
                }
                thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}