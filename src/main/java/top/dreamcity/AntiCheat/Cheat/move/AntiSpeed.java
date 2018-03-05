package top.dreamcity.AntiCheat.Cheat.move;

import cn.nukkit.Player;
import cn.nukkit.Server;
import top.dreamcity.AntiCheat.AntiCheatAPI;
import top.dreamcity.AntiCheat.Config.MasterConfig;
import top.dreamcity.AntiCheat.Event.PlayerCheating;

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
public class AntiSpeed extends Move {
    public AntiSpeed(Player player) {
        super(player);
    }

    @Override
    public CheatType getCheatType() {
        return CheatType.SPEED;
    }

    @Override
    public boolean isCheat() {
        //CheckCheatEvent event = new CheckCheatEvent(player, getCheatType());
        //Server.getInstance().getPluginManager().callEvent(event);
        if (player.getGamemode() != 0) return false;
        //if (event.isCancelled()) return false;
        Boolean flag = false;
        MasterConfig config = AntiCheatAPI.getInstance().getMasterConfig();
        if (playerMoveSpeed >= config.getMaxMoveSpeed()) {
            if (!player.hasEffect(1)) {
                if (config.getAntiSpeedPingCheck()) {
                    if (player.getPing() < config.getPingNoCheckValue()) {
                        flag = true;
                    }
                } else {
                    flag = true;
                }
            } else {
                if (config.getAntiSpeedPingCheck()) {
                    if (player.getPing() < config.getPingNoCheckValue()) {
                        flag = true;
                    }
                } else {
                    flag = true;
                }
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
