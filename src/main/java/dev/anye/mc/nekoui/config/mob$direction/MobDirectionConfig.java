package dev.anye.mc.nekoui.config.mob$direction;

import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import dev.anye.core.color._ColorCDT;
import dev.anye.core.color._ColorSupport;
import dev.anye.core.json._JsonConfig;
import dev.anye.mc.nekoui.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import org.slf4j.Logger;

public class MobDirectionConfig extends _JsonConfig<MobDirectionData> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String filePath = Configs.ConfigDir +"mob-direction.json";
    public static final MobDirectionConfig I = new MobDirectionConfig();

    public MobDirectionConfig() {
        super(filePath, """
                {
                                      "enable": true,
                                      "dynamicDisplay":true,
                                      "poiShowRadius":40,
                                      "poiRadius":22,
                                      "poiSize":11,
                                      "poiMaxSize": 9,
                                      "poiMinSize": 2,
                                      "ratio":-0.5,
                                      "onlyLivingEntity":true,
                                      "notInListMode":true,
                                      "entityList":{
                                          "minecraft.chest_minecart": true
                                      },
                                      "defaultColor":"0xFF000000",
                                      "useEggColor": false,
                                      "eggLayerIndex": 0,
                                      "entityColors":{
                                          "minecraft.player": "0x56FFFFFF",
                                          "minecraft.chest_minecart": "0xFFFFFF00"
                                      }
                                    }""", new TypeToken<>(){});

        //if (getDatas().isUseEggColor()) writeEggColor();
        writeColor();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public MobDirectionData getDatas() {
        if (datas== null) return new MobDirectionData();
        return datas;
    }

    public int getEntityColor(Entity entity){
        if (entity != null){
            ResourceLocation res = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
            return getEntityColor(res.toLanguageKey());
        }
        return _ColorCDT.white;
    }

    public int getEntityColor(String entity){
        return getDatas().getColor(entity);
    }
    public boolean isShowPoi(Entity entity){
        if (entity != null){
            ResourceLocation res = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
            return isShowPoi(res.toLanguageKey());
        }
        return false;
    }
    public boolean isShowPoi(String entity){
        return getDatas().isShow(entity);
    }

    public static int getEntityColor(MobCategory mobCategory) {
        return mobCategory == MobCategory.MONSTER ? _ColorCDT.red : mobCategory.isPersistent() ? _ColorCDT.blue : mobCategory.isFriendly() ? _ColorCDT.green : _ColorCDT.yellow;
    }
    public void writeColor(){
        BuiltInRegistries.ENTITY_TYPE.forEach((entityType) -> {
            String eid = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toLanguageKey();
            if (getDatas().getEntityColors().get(eid) == null){
                int color = getEntityColor(entityType.getCategory());
                //int color = getSpawnEggColors(entityType)[getDatas().getEggLayerIndex()];
                if (color == -1) return;
                getDatas().getEntityColors().put(eid, _ColorSupport.intToHexColor(color));
            }
        });
        save();
    }
    /*
    public void writeEggColor(){
        BuiltInRegistries.ENTITY_TYPE.forEach((entityType) -> {
            String eid = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toLanguageKey();
            if (getDatas().getEntityColors().get(eid) == null){
                int color = getSpawnEggColors(entityType)[getDatas().getEggLayerIndex()];
                if (color == -1) return;
                getDatas().getEntityColors().put(eid, _ColorSupport.intToHexColor(color));
            }
        });
        save();
    }

     */
    public static ItemStack getSpawnEgg(EntityType<?> entity) {
        if (Minecraft.getInstance().level instanceof ClientLevel clientLevel) {
            for (SpawnEggItem eggItem : SpawnEggItem.eggs()) {
                if (eggItem.getType(ItemStack.EMPTY) == entity) {
                    return new ItemStack(eggItem);
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getSpawnEgg(Entity entity) {
        EntityType<?> entityType = entity.getType();
        for (SpawnEggItem eggItem : SpawnEggItem.eggs()) {
            if (eggItem.getType(ItemStack.EMPTY) == entityType) {
                return new ItemStack(eggItem);
            }
        }
        return ItemStack.EMPTY;
    }
    public static int[] getSpawnEggColors(ItemStack spawnEgg) {
        if (spawnEgg.getItem() instanceof SpawnEggItem spawnEggItem) {
            int baseColor = -1; //spawnEggItem.getColor(0);
            int overlayColor = -1; //spawnEggItem.getColor(1);
            return new int[]{baseColor, overlayColor};
        }
        return new int[]{-1, -1};
    }
    public static int[] getSpawnEggColors(Entity spawnEgg) {
        return getSpawnEggColors(getSpawnEgg(spawnEgg));
    }
    public static int[] getSpawnEggColors(EntityType<?> spawnEgg) {
        return getSpawnEggColors(getSpawnEgg(spawnEgg));
    }
}