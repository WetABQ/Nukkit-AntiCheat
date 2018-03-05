package top.dreamcity.AntiCheat.Cheat.move;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import top.dreamcity.AntiCheat.AntiCheatAPI;
import top.dreamcity.AntiCheat.Cheat.Report;
import top.dreamcity.AntiCheat.Event.Listener.EventListener;

import java.math.BigDecimal;

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
public class ReportFlyThread extends Report {

    public ReportFlyThread(Player player) {
        super(player);
        Server.getInstance().getScheduler().scheduleAsyncTask(AntiCheatAPI.getInstance(), this);
    }

    @Override
    public void run() {
        try {
            boolean flag = false;
            for (int f = 0; f < 60; f++) {
                if (player.isOnline() && !player.isOp() && player.getGamemode() == 0) {
                    if (player.y != (int) player.y) {
                        double skidY = player.y;
                        Thread.sleep(1000);
                        if (player.y != (int) player.y) {
                            BigDecimal b1 = new BigDecimal(Double.toString(player.y));
                            BigDecimal b2 = new BigDecimal(Double.toString(skidY));
                            double skidDY = b1.subtract(b2).doubleValue();
                            if (skidDY <= 1 && skidDY >= 0 && !EventListener.AntiTower.containsKey(player.getName())) {
                                if (player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z) == 0 &&
                                        player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z - 1) == 0 &&
                                        player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z + 1) == 0 &&
                                        player.getLevel().getBlockIdAt((int) player.x + 1, (int) player.y - 1, (int) player.z) == 0 &&
                                        player.getLevel().getBlockIdAt((int) player.x - 1, (int) player.y - 1, (int) player.z) == 0 && AntiFlyPlayerThread.ifPlayerinSky(player)) {
                                    int groundY = 1;
                                    while (player.getLevel().getBlockIdAt((int) player.x, (int) player.y - groundY, (int) player.z) == 0) {
                                        groundY++;
                                    }
                                    groundY -= 1;
                                    if (groundY > 2) {
                                        player.teleport(new Vector3(player.x, player.y - groundY, player.z));
                                    }
                                }
                            }
                        }
                    }
                    //System.out.println("a");
                    if (player.isOnGround()) {
                        //System.out.println("b");
                        boolean b = false;
                        if (player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z) == 0 && player.isSneaking()) {
                            if (player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z) == 0 &&
                                    player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z + 1) == 0 &&
                                    player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z - 1) == 0 &&
                                    player.getLevel().getBlockIdAt((int) player.x + 1, (int) player.y - 1, (int) player.z) == 0 &&
                                    player.getLevel().getBlockIdAt((int) player.x - 1, (int) player.y - 1, (int) player.z) == 0 &&
                                    player.getLevel().getBlockIdAt((int) player.x, (int) player.y, (int) player.z) == 0 &&
                                    player.getLevel().getBlockIdAt((int) player.x - 1, (int) player.y - 1, (int) player.z - 1) == 0 &&
                                    player.getLevel().getBlockIdAt((int) player.x - 1, (int) player.y - 1, (int) player.z + 1) == 0 &&
                                    player.getLevel().getBlockIdAt((int) player.x + 1, (int) player.y - 1, (int) player.z - 1) == 0 &&
                                    player.getLevel().getBlockIdAt((int) player.x + 1, (int) player.y - 1, (int) player.z + 1) == 0 && AntiFlyPlayerThread.ifPlayerinSky(player)) {
                                b = true;
                            }
                        } else if (!player.isSneaking() && player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z) == 0) {
                            b = true;
                        }
                        if (b) {
                            //System.out.println("c");
                            double y = player.y;
                            Thread.sleep(3000);
                            if (y <= player.y && player.isOnGround() && !EventListener.AntiTower.containsKey(player.getName())) {
                                while (player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z) == 0 &&
                                        player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z + 1) == 0 &&
                                        player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z - 1) == 0 &&
                                        player.getLevel().getBlockIdAt((int) player.x + 1, (int) player.y - 1, (int) player.z) == 0 &&
                                        player.getLevel().getBlockIdAt((int) player.x - 1, (int) player.y - 1, (int) player.z) == 0 &&
                                        player.getLevel().getBlockIdAt((int) player.x, (int) player.y, (int) player.z) == 0 &&
                                        player.getLevel().getBlockIdAt((int) player.x - 1, (int) player.y - 1, (int) player.z - 1) == 0 &&
                                        player.getLevel().getBlockIdAt((int) player.x - 1, (int) player.y - 1, (int) player.z + 1) == 0 &&
                                        player.getLevel().getBlockIdAt((int) player.x + 1, (int) player.y - 1, (int) player.z - 1) == 0 &&
                                        player.getLevel().getBlockIdAt((int) player.x + 1, (int) player.y - 1, (int) player.z + 1) == 0 && AntiFlyPlayerThread.ifPlayerinSky(player)) {
                                    player.move(0, -1, 0);
                                    player.getAdventureSettings().set(AdventureSettings.Type.FLYING, false);
                                    //player.getAdventureSettings().setFlying(false);
                                    //player.getAdventureSettings().setCanFly(false);
                                    player.getAdventureSettings().update();
                                    y = player.y;
                                    Thread.sleep(1000);
                                    if (player.y >= y && player.isOnGround() && !EventListener.AntiTower.containsKey(player.getName())) {
                                        int groundY = 1;
                                        while (player.getLevel().getBlockIdAt((int) player.x, (int) player.y - groundY, (int) player.z) == 0) {
                                            groundY++;
                                        }
                                        groundY -= 1;
                                        if (groundY > 2 && player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z) == 0 &&
                                                player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z + 1) == 0 &&
                                                player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z - 1) == 0 &&
                                                player.getLevel().getBlockIdAt((int) player.x + 1, (int) player.y - 1, (int) player.z) == 0 &&
                                                player.getLevel().getBlockIdAt((int) player.x - 1, (int) player.y - 1, (int) player.z) == 0 &&
                                                player.getLevel().getBlockIdAt((int) player.x, (int) player.y, (int) player.z) == 0 &&
                                                player.getLevel().getBlockIdAt((int) player.x - 1, (int) player.y - 1, (int) player.z - 1) == 0 &&
                                                player.getLevel().getBlockIdAt((int) player.x - 1, (int) player.y - 1, (int) player.z + 1) == 0 &&
                                                player.getLevel().getBlockIdAt((int) player.x + 1, (int) player.y - 1, (int) player.z - 1) == 0 &&
                                                player.getLevel().getBlockIdAt((int) player.x + 1, (int) player.y - 1, (int) player.z + 1) == 0 && player.isOnline() && AntiFlyPlayerThread.ifPlayerinSky(player)) {
                                            player.teleport(new Vector3(player.x, player.y - groundY, player.z));
                                        }
                                    }
                                }
                                // System.out.println("d");
                            } else {
                                // System.out.println("e");
                                y = player.y;
                                if (player.move(0, -3, 0)) {
                                    System.out.println("f" + y + "|" + player.y);
                                    if (player.move(0, -3, 0)) {
                                        System.out.println("g" + y + "|" + player.y);
                                        if (player.y + 6 <= y) {
                                            System.out.println("h");
                                            flag = true;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        //System.out.println("i");
                        double y = player.y;
                        Thread.sleep(3000);

                        //Player Jump : Normal[1.25] Jump_Effect_Level1[1+15/16] Jump_Effect_Level2[2.5]
                        if ((player.y - y) > -0.000000000000001 && (player.y - y) < 0.000000000000001 && !player.isOnGround() && !EventListener.AntiTower.containsKey(player.getName())) {
                            System.out.println("j");
                            flag = true;
                        }
                        y = player.y;
                        if (player.move(0, -3, 0)) {
                            //System.out.println("k" + y + "|" + player.y);
                            if (player.move(0, -3, 0)) {
                                //System.out.println("l" + y + "|" + player.y);
                                if (player.y + 6 <= y) {
                                    System.out.println("m");
                                    flag = true;
                                }
                            }
                        }
                    }
                    //System.out.println("n");
                    if (flag) {
                        f = 60;
                        player.kick(TextFormat.AQUA + "Cheat Type: " + TextFormat.RED + "Fly");
                        Server.getInstance().broadcastMessage(TextFormat.RED + player.getName() + " Kick by AntiCheatSystem   Reason: Fly");
                    }
                }
                //System.out.println("shabi!!!");
                Thread.sleep(1000);
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
