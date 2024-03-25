package anmao.mc.nekoui.lib.player;

import anmao.mc.amlib.attribute.AttributeHelper;
import anmao.mc.amlib.format._FormatToString;
import anmao.mc.nekoui.lib.FormattedData;
import anmao.mc.nekoui.lib.dat.CustomDataTypes_InfoConfig_Key_AK;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class PlayerInfo {
    public static Player clientPlayer = Minecraft.getInstance().player;
    public static String getPlayerNbtDat(CustomDataTypes_InfoConfig_Key_AK[] caks){
        if (clientPlayer != null) {
            String dat = clientPlayer.serializeNBT().toString();
            try {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(dat, JsonObject.class);
                JsonElement jsonElement = null;
                for (CustomDataTypes_InfoConfig_Key_AK cak : caks) {
                    if (cak.getType() == 0) {
                        if (jsonElement == null) {
                            jsonElement = jsonObject.get(cak.getKey());
                        } else {
                            if (jsonElement.isJsonObject()) {
                                JsonObject object = jsonElement.getAsJsonObject();
                                jsonElement = object.get(cak.getKey());
                            }
                        }
                    } else if (cak.getType() == 1) {
                        if (jsonElement != null) {
                            if (jsonElement.isJsonArray()) {
                                JsonArray array = jsonElement.getAsJsonArray();
                                for (JsonElement element : array) {
                                    JsonObject o = element.getAsJsonObject();
                                    if (cak.getNeedValue().equals(o.get(cak.getNeedKey()).getAsString())) {
                                        jsonElement = o.get(cak.getKey());
                                    }
                                }
                            }
                        }
                    }
                }
                if (jsonElement != null) {
                    return FormattedData.numberToString(jsonElement.getAsString());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return "Invalid index";
    }
    public static String getPlayerNbtDat(String str){
        if (clientPlayer != null) {
            String dat = clientPlayer.serializeNBT().toString();
            try {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(dat, JsonObject.class);
                return FormattedData.numberToString(jsonObject.get(str).getAsString());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return "error";
    }
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
                        return jsonObject.get(str[0]).getAsString();
                    }
                }
                if (type == 2) {

                    if (l == 4) {
                        JsonArray array = jsonObject.getAsJsonArray(str[0]);
                        for (JsonElement element : array) {
                            JsonObject object = element.getAsJsonObject();
                            if (str[2].equals(object.get(str[1]).getAsString())) {
                                //System.out.println(bs[4] + " : " + value);
                                return object.get(str[3]).getAsString();
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
                            //System.out.println("Base value at index " + index + ": " + value);
                            return object.get(str[2]).getAsString();
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
        return _FormatToString.numberToString(getPlayerData(s));
    }
    private static Number getPlayerData(String string) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null){
            return 0;
        }
        return switch (string) {
            case "xp" -> player.experienceProgress;
            case "lvl" -> player.experienceLevel;
            case "health" -> player.getHealth();
            case "maxHealth" -> player.getMaxHealth();
            case "hunger" -> player.getFoodData().getFoodLevel();
            case "atk" -> getAttackDamage(player);
            case "atkWithItem" -> getTotalAttackDamage(player);
            case "luck" -> player.getLuck();
            case "speed" -> player.getSpeed();
            case "posX" -> player.getX();
            case "posY" -> player.getY();
            case "posZ" -> player.getZ();
            case "dayTime" -> player.level().getDayTime();
            case "gameTime" -> player.level().getGameTime();
            case "airSupply" -> player.getAirSupply();
            case "armor" -> player.getArmorValue();
            case "armorToughness" -> player.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
            case "attackSpeed" -> player.getAttributeValue(Attributes.ATTACK_SPEED);
            case "attackKnockBack" -> player.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
            case "serverPing" -> getServerInfo(string);
            default -> 0;
        };
    }
    public static double getPlayerAttribute(String s){
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null){
            return 0;
        }
        double[] v = {0};
        player.getAttributes().getSyncableAttributes().forEach(attributeInstance -> {
            if (attributeInstance.getAttribute().getDescriptionId().equals(s)){
                v[0] = attributeInstance.getValue();
            }
        });
        return v[0];
    }
    private static double getServerInfo(String s){
        ServerData server = Minecraft.getInstance().getCurrentServer();
        if (server == null){
            return 0;
        }
        if (s.equals("serverPing")){
            return server.ping;
        }
        return 0;
    }
    private static double getAttackDamage(LocalPlayer player) {
        return player.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
    }
    private static double getTotalAttackDamage(LocalPlayer player) {
        Collection<AttributeModifier> att = player.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
        double baseDamage = getAttackDamage(player);
        double itemDamage = AttributeHelper.getAttributeModifierValue(att);// AM.getAdddamage(att);
        return baseDamage + itemDamage;
    }
}
