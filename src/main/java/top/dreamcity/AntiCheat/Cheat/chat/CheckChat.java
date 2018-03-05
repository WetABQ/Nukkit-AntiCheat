package top.dreamcity.AntiCheat.Cheat.chat;

import cn.nukkit.Player;
import cn.nukkit.Server;
import top.dreamcity.AntiCheat.Event.CheckCheatEvent;
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
public class CheckChat extends Chat {

    public CheckChat(Player player, String message) {
        super(player, message);
    }

    @Override
    public CheatType getCheatType() {
        return CheatType.FAST_CHAT;
    }

    @Override
    public boolean isCheat() {
        CheckCheatEvent event = new CheckCheatEvent(player, getCheatType());
        Server.getInstance().getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;
        if (CheckChatThread.hasPlayer(player.getName())) {
            PlayerCheating event2 = new PlayerCheating(player, getCheatType());
            Server.getInstance().getPluginManager().callEvent(event2);
            return !event.isCancelled();
        } else {
            CheckChatThread.addPlayer(player.getName());
        }
        return false;
    }

}
