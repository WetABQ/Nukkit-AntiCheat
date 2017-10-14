package top.dreamcity.AntiCheat.Config;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import top.dreamcity.AntiCheat.AntiCheat;

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
public class MasterConfig {

    private ConfigSection config;

    private Boolean isEmpty;

    private Boolean antiSpeed;

    private Boolean checkBB;

    private Boolean antiFly;

    private Boolean checkChat;

    private Boolean checkChatWord;

    private Boolean antiKillAura;

    private Boolean antiAutoAim;

    private Boolean antiSpeedPingCheck;

    private float maxMoveSpeed;

    private int pingNoCheckValue;

    private int inAirTimeCheck;

    private int chatSec;

    private int checkKillAuraCPS;

    private ArrayList<String> SensitiveWords;


    public MasterConfig(ConfigSection configSection){
        config = configSection;
        isEmpty = config.isEmpty();
        init();
    }

    public void init(){
        if(!isEmpty){
            antiSpeed = config.getBoolean("antiSpeed");
            checkBB = config.getBoolean("checkBB");
            antiFly = config.getBoolean("antiFly ");
            checkChat = config.getBoolean("checkChat");
            checkChatWord = config.getBoolean("checkChatWord");
            antiKillAura = config.getBoolean("antiKillAura");
            antiAutoAim = config.getBoolean("antiAutoAim");
            antiSpeedPingCheck = config.getBoolean("antiSpeedPingCheck");
            maxMoveSpeed = (float) config.getDouble("maxMoveSpeed");
            pingNoCheckValue = config.getInt("pingNoCheckValue");
            inAirTimeCheck = config.getInt("inAirTimeCheck");
            chatSec = config.getInt("chatSec");
            checkKillAuraCPS = config.getInt("checkKillAuraCPS");
            SensitiveWords = (ArrayList) config.get("SensitiveWords");
        }else{
            spawnDefaultConfig();
        }
    }

    public void spawnDefaultConfig(){
        AntiCheat.getInstance().getLogger().notice("Start spawning default config.");
        antiSpeed = true;
        checkBB = true;
        antiFly = true;
        checkChat = true;
        checkChatWord = true;
        antiKillAura = true;
        antiAutoAim = true;
        antiSpeedPingCheck = true;
        maxMoveSpeed = 6F;
        pingNoCheckValue = 25;
        inAirTimeCheck = 5;
        chatSec = 2;
        checkKillAuraCPS = 3;
        SensitiveWords = new ArrayList<>();
        SensitiveWords.add("fuck");
        SensitiveWords.add("shit");
        save();
    }

    public void save(){
        try {
            config.put("antiSpeed", antiSpeed);
            config.put("antiSpeedPingCheck", antiSpeedPingCheck);
            config.put("pingNoCheckValue", pingNoCheckValue);
            config.put("maxMoveSpeed", maxMoveSpeed);
            config.put("checkBB", checkBB);
            config.put("antiFly", antiFly);
            config.put("inAirTimeCheck", inAirTimeCheck);
            config.put("checkChat", checkChat);
            config.put("chatSec", chatSec);
            config.put("checkChatWord", checkChatWord);
            config.put("SensitiveWords", SensitiveWords);
            config.put("antiKillAura", antiKillAura);
            config.put("antiAutoAim", antiAutoAim);
            config.put("checkKillAuraCPS", checkKillAuraCPS);
            Config c = new Config(AntiCheat.getInstance().getDataFolder() + "/config.yml", Config.YAML);
            c.setAll(config);
            c.save();
        }catch (NullPointerException e){
            spawnDefaultConfig();
            save();
        }
    }

    public boolean isEmpty(){
        return isEmpty;
    }

    public int getChatSec() {
        return chatSec;
    }

    public int getCheckKillAuraCPS() {
        return checkKillAuraCPS;
    }

    public int getInAirTimeCheck() {
        return inAirTimeCheck;
    }

    public int getPingNoCheckValue() {
        return pingNoCheckValue;
    }

    public ArrayList<String> getSensitiveWords() {
        return SensitiveWords;
    }

    public Boolean getAntiAutoAim() {
        return antiAutoAim;
    }

    public Boolean getAntiFly() {
        return antiFly;
    }

    public Boolean getAntiSpeed() {
        return antiSpeed;
    }

    public Boolean getAntiSpeedPingCheck() {
        return antiSpeedPingCheck;
    }

    public Boolean getCheckBB() {
        return checkBB;
    }

    public Boolean getCheckChat() {
        return checkChat;
    }

    public Boolean getCheckChatWord() {
        return checkChatWord;
    }

    public Boolean getAntiKillAura() {
        return antiKillAura;
    }

    public float getMaxMoveSpeed() {
        return maxMoveSpeed;
    }
}
