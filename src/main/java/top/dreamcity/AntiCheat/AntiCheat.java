package top.dreamcity.AntiCheat;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import top.dreamcity.AntiCheat.Cheat.Report;
import top.dreamcity.AntiCheat.Cheat.chat.CheckChatThread;
import top.dreamcity.AntiCheat.Cheat.move.AntiFlyThread;
import top.dreamcity.AntiCheat.Cheat.move.AntiSpeedThread;
import top.dreamcity.AntiCheat.Cheat.move.AntiWaterWalkThread;
import top.dreamcity.AntiCheat.Command.ReportCommand;
import top.dreamcity.AntiCheat.Config.MasterConfig;
import top.dreamcity.AntiCheat.Config.PlayerCheatRecord;
import top.dreamcity.AntiCheat.Event.Listener.EventListener;

import java.util.HashMap;
import java.util.HashSet;

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
public class AntiCheat extends PluginBase implements AntiCheatAPI {

    private static AntiCheat instance;
    private static MasterConfig masterConfig;
    private PlayerCheatRecord playerCheatRecord;
    public static HashMap<String, top.dreamcity.AntiCheat.Cheat.AntiCheat.CheatType> reportPlayer = new HashMap<>();
    public static HashMap<String, Report> reportThread = new HashMap<>();
    public static HashSet<String> DemoPlayer = new HashSet<>();

    public static AntiCheat getInstance() {
        return instance;
    }


    @Override
    public void onLoad() {
        instance = this;
        this.saveResource("Steve.png");
        this.getLogger().notice("AntiCheat - Load");
    }

    @Override
    public void onEnable() {

        initConfig();

        initAntiThread();

        Server.getInstance().getCommandMap().register("", new ReportCommand());

        this.getServer().getPluginManager().registerEvents(new EventListener(), this);

        this.getLogger().notice("AntiCheat - Enable");
    }

    @Override
    public void onDisable() {
        this.getLogger().notice("AntiCheat - Disable");
    }

    private void initAntiThread() {
        //this.getServer().getScheduler().scheduleRepeatingTask(new AntiSpeedThread(this),1);
        new AntiSpeedThread();
        new CheckChatThread();
        new AntiFlyThread();
        new AntiWaterWalkThread();
    }

    private void initConfig() {
        Config c = new Config(this.getDataFolder() + "/config.yml", Config.YAML);
        masterConfig = new MasterConfig(c.getRootSection());
        if (masterConfig.isEmpty()) {
            this.getLogger().warning("The Config is empty!");
        }
        playerCheatRecord = new PlayerCheatRecord(new Config(this.getDataFolder() + "/record.yml", Config.YAML).getRootSection());
    }

    @Override
    public void addRecord(Player player, top.dreamcity.AntiCheat.Cheat.AntiCheat.CheatType cheatType) {
        playerCheatRecord.addRecord(player, cheatType);
    }

    @Override
    public MasterConfig getMasterConfig() {
        return masterConfig;
    }

}
