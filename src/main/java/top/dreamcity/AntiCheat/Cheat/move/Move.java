package top.dreamcity.AntiCheat.Cheat.move;

import cn.nukkit.Player;
import top.dreamcity.AntiCheat.Cheat.AntiCheat;

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
public abstract class Move extends AntiCheat {

    protected float playerMoveSpeed;

    public Move(Player player) {
        super(player);
        playerMoveSpeed = AntiSpeedThread.getMove(player.getName());
    }

    public float getPlayerMoveSpeed() {
        return playerMoveSpeed;
    }
}
