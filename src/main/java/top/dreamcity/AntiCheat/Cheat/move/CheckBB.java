package top.dreamcity.AntiCheat.Cheat.move;


import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.math.AxisAlignedBB;
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
public class CheckBB extends Move {

    public CheckBB(Player player) {
        super(player);
    }

    @Override
    public CheatType getCheatType() {
        return CheatType.BOUNDING_BOX;
    }

    @Override
    public boolean isCheat() {
        CheckCheatEvent event = new CheckCheatEvent(player, getCheatType());
        Server.getInstance().getPluginManager().callEvent(event);
        if (player.getGamemode() != 0) event.setCancelled();
        if (event.isCancelled()) return false;
        double radius = (double) player.getWidth() / 2.0D;
        AxisAlignedBB bb = player.getBoundingBox().clone().setBounds(
                player.x - radius + 0.2D, player.y + 0.85D, player.z - radius + 0.2D,
                player.x + radius - 0.2D, player.y + (double) (player.getHeight() * player.scale), player.z + radius - 0.2D
        );
        for (Block block : player.getBlocksAround()) {
            if (block.collidesWithBB(bb)) {
                PlayerCheating event2 = new PlayerCheating(player, getCheatType());
                Server.getInstance().getPluginManager().callEvent(event2);
                return !event.isCancelled();
                //player.teleport(player.getPosition().clone().add(0, 1, 0));
            }
        }
        return false;
    }
}
