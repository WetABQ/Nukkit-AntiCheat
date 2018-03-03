package top.dreamcity.AntiCheat.Cheat.move;

import cn.nukkit.Player;
import cn.nukkit.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright © 2016 WetABQ&DreamCityAdminGroup All right reserved.
 * Welcome to DreamCity Server Address:dreamcity.top:19132
 * Created by WetABQ(Administrator) on 2017/12/6.
 * |||    ||    ||||                           ||        ||||||||     |||||||
 * |||   |||    |||               ||         ||  |      |||     ||   |||    |||
 * |||   |||    ||     ||||||  ||||||||     ||   ||      ||  ||||   |||      ||
 * ||  |||||   ||   |||   ||  ||||        ||| |||||     ||||||||   |        ||
 * ||  || ||  ||    ||  ||      |        |||||||| ||    ||     ||| ||      ||
 * ||||   ||||     ||    ||    ||  ||  |||       |||  ||||   |||   ||||||||
 * ||     |||      |||||||     |||||  |||       |||| ||||||||      |||||    |
 * ||||
 */
public class AntiWaterWalkThread implements Runnable {
    private Thread thread;

    public AntiWaterWalkThread() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            try {
                Map<UUID,Player> players = new HashMap<>(Server.getInstance().getOnlinePlayers());
                for (Player player : players.values()) {
                    if (player.isOnline() && !player.isOp() && player.getGamemode() == 0) {
                        new AntiWaterWalkPlayerThread(player, player.isOnGround());
                    }
                }
                thread.sleep(7500);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
