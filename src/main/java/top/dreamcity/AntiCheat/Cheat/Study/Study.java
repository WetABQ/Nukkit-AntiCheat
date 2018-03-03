package top.dreamcity.AntiCheat.Cheat.Study;

import cn.nukkit.Server;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2016 WetABQ&DreamCityAdminGroup All right reserved.
 * Welcome to DreamCity Server Address:dreamcity.top:19132
 * Created by WetABQ(Administrator) on 2017/12/23.
 * |||    ||    ||||                           ||        ||||||||     |||||||
 * |||   |||    |||               ||         ||  |      |||     ||   |||    |||
 * |||   |||    ||     ||||||  ||||||||     ||   ||      ||  ||||   |||      ||
 * ||  |||||   ||   |||   ||  ||||        ||| |||||     ||||||||   |        ||
 * ||  || ||  ||    ||  ||      |        |||||||| ||    ||     ||| ||      ||
 * ||||   ||||     ||    ||    ||  ||  |||       |||  ||||   |||   ||||||||
 * ||     |||      |||||||     |||||  |||       |||| ||||||||      |||||    |
 * ||||
 */
public class Study {
    public final static String API_ADDRESS_SPEED = "http://14.29.54.37:88/AntiCheat/SpeedCheat.php";

    public static void SpeedStudy(double maxSpeed,double avgSpeed,boolean isCheat){
        sendGet(API_ADDRESS_SPEED,"maxspeed="+maxSpeed+"&averageSpeed="+avgSpeed+"&isCheating="+isCheat);
    }

    public static boolean SpeedPredict(double maxSpeed,double avgSpeed){
        try {
            Gson gson = new Gson();
            Map<?, ?> json = gson.fromJson(sendGet(API_ADDRESS_SPEED, "maxspeed=" + maxSpeed + "&averageSpeed=" + avgSpeed), Map.class);
            return json.get("isCheating").toString().equals("true");
        }catch (Exception e){
            Server.getInstance().getLogger().error("AntiCheat-MLSystem >> Error to predict speed cheat.");
            return false;
        }
    }

    private static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("Send get error" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
