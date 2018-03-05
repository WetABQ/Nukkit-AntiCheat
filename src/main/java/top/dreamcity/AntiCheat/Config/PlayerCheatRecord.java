package top.dreamcity.AntiCheat.Config;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import top.dreamcity.AntiCheat.AntiCheat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright © 2017 WetABQ&DreamCityAdminGroup All right reserved.
 * Welcome to DreamCity Server Address:dreamcity.top:19132
 * Created by WetABQ(Administrator) on 2017/10/14.
 * |||    ||    ||||                           ||        ||||||||     |||||||
 * |||   |||    |||               ||         ||  |      |||     ||   |||    |||
 * |||   |||    ||     ||||||  ||||||||     ||   ||      ||  ||||   |||      ||
 * ||  |||||   ||   |||   ||  ||||        ||| |||||     ||||||||   |        ||
 * ||  || ||  ||    ||  ||      |        |||||||| ||    ||     ||| ||      ||
 * ||||   ||||     ||    ||    ||  ||  |||       |||  ||||   |||   ||||||||
 * ||     |||      |||||||     |||||  |||       |||| ||||||||      |||||    |
 * ||||
 */
public class PlayerCheatRecord {

    private ConfigSection config;

    public PlayerCheatRecord(ConfigSection configSection) {
        config = configSection;
    }

    public void addRecord(Player player, top.dreamcity.AntiCheat.Cheat.AntiCheat.CheatType cheatType) {
        int id = config.size() + 1;
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = format.format(date);
        config.put("#" + id + "|Time:" + time, "Player " + player.getName() + "(" + player.getLocation().toString() + ")[HP:" + player.getHealth() + "/" + player.getMaxHealth() + "] try cheating(Type:" + cheatType.getTypeName() + ").");
        save();
    }

    private void save() {
        Config c = new Config(AntiCheat.getInstance().getDataFolder() + "/record.yml", Config.YAML);
        c.setAll(config);
        c.save();
    }

}
