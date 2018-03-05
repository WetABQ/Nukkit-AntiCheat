package top.dreamcity.AntiCheat.Cheat.combat;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import top.dreamcity.AntiCheat.Event.CheckCheatEvent;
import top.dreamcity.AntiCheat.Event.PlayerCheating;

/**
 * Copyright © 2016 WetABQ&DreamCityAdminGroup All right reserved.
 * Welcome to DreamCity Server Address:dreamcity.top:19132
 * Created by WetABQ(Administrator) on 2017/12/4.
 * |||    ||    ||||                           ||        ||||||||     |||||||
 * |||   |||    |||               ||         ||  |      |||     ||   |||    |||
 * |||   |||    ||     ||||||  ||||||||     ||   ||      ||  ||||   |||      ||
 * ||  |||||   ||   |||   ||  ||||        ||| |||||     ||||||||   |        ||
 * ||  || ||  ||    ||  ||      |        |||||||| ||    ||     ||| ||      ||
 * ||||   ||||     ||    ||    ||  ||  |||       |||  ||||   |||   ||||||||
 * ||     |||      |||||||     |||||  |||       |||| ||||||||      |||||    |
 * ||||
 */
public class Reach extends Combat {
    public Block block = null;
    public Entity entity = null;


    public Reach(Player player, Block block) {
        super(player);
        this.block = block;
    }

    public Reach(Player player, Entity entity) {
        super(player);
        this.entity = entity;
    }

    @Override
    public CheatType getCheatType() {
        return CheatType.REACH;
    }


    @Override
    public boolean isCheat() {
        CheckCheatEvent event = new CheckCheatEvent(player, getCheatType());
        Server.getInstance().getPluginManager().callEvent(event);
        if (player.getGamemode() != 0) event.setCancelled();

        if (event.isCancelled()) return false;
        boolean flag = false;
        if (entity != null) {
            if (entity.distance(player) >= 4) {
                flag = true;
            }
        } else if (block != null) {
            if (block.distance(player) >= 6) {
                flag = true;
            }
        }
        if (flag) {
            PlayerCheating event2 = new PlayerCheating(player, getCheatType());
            Server.getInstance().getPluginManager().callEvent(event2);
            return !event2.isCancelled();
        }
        return false;
    }

}


