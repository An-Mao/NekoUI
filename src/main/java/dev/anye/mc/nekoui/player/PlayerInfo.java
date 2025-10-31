package dev.anye.mc.nekoui.player;

import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import dev.anye.core.format._FormatToString;
import dev.anye.mc.cores.helper.attribute.AttributeHelper;
import dev.anye.mc.cores.helper.item.ItemHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

import java.util.Collection;

public class PlayerInfo {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static String getPlayerDat(String s){
        return _FormatToString.formatValue(getPlayerData(s),2);
    }

    public static JsonObject getPlayerNbtDat(LocalPlayer player){
        if (player != null) {
            String dat = player.getPersistentData().toString();
            try {
                Gson gson = new Gson();
                return gson.fromJson(dat, JsonObject.class);
            }catch(Exception e){
                LOGGER.error(e.getMessage());
            }
        }
        return null;
    }

    private static Number getPlayerData(String string) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null){
            return 0;
        }
        return switch (string) {
            case "fps" -> Minecraft.getInstance().getFps();
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
            case "serverPing", "serverPlayersOnline" -> getServerInfo(string);
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
            if (attributeInstance.getAttribute().getRegisteredName().equals(s)){
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
        if (s.equals("serverPlayers")){
            if (server.players != null) {
                return server.players.online();
            }
        }
        return 0;
    }


    private static double getAttackDamage(LocalPlayer player) {
        return player.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
    }
    private static double getTotalAttackDamage(LocalPlayer player) {
        ItemStack mainHandItem = player.getMainHandItem();
        double itemDamage = 0.0;
        if (!mainHandItem.isEmpty()){
            Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers = ItemHelper.getAttributeModifiers(mainHandItem, EquipmentSlot.MAINHAND);
            Collection<AttributeModifier> att = attributeModifiers.get(Attributes.ATTACK_DAMAGE);
            itemDamage = AttributeHelper.getAttributeModifierValue(att);// AM.getAdddamage(att);
        }
        double baseDamage = getAttackDamage(player);
        return baseDamage + itemDamage;
    }
}
