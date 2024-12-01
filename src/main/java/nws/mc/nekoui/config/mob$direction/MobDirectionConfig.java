package nws.mc.nekoui.config.mob$direction;

import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nws.dev.core.color._ColorCDT;
import nws.dev.core.color._ColorSupport;
import nws.dev.core.json._JsonConfig;
import nws.mc.nekoui.config.Configs;
import org.slf4j.Logger;

@OnlyIn(Dist.CLIENT)
public class MobDirectionConfig extends _JsonConfig<MobDirectionData> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String filePath = Configs.ConfigDir +"mob-direction.json";
    public static final MobDirectionConfig I = new MobDirectionConfig();

    public MobDirectionConfig() {
        super(filePath, """
                {
                                      "enable": true,
                                      "dynamicDisplay":true,
                                      "poiShowRadius":80,
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
                                      "useEggColor": true,
                                      "eggLayerIndex": 0,
                                      "entityColors":{
                                          "minecraft.player": "0x56FFFFFF",
                                          "minecraft.chest_minecart": "0xFFFFFF00"
                                      }
                                    }""", new TypeToken<>(){});

        if (getDatas().isUseEggColor()) writeEggColor();
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
    public static ItemStack getSpawnEgg(EntityType<?> entity) {
        for (SpawnEggItem eggItem : SpawnEggItem.eggs()) {
            if (eggItem.getType(ItemStack.EMPTY) == entity) {
                return new ItemStack(eggItem);
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
            int baseColor = spawnEggItem.getColor(0);
            int overlayColor = spawnEggItem.getColor(1);
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
