package anmao.mc.nekoui.lib.player;

import anmao.mc.nekoui.constant._MC;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.entity.player.Player;

public class PlayerInfo {
    public static Player clientPlayer = _MC.MC.player;
    public static String getDat(String[] str){
        if (clientPlayer != null) {
            int l = str.length;
            int type = Integer.parseInt(str[0]);
            String dat = clientPlayer.serializeNBT().toString();
            //System.out.println(dat);
            try {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(dat, JsonObject.class);
                if (type == 1) {
                    if (l == 2) {
                        //System.out.println(bs[1] + " : " + value);
                        return jsonObject.get(str[1]).getAsString();
                    }
                }
                if (type == 2) {
                    if (l == 5) {
                        JsonArray array = jsonObject.getAsJsonArray(str[1]);
                        for (JsonElement element : array) {
                            JsonObject object = element.getAsJsonObject();
                            if (str[3].equals(object.get(str[2]).getAsString())) {
                                String value = object.get(str[4]).getAsString();
                                //System.out.println(bs[4] + " : " + value);
                                return value;
                            }
                        }
                    }
                }
                if (type == 3) {
                    if (l == 4) {
                        JsonArray array = jsonObject.getAsJsonArray(str[1]);
                        int index = Integer.parseInt(str[2]);
                        if (index >= 0 && index < array.size()) {
                            JsonObject object = array.get(index).getAsJsonObject();
                            String value = object.get(str[3]).getAsString();
                            //System.out.println("Base value at index " + index + ": " + value);
                            return value;
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return "Invalid index";
    }

    public static String[] breakStr(String s){
        return s.split("#");
    }
}
