package top.dreamcity.AntiCheat.Cheat.move;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.MobEffectPacket;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.TextFormat;
import top.dreamcity.AntiCheat.AntiCheatAPI;
import top.dreamcity.AntiCheat.Event.Listener.EventListener;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright © 2017 WetABQ&DreamCityAdminGroup All right reserved.
 * Welcome to DreamCity Server Address:dreamcity.top:19132
 * Created by WetABQ(Administrator) on 2017/10/8.
 * |||    ||    ||||                           ||        ||||||||     |||||||
 * |||   |||    |||               ||         ||  |      |||     ||   |||    |||
 * |||   |||    ||     ||||||  ||||||||     ||   ||      ||  ||||   |||      ||
 * ||  |||||   ||   |||   ||  ||||        ||| |||||     ||||||||   |        ||
 * ||  || ||  ||    ||  ||      |        |||||||| ||    ||     ||| ||      ||
 * ||||   ||||     ||    ||    ||  ||  |||       |||  ||||   |||   ||||||||
 * ||     |||      |||||||     |||||  |||       |||| ||||||||      |||||    |
 * ||||
 */
public class AntiSpeedThread extends AsyncTask {

    //private static HashMap<String,HashMap<Integer,Float>> moveSpeed = new HashMap<>();
    private static HashMap<String, Float> finalSpeed = new HashMap<>();
    public static HashMap<String, Position> positionHashMap = new HashMap<>();
    private static HashMap<String, Long> timeCheck = new HashMap<>();
    private static HashMap<String, Integer> highJump = new HashMap<>();

    public AntiSpeedThread() {
        Server.getInstance().getScheduler().scheduleAsyncTask(AntiCheatAPI.getInstance(), this);
    }

