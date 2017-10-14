package top.dreamcity.AntiCheat.Cheat;

import cn.nukkit.Player;

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
public abstract class AntiCheat {
    protected Player player;

    public AntiCheat(Player player){
        this.player = player;
    }

    abstract public CheatType getCheatType();

    abstract public boolean isCheat();


    public enum CheatType{
        SPEED("speed"),
        BOUNDING_BOX("bb"),
        FLY("fly"),
        FAST_CHAT("fastchat"),
        SENSITIVE_WORDS("sensitivewords"),
        KILL_AURA("killaura"),
        AUTO_AIM("autoaim");

        private String typeName;

        CheatType(String typeName){
            this.typeName = typeName;
        }

        public static CheatType fromTypeName(String typeName) {
            for (CheatType type : CheatType.values()) {
                if (type.getTypeName().equals(typeName)) {
                    return type;
                }
            }
            return null;
        }

        public String getTypeName() {
            return this.typeName;
        }

    }
}
