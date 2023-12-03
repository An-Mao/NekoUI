package anmao.mc.nekoui.lib.player;

import anmao.mc.nekoui.constant._MC;
import anmao.mc.nekoui.lib.AM;
import anmao.mc.nekoui.lib.FormattedData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class PlayerInfo {
    public static Player clientPlayer = _MC.MC.player;
    public static String getPlayerDat(int type, String[] str){
        if (clientPlayer != null) {
            int l = str.length;
            //int type = Integer.parseInt(str[0]);
            String dat = clientPlayer.serializeNBT().toString();
            //System.out.println(dat);
            try {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(dat, JsonObject.class);
                if (type == 1) {
                    if (l == 1) {
                        //System.out.println(bs[1] + " : " + value);
                        return FormattedData.numberToString(jsonObject.get(str[0]).getAsString());
                    }
                }
                if (type == 2) {
                    if (l == 4) {
                        JsonArray array = jsonObject.getAsJsonArray(str[0]);
                        for (JsonElement element : array) {
                            JsonObject object = element.getAsJsonObject();
                            if (str[2].equals(object.get(str[1]).getAsString())) {
                                String value = object.get(str[3]).getAsString();
                                //System.out.println(bs[4] + " : " + value);
                                return FormattedData.numberToString(value);
                            }
                        }
                    }
                }
                if (type == 3) {
                    if (l == 3) {
                        JsonArray array = jsonObject.getAsJsonArray(str[0]);
                        int index = Integer.parseInt(str[1]);
                        if (index >= 0 && index < array.size()) {
                            JsonObject object = array.get(index).getAsJsonObject();
                            String value = object.get(str[2]).getAsString();
                            //System.out.println("Base value at index " + index + ": " + value);
                            return FormattedData.numberToString(value);
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return "Invalid index";
    }
    public static String getPlayerDat(String s){
        if (clientPlayer == null) {
            return "clientPlayer is null";
        }
        switch (s){
            default: return "";
            case "xp": return FormattedData.numberToString(clientPlayer.experienceProgress);
            case "lvl": return FormattedData.numberToString(clientPlayer.experienceLevel);
            case "health": return FormattedData.numberToString(clientPlayer.getHealth());
            case "maxHealth": return FormattedData.numberToString(clientPlayer.getMaxHealth());
            case "hunger": return FormattedData.numberToString(clientPlayer.getFoodData().getFoodLevel());
            case "atk": return FormattedData.numberToString(getAttackDamage());
            case "atkWithItem": return FormattedData.numberToString(getTotalAttackDamage());
            case "luck": return FormattedData.numberToString(clientPlayer.getLuck());
            case "speed": return FormattedData.numberToString(clientPlayer.getSpeed());
            case "posX": return FormattedData.numberToString(clientPlayer.getX());
            case "posY": return FormattedData.numberToString(clientPlayer.getY());
            case "posZ": return FormattedData.numberToString(clientPlayer.getZ());
            case "dayTime": return FormattedData.numberToString(clientPlayer.level().getDayTime());
            case "gameTime": return FormattedData.numberToString(clientPlayer.level().getGameTime());
        }
    }
    private static Number getPlayerAttribute(String string) {
        return switch (string) {
            case "xp" -> clientPlayer.experienceProgress;
            case "lvl" -> clientPlayer.experienceLevel;
            case "health" -> clientPlayer.getHealth();
            case "maxHealth" -> clientPlayer.getMaxHealth();
            case "hunger" -> clientPlayer.getFoodData().getFoodLevel();
            case "atk" -> getAttackDamage();
            case "atkWithItem" -> getTotalAttackDamage();
            case "luck" -> clientPlayer.getLuck();
            case "speed" -> clientPlayer.getSpeed();
            case "posX" -> clientPlayer.getX();
            case "posY" -> clientPlayer.getY();
            case "posZ" -> clientPlayer.getZ();
            default -> 0;
        };
    }
    private static double getAttackDamage() {
        return clientPlayer.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
    }

    private static double getTotalAttackDamage() {
        Collection<AttributeModifier> att = clientPlayer.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
        double baseDamage = getAttackDamage();
        double itemDamage = AM.getAdddamage(att);
        return baseDamage + itemDamage;
    }
}
