package top.dreamcity.AntiCheat.Cheat.combat;


import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.*;

/**
 * Copyright © 2016 WetABQ&DreamCityAdminGroup All right reserved.
 * Welcome to DreamCity Server Address:dreamcity.top:19132
 * Created by WetABQ(Administrator) on 2017/9/16.
 * |||    ||    ||||                           ||        ||||||||     |||||||
 * |||   |||    |||               ||         ||  |      |||     ||   |||    |||
 * |||   |||    ||     ||||||  ||||||||     ||   ||      ||  ||||   |||      ||
 * ||  |||||   ||   |||   ||  ||||        ||| |||||     ||||||||   |        ||
 * ||  || ||  ||    ||  ||      |        |||||||| ||    ||     ||| ||      ||
 * ||||   ||||     ||    ||    ||  ||  |||       |||  ||||   |||   ||||||||
 * ||     |||      |||||||     |||||  |||       |||| ||||||||      |||||    |
 * ||||
 */
public class NPC extends EntityHuman{

    public NPC(Position pos, byte[] skin,Player player) {
        super(pos.getLevel().getChunk((int) pos.getX() >> 4, (int) pos.getZ() >> 4), getEntityNBT(pos,skin));
        this.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_ALWAYS_SHOW_NAMETAG, false);
        this.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_CAN_SHOW_NAMETAG, false);
        this.setHealth(999);
        this.setMaxHealth(999);
        this.setMovementSpeed(0);
        if(!this.hasSpawned.containsValue(player)){
            this.spawnTo(player);
        }
    }

    private static CompoundTag getEntityNBT(Vector3 position,byte[] skin) {
        return new CompoundTag()
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("", position.x))
                        .add(new DoubleTag("", position.y))
                        .add(new DoubleTag("", position.z)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", 0))
                        .add(new FloatTag("", 0)))
                .putCompound("Skin",new CompoundTag()
                        .putByteArray("Data", skin)
                        .putString("ModelId","Standard_Steve")
                )
                .putString("NameTag","xxx");
    }
}
