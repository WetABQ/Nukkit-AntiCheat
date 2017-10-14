package top.dreamcity.AntiCheat.Cheat.chat;

import cn.nukkit.Player;
import cn.nukkit.Server;
import top.dreamcity.AntiCheat.AntiCheatAPI;
import top.dreamcity.AntiCheat.Event.CheckCheatEvent;
import top.dreamcity.AntiCheat.Event.PlayerCheating;

import java.util.ArrayList;

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
public class CheckWords extends Chat {

    public CheckWords(Player player, String message) {
        super(player, message);
    }

    @Override
    public CheatType getCheatType() {
        return CheatType.SENSITIVE_WORDS;
    }

    @Override
    public boolean isCheat() {
        CheckCheatEvent event = new CheckCheatEvent(player, getCheatType());
        Server.getInstance().getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;
        ArrayList<String> list = AntiCheatAPI.getInstance().getMasterConfig().getSensitiveWords();
        for (String sw : list) {
            if (message.contains(sw)) {
                PlayerCheating event2 = new PlayerCheating(player, getCheatType());
                Server.getInstance().getPluginManager().callEvent(event2);
                return !event.isCancelled();
            }
        }
        return false;
    }

    public String ChangeMessage() {
        if (isCheat()) {
            ArrayList<String> list = AntiCheatAPI.getInstance().getMasterConfig().getSensitiveWords();
            for (String sw : list) {
                message = message.replaceAll(sw, "**");
            }
        }
        return message;
    }

}
