package top.dreamcity.AntiCheat.Cheat.move;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;

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
public class AntiSpeedThread implements Runnable{
    private Thread thread;
    private static HashMap<String,HashMap<Integer,Float>> moveSpeed = new HashMap<>();
    private static HashMap<String,Float> finalSpeed = new HashMap<>();
    private static HashMap<String,Position> positionHashMap = new HashMap<>();

    public AntiSpeedThread(){
        thread = new Thread(this);
        thread.start();
    }

    public void run(){
        while (true){
            try{
                for (Player player : Server.getInstance().getOnlinePlayers().values()){
                    if (positionHashMap.containsKey(player.getName())){
                        Position from = positionHashMap.get(player.getName());
                        Position to = player.getPosition();
                        float move = ((float) Math.sqrt(Math.pow(from.x - to.x,2)+Math.pow(from.z - to.z,2)));
                        putMove(player.getName(),move);
                        positionHashMap.put(player.getName(),player.getPosition());
                    } else {
                        putMove(player.getName(),0F);
                        positionHashMap.put(player.getName(),player.getPosition());
                    }
                }
                thread.sleep(50);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void setFinalMove(String name,float speed){
        finalSpeed.put(name,speed);
    }

    public static float getMove(String name){
        return finalSpeed.get(name);
    }

    private void putMove(String name,float speed){
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
            Server.getInstance().getPlayer(name).sendPopup(TextFormat.GREEN+"Speed:"+ fs+"                             \n\n\n");
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
    }
}