    public void onRun() {
        while (true) {
            try {
                Map<UUID, Player> players = new HashMap<>(Server.getInstance().getOnlinePlayers());
                for (Player player : players.values()) {
                    for (String name : highJump.keySet()) {
                        if (Server.getInstance().getPlayerExact(name) == null) {
                            highJump.remove(name);
                        }
                    }
                    if (positionHashMap.containsKey(player.getName())) {
                        if (!EventListener.tp.containsKey(player.getName())) {
                            Position from = positionHashMap.get(player.getName());
                            Position to = player.getPosition();
                            float move = ((float) Math.sqrt(Math.pow((int) from.x - (int) to.x, 2) + Math.pow((int) from.z - (int) to.z, 2))) - (float) 0.1 * (System.currentTimeMillis() - timeCheck.get(player.getName()) - 1000);
                            //putMove(player.getName(),move);
                            if (player.hasEffect(1)) {
                                Effect speed = player.getEffect(1);
                                if (speed.getAmplifier() > 0) {
                                    int i = speed.getAmplifier() + 1;
                                    if (i <= 2) {
                                        double fsp = (double) i * 0.2D;
                                        move = move - (float) fsp;
                                    } else {
                                        double fsp = (double) i * 0.4D;
                                        move = move - (float) fsp;
                                    }
                                }
                            }
                            if (move < 0) {
                                move = 0;
                            }
                            setFinalMove(player.getName(), move);
                            positionHashMap.put(player.getName(), player.getPosition());
                            timeCheck.put(player.getName(), System.currentTimeMillis());
                        } else {
                            if (EventListener.tp.get(player.getName()) > 0) {
                                EventListener.tp.put(player.getName(), EventListener.tp.get(player.getName()) - 1);
                                setFinalMove(player.getName(), 0);
                                positionHashMap.put(player.getName(), player.getPosition());
                                timeCheck.put(player.getName(), System.currentTimeMillis());
                            } else {
                                EventListener.tp.remove(player.getName());
                            }
                        }
                    } else {
                        //putMove(player.getName(),0F);
                        setFinalMove(player.getName(), 0F);
                        timeCheck.put(player.getName(), System.currentTimeMillis());
                        positionHashMap.put(player.getName(), player.getPosition());
                    }
                    if (!player.hasEffect(3)) {
                        MobEffectPacket pk = new MobEffectPacket();
                        pk.eid = player.getId();
                        pk.effectId = 3;
                        pk.eventId = 3;
                        player.dataPacket(pk);
                    }
                    if (EventListener.AntiTower.containsKey(player.getName())) {
                        double y = EventListener.AntiTower.get(player.getName()).y;
                        double jumpY = 1.45D;
                        if (player.hasEffect(Effect.JUMP)) {
                            jumpY = Math.pow((player.getEffect(Effect.JUMP).getAmplifier() + 4.2), 2) / 16D;
                            jumpY = jumpY + 0.1;
                        }
                        BigDecimal b1 = new BigDecimal(Double.toString(player.y));
                        BigDecimal b2 = new BigDecimal(Double.toString(y));
                        double dY = b1.subtract(b2).doubleValue();
                        if (dY > jumpY) {
                            int groundY = 1;
                            while (player.getLevel().getBlockIdAt((int) player.x, (int) player.y - groundY, (int) player.z) == 0 && player.isOnline()) {
                                groundY++;
                            }
                            groundY -= 1;
                            if (groundY > 1) {
                                //System.out.println("high Jump!!!");
                                player.setMotion(new Vector3(0, 0 - groundY, 0));
                                player.sendMessage(TextFormat.colorize("&cYou suspected to use HighJump cheat, please stop your behavior &eCheck: AntiCheat"));
                                if (highJump.containsKey(player.getName())) {
                                    highJump.put(player.getName(), highJump.get(player.getName()) + 1);
                                    if (highJump.get(player.getName()) >= 10) {
                                        Server.getInstance().broadcastMessage(TextFormat.colorize("&dPlayer &b" + player.getName() + " &6suspected to use high jump cheats kicked by AntiCheat!"));
                                        player.kick(TextFormat.colorize("&cYou suspected to use high jump cheats kicked out by anti-cheat\n&eIf you misjudge the following information to the administrator\nCheck: AntiCheat[HighJump] HighJump:" + highJump.get(player.getName()) + " Speed:" + AntiSpeedThread.getMove(player.getName())) + " onGround:" + player.isOnGround() + " inAirTick:" + player.getInAirTicks());
                                    }
                                } else {
                                    highJump.put(player.getName(), 1);
                                }
                            }
                        }
                        EventListener.AntiTower.remove(player.getName());
                    }
                    //player.sendTip(TextFormat.GREEN+"                                     Speed: "+getMove(player.getName()));
                    if (AntiCheatAPI.getInstance().getMasterConfig().getAntiSpeed()) {
                        AntiSpeed antiSpeed = new AntiSpeed(player);
                        if (antiSpeed.isCheat()) {
                            AntiCheatAPI.getInstance().addRecord(player, antiSpeed.getCheatType());
                            if (!EventListener.tp.containsKey(player.getName())) {
                                player.sendMessage(TextFormat.RED + "We detected that you used to accelerate. Perhaps this is a misjudgment.");
                                player.setMotion(new Vector3(0, 0, 0));
                            }
                        }
                    }
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setFinalMove(String name, float speed) {
        finalSpeed.put(name, speed);
    }

    public static float getMove(String name) {
        return finalSpeed.get(name);
        //return 0;
    }

    /*private void putMove(String name,float speed){
        HashMap<Integer,Float> move = new HashMap<>();
        if(moveSpeed.containsKey(name)){
            move = moveSpeed.get(name);
            if(move.get(1) < 0){
                move.put(1,speed);
                moveSpeed.put(name,move);
                return;
            }
            if(move.get(2) < 0){
                move.put(2,speed);
                moveSpeed.put(name,move);
                return;
            }
            if(move.get(3) < 0){
                move.put(3,speed);
                moveSpeed.put(name,move);
                return;
            }
            if(move.get(4) < 0){
                move.put(4,speed);
                moveSpeed.put(name,move);
                return;
            }
            if(move.get(5) < 0){
                move.put(5,speed);
                moveSpeed.put(name,move);
                return;
            }
            if(move.get(6) < 0){
                move.put(6,speed);
                moveSpeed.put(name,move);
                return;
            }
            if(move.get(7) < 0){
                move.put(7,speed);
                moveSpeed.put(name,move);
                return;
            }
            if(move.get(8) < 0){
                move.put(8,speed);
                moveSpeed.put(name,move);
                return;
            }
            float fs = (move.get(1) +move.get(2) + move.get(3)+move.get(4)+move.get(5)+move.get(6)+move.get(7)+move.get(8));
            fs = fs / 8;
            fs = fs*10;
            finalSpeed.put(name,fs);
            //Server.getInstance().getPlayer(name).sendPopup(TextFormat.GREEN+"Speed:"+ fs+"                             \n\n\n");
            move.put(1,-1F);
            move.put(2,-1F);
            move.put(3,-1F);
            move.put(4,-1F);
            move.put(5,-1F);
            move.put(6,-1F);
            move.put(7,-1F);
            move.put(8,-1F);
            moveSpeed.put(name,move);
        }else{
            move.put(1,-1F);
            move.put(2,-1F);
            move.put(3,-1F);
            move.put(4,-1F);
            move.put(5,-1F);
            move.put(6,-1F);
            move.put(7,-1F);
            move.put(8,-1F);
            moveSpeed.put(name,move);
            finalSpeed.put(name,0F);
        }
    }*/

}
