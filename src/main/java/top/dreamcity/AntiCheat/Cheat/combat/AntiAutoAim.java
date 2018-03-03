package top.dreamcity.AntiCheat.Cheat.combat;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import top.dreamcity.AntiCheat.AntiCheatAPI;
import top.dreamcity.AntiCheat.Event.CheckCheatEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


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
        addDummy();
    }
    private NPC npc;

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
        /*ddPlayerPacket pk = new AddPlayerPacket();
        eid = pk.entityRuntimeId = pk.entityUniqueId = Entity.entityCount++;
        pk.item = Item.get(Item.BOW);
        pk.speedX = pk.speedY = pk.speedZ = 0;
        pk.pitch = pk.yaw = 90;
        pk.metadata = new EntityMetadata().put(new FloatEntityData(39, 0.1F));
        pk.x = (float) player.getX();
        pk.y = (float) player.getY() + 1.5F;
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
        player.dataPacket(pk4);*/
        byte[] skin = image(AntiCheatAPI.getInstance().getMasterConfig().getSkinPath());
        NPC npc = new NPC(new Position(player.getX(), player.getY()+3, player.getZ(), player.getLevel()), skin, player);
        npc.setNameTag("AntiCheat");
        npc.setScale(0.0001F);
        this.npc = npc;
    }

    public void move(Player player){
        npc.teleport(new Location(player.x,player.y,player.z,player.yaw,player.pitch));
    }

    private static byte[] image(String path){
        File file = new File(path);
        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IOException var5) {
            throw new RuntimeException(var5);
        }
        return parseBufferedImage(image);
    }

    private static byte[] parseBufferedImage(BufferedImage image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for(int y = 0; y < image.getHeight(); ++y) {
            for(int x = 0; x < image.getWidth(); ++x) {
                Color color = new Color(image.getRGB(x, y), true);
                outputStream.write(color.getRed());
                outputStream.write(color.getGreen());
                outputStream.write(color.getBlue());
                outputStream.write(color.getAlpha());
            }
        }

        image.flush();
        return outputStream.toByteArray();
    }
}
