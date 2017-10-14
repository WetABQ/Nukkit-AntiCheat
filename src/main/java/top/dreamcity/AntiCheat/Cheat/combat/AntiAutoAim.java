package top.dreamcity.AntiCheat.Cheat.combat;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.entity.data.FloatEntityData;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.AddPlayerPacket;
import cn.nukkit.network.protocol.MoveEntityPacket;
import top.dreamcity.AntiCheat.Event.CheckCheatEvent;

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
public class AntiAutoAim extends Combat {
    public AntiAutoAim(Player player) {
        super(player);
    }
    private long eid;

    @Override
    public CheatType getCheatType() {
        return CheatType.AUTO_AIM;
    }

    @Override
    public boolean isCheat() {
        CheckCheatEvent event = new CheckCheatEvent(player, getCheatType());
        Server.getInstance().getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;
        addDummy();
        return false;
    }

    private void addDummy() {
        AddPlayerPacket pk = new AddPlayerPacket();
        eid = pk.entityRuntimeId = pk.entityUniqueId = Entity.entityCount++;
        pk.item = Item.get(Item.BOW);
        pk.speedX = pk.speedY = pk.speedZ = 0;
        pk.pitch = pk.yaw = 90;
        pk.metadata = new EntityMetadata().put(new FloatEntityData(39, 0.1F));
        pk.x = (float) player.getX();
        pk.y = (float) player.getY();
        pk.z = (float) player.getZ();
        AddPlayerPacket pk1 = ((AddPlayerPacket) (pk.clone()));
        AddPlayerPacket pk2 = ((AddPlayerPacket) (pk.clone()));
        AddPlayerPacket pk3 = ((AddPlayerPacket) (pk.clone()));
        AddPlayerPacket pk4 = ((AddPlayerPacket) (pk.clone()));
        pk1.x++;
        pk2.x--;
        pk3.z++;
        pk4.z--;
        player.dataPacket(pk1);
        player.dataPacket(pk2);
        player.dataPacket(pk3);
        player.dataPacket(pk4);
    }

    public void move(Player player){
        MoveEntityPacket pk = new MoveEntityPacket();
        pk.x = player.x;
        pk.y = player.y;
        pk.z = player.z;
        pk.eid = eid;
        pk.yaw = pk.pitch = pk.headYaw = 0.0D;
        player.dataPacket(pk);
    }
}
