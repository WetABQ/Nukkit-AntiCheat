package top.dreamcity.AntiCheat.Event.Listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.utils.TextFormat;
import top.dreamcity.AntiCheat.AntiCheatAPI;
import top.dreamcity.AntiCheat.Cheat.chat.CheckChat;
import top.dreamcity.AntiCheat.Cheat.chat.CheckWords;
import top.dreamcity.AntiCheat.Cheat.combat.AntiAutoAim;
import top.dreamcity.AntiCheat.Cheat.move.AntiSpeed;
import top.dreamcity.AntiCheat.Cheat.move.CheckBB;

import java.util.HashMap;

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
public class EventListener implements Listener {

    private HashMap<String, AntiAutoAim> AntiAutoAim = new HashMap<>();

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String msg = event.getMessage();
        CheckChat checkChat = new CheckChat(player, msg);
        CheckWords checkWords = new CheckWords(player, msg);
        if (AntiCheatAPI.getInstance().getMasterConfig().getCheckChat() && checkChat.isCheat()) {
            AntiCheatAPI.getInstance().addRecord(player, checkChat.getCheatType());
            player.sendMessage(TextFormat.RED + "You chat faster than predetermined value.");
            event.setCancelled();
        }
        if (AntiCheatAPI.getInstance().getMasterConfig().getCheckChatWord() && checkWords.isCheat()) {
            event.setMessage(checkWords.ChangeMessage());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        if (AntiCheatAPI.getInstance().getMasterConfig().getAntiAutoAim()) {
            AntiAutoAim.put(event.getPlayer().getName(), new AntiAutoAim(event.getPlayer()));
            event.getPlayer().sendMessage(TextFormat.GOLD + "The server enable AntiAutoAim!");
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (AntiAutoAim.containsKey(player.getName())) {
            AntiAutoAim.get(player.getName()).move(player);
        } else {
            AntiAutoAim.put(player.getName(), new AntiAutoAim(event.getPlayer()));
        }
        if (AntiCheatAPI.getInstance().getMasterConfig().getAntiSpeed()) {
            AntiSpeed antiSpeed = new AntiSpeed(player);
            if (antiSpeed.isCheat()) {
                AntiCheatAPI.getInstance().addRecord(player, antiSpeed.getCheatType());
                player.sendMessage(TextFormat.RED + "We detected that you used to accelerate. Perhaps this is a misjudgment.");
                event.setCancelled();
            }
        }
        if (AntiCheatAPI.getInstance().getMasterConfig().getCheckBB()) {
            CheckBB checkBB = new CheckBB(player);
            if (checkBB.isCheat()) {
                AntiCheatAPI.getInstance().addRecord(player, checkBB.getCheatType());
                //player.sendMessage(TextFormat.RED+"We detected that you used to accelerate. Perhaps this is a misjudgment.");
                //player.teleport(player.getLocation().add(0,1.5,0));
                event.setCancelled();
            }
        }
    }

}
