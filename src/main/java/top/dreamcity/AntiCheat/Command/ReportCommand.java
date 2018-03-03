package top.dreamcity.AntiCheat.Command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import top.dreamcity.AntiCheat.AntiCheatAPI;
import top.dreamcity.AntiCheat.Cheat.AntiCheat;
import top.dreamcity.AntiCheat.Cheat.move.ReportFlyThread;
import top.dreamcity.AntiCheat.Cheat.move.ReportSpeedThread;

import java.util.HashMap;

/**
 * Copyright © 2016 WetABQ&DreamCityAdminGroup All right reserved.
 * Welcome to DreamCity Server Address:dreamcity.top:19132
 * Created by WetABQ(Administrator) on 2017/11/16.
 * |||    ||    ||||                           ||        ||||||||     |||||||
 * |||   |||    |||               ||         ||  |      |||     ||   |||    |||
 * |||   |||    ||     ||||||  ||||||||     ||   ||      ||  ||||   |||      ||
 * ||  |||||   ||   |||   ||  ||||        ||| |||||     ||||||||   |        ||
 * ||  || ||  ||    ||  ||      |        |||||||| ||    ||     ||| ||      ||
 * ||||   ||||     ||    ||    ||  ||  |||       |||  ||||   |||   ||||||||
 * ||     |||      |||||||     |||||  |||       |||| ||||||||      |||||    |
 * ||||
 */
public class ReportCommand extends Command{

    public ReportCommand(){
        super("report");
        this.setDescription("Report to the system to cheat players!");
        this.setAliases(new String[]{
                "r"
        });
        this.setUsage("/r or /report <Player Name> <Cheat Type>");
        this.setCommandParameters(new HashMap<String, CommandParameter[]>() {
            {
                put("1arg", new CommandParameter[]{
                        new CommandParameter("Player Name", CommandParameter.ARG_TYPE_PLAYER, false),
                        new CommandParameter("Cheat Type", CommandParameter.ARG_TYPE_RAW_TEXT, false)
                });
            }
        });
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            if (args.length != 2) {
                return false;
            }
            Player p = Server.getInstance().getPlayer(args[0]);
            if (p == null) {
                sender.sendMessage(TextFormat.RED + "Player isn't online");
                return true;
            }
            int type = -1;
            switch (args[1].toLowerCase()) {
                case "加速":
                case "速度":
                case "speed":
                case "s":
                case "sp":
                    type = 0;
                    break;
                case "飞行":
                case "f":
                case "fly":
                    type = 1;
                    break;
            }
            AntiCheat.CheatType cheatType = null;
            switch (type) {
                case 0:
                    cheatType = AntiCheat.CheatType.SPEED;
                    break;
                case 1:
                    cheatType = AntiCheat.CheatType.FLY;
                    break;
                default:
                    sender.sendMessage(TextFormat.RED + "Unknown type! For example: fly f 飞行 s sp speed 速度 加速");
                    return true;
            }
            if(AntiCheatAPI.getInstance().reportPlayer.containsKey(p.getName())){
                sender.sendMessage(TextFormat.GREEN + "Please do not repeat the report");
                return true;
            }
            AntiCheatAPI.getInstance().reportPlayer.put(p.getName(), cheatType);
            Server.getInstance().getLogger().warning("Player "+sender.getName()+" report "+p.getName()+" cheat type:"+cheatType.getTypeName());
            sender.sendMessage(TextFormat.GREEN + "You successfully reported the player " + p.getName() + " cheat type " + cheatType.getTypeName());
            addReportThread(p,cheatType);
        } else {
            sender.sendMessage(TextFormat.RED + "You must run in game!");
        }
        return true;
    }


    private void addReportThread(Player player, AntiCheat.CheatType type){
        switch(type.getTypeName()){
            case "fly":
                AntiCheatAPI.getInstance().reportThread.put(player.getName(),new ReportFlyThread(player));
                break;
            case "speed":
                AntiCheatAPI.getInstance().reportThread.put(player.getName(),new ReportSpeedThread(player));
                break;
            default:
                AntiCheatAPI.getInstance().reportThread.put(player.getName(),new ReportFlyThread(player));
                AntiCheatAPI.getInstance().reportThread.put(player.getName(),new ReportSpeedThread(player));
                break;
        }
    }

}
