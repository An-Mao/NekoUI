package anmao.mc.nekoui.lib.player;

import anmao.mc.nekoui.constant._MC;
import anmao.mc.nekoui.lib.AM;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameInfo {
    public static Player clientPlayer = _MC.MC.player;
    private static final Map<String, String> ATTRIBUTE_METHODS = new HashMap<>();
    static {
        ATTRIBUTE_METHODS.put("xp", "experienceProgress");
        ATTRIBUTE_METHODS.put("lvl", "experienceLevel");
        ATTRIBUTE_METHODS.put("health", "getHealth");
        ATTRIBUTE_METHODS.put("maxHealth", "getMaxHealth");
        ATTRIBUTE_METHODS.put("hunger", "getFoodLevel");
        ATTRIBUTE_METHODS.put("atk", "getAttackDamage");
        ATTRIBUTE_METHODS.put("atkWithItem", "getTotalAttackDamage");
        ATTRIBUTE_METHODS.put("luck", "getLuck");
        ATTRIBUTE_METHODS.put("speed", "getSpeed");
    }
    public String getPlayerAttribute(String attributeName) {
        String methodName = ATTRIBUTE_METHODS.get(attributeName);
        if (methodName != null) {
            try {
                Method method = Player.class.getMethod(methodName);
                Object result = method.invoke(clientPlayer);
                return String.valueOf(result);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return "Attribute not found";
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
    public static String getPlayerDat(String s){
        if (clientPlayer != null){
            if (Objects.equals(s, "xp")){
                return String.valueOf(clientPlayer.experienceProgress);
            }else if (Objects.equals(s, "lvl")){
                return String.valueOf(clientPlayer.experienceLevel);
            }else if (Objects.equals(s, "health")){
                return String.valueOf(clientPlayer.getHealth());
            }else if (Objects.equals(s, "maxHealth")){
                return String.valueOf(clientPlayer.getMaxHealth());
            }else if (Objects.equals(s, "hunger")){
                return String.valueOf(clientPlayer.getFoodData().getFoodLevel());
            }else if (Objects.equals(s, "atk")){
                return String.valueOf(clientPlayer.getAttributes().getValue(Attributes.ATTACK_DAMAGE));
            }else if (Objects.equals(s, "atkWithItem")){
                Collection<AttributeModifier> att = clientPlayer.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
                double baseDamage = clientPlayer.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
                double itemDamage = AM.getAdddamage(att);
                return String.valueOf(baseDamage + itemDamage);
            }else if (Objects.equals(s, "luck")){
                return String.valueOf(clientPlayer.getLuck());
            }else if (Objects.equals(s, "speed")){
                return String.valueOf(clientPlayer.getSpeed());
            }
        }
        return "error";
    }
}
