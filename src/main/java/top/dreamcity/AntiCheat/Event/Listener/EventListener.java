package top.dreamcity.AntiCheat.Event.Listener;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityTeleportEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.Vector2;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlayerActionPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.network.protocol.RemoveBlockPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;
import cn.nukkit.utils.TextFormat;
import top.dreamcity.AntiCheat.AntiCheat;
import top.dreamcity.AntiCheat.AntiCheatAPI;
import top.dreamcity.AntiCheat.Cheat.Study.StudySpeedThread;
import top.dreamcity.AntiCheat.Cheat.chat.CheckChat;
import top.dreamcity.AntiCheat.Cheat.chat.CheckWords;
import top.dreamcity.AntiCheat.Cheat.combat.AntiAutoAim;
import top.dreamcity.AntiCheat.Cheat.combat.NPC;
import top.dreamcity.AntiCheat.Cheat.combat.Reach;
import top.dreamcity.AntiCheat.Cheat.move.AntiSpeed;
import top.dreamcity.AntiCheat.Cheat.move.AntiSpeedThread;
import top.dreamcity.AntiCheat.Cheat.move.CheckBB;
import top.dreamcity.AntiCheat.Event.PlayerCheating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    public static HashMap<String,Position> AntiTower = new HashMap<>();
    public static HashMap<String,Integer> tp = new HashMap<>();


    @EventHandler
    public void onDataPacket(DataPacketReceiveEvent event){
        if(event.getPacket() instanceof PlayerActionPacket){
            /*
             * That maybe can't AntiTower,because JavaScript Tower can't send packet;
             * But this can AntiFly?
             */
            if(((PlayerActionPacket) event.getPacket()).action == PlayerActionPacket.ACTION_JUMP){
                Player player = event.getPlayer();
                if (!AntiTower.containsKey(event.getPlayer())) {
                    AntiTower.put(event.getPlayer().getName(), event.getPlayer().getPosition());
                } else {
                    if(player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z) == 0 &&
                            player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z - 1) == 0 &&
                            player.getLevel().getBlockIdAt((int) player.x, (int) player.y - 1, (int) player.z + 1) == 0 &&
                            player.getLevel().getBlockIdAt((int) player.x + 1, (int) player.y - 1, (int) player.z) == 0 &&
                            player.getLevel().getBlockIdAt((int) player.x - 1, (int) player.y - 1, (int) player.z) == 0) {
                        int groundY = 1;
                        while (player.getLevel().getBlockIdAt((int) player.x, (int) player.y - groundY, (int) player.z) == 0) {
                            groundY++;
                        }
                        groundY -= 1;
                        if (groundY > 2) {
                            player.teleport(new Vector3(player.x, player.y - groundY, player.z));
                        }
                    }
                }
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
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
        if (AntiCheatAPI.getInstance().getMasterConfig().getCheckBB()) {
            CheckBB checkBB = new CheckBB(player);
            if (checkBB.isCheat()) {
                AntiCheatAPI.getInstance().addRecord(player, checkBB.getCheatType());
                //player.sendMessage(TextFormat.RED+"We detected that you used to accelerate. Perhaps this is a misjudgment.");
                //player.teleport(player.getLocation().add(0,1.5,0));
                event.setCancelled();
            }
        }
        if (AntiCheatAPI.getInstance().getMasterConfig().getAntiAutoAim() && AntiAutoAim.containsKey(player.getName())) {
            AntiAutoAim.get(event.getPlayer().getName()).move(event.getPlayer());
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Reach reach = new Reach(event.getPlayer(),event.getBlock());
        if(reach.isCheat()){
            event.getPlayer().sendMessage(TextFormat.RED+"Maybe you used Reach!!!");
            event.setCancelled();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void   onBlockBreak(BlockBreakEvent event){
        /*Player player = event.getPlayer();
        double breakTime = event.getBlock().getBreakTime(event.getPlayer().getInventory().getItemInHand(), event.getPlayer());
        if (player.isCreative() && breakTime > 0.15D) {
            breakTime = 0.15D;
        }

        if (player.hasEffect(3)) {
            breakTime *= 1.0D - 0.2D * (double)(player.getEffect(3).getAmplifier() + 1);
        }

        if (player.hasEffect(4)) {
            breakTime *= 1.0D - 0.3D * (double)(player.getEffect(4).getAmplifier() + 1);
        }

        Enchantment eff = event.getPlayer().getInventory().getItemInHand().getEnchantment(15);
        if (eff != null && eff.getLevel() > 0) {
            breakTime *= 1.0D - 0.3D * (double)eff.getLevel();
        }
        if((double)player.lastBreak - (double)System.currentTimeMillis() < (breakTime - 1)*1000D){
            //event.setCancelled();
        }*/

    }

     public void onEntitDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            Reach reach = new Reach((Player)event.getDamager(),event.getEntity());
            if(reach.isCheat() && !event.getCause().equals(EntityDamageByEntityEvent.DamageCause.PROJECTILE)){
                ((Player) event.getDamager()).sendMessage(TextFormat.RED+"Maybe you used Reach!!!");
                event.setCancelled();
            }
            event.setKnockBack(0.25F); // Nukkit KnockBack is so ‘long’
        }
        if(event.getEntity() instanceof NPC){
            event.setCancelled();
        }
    }



    @EventHandler
    public void PlayerCheatingEvent(PlayerCheating event){
        if(event.getPlayer() != null && !event.getCheatType().getTypeName().equals("bb")){
            Server.getInstance().getLogger().warning("Player "+event.getPlayer().getName()+" cheating,type: " +event.getCheatType().getTypeName());
        }
    }

}
