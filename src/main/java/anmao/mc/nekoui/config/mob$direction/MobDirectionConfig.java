package anmao.mc.nekoui.config.mob$direction;

import anmao.dev.core.color.ColorHelper;
import anmao.dev.core.color._ColorCDT;
import anmao.dev.core.json.JsonConfig;
import anmao.mc.nekoui.config.Configs;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

@OnlyIn(Dist.CLIENT)
public class MobDirectionConfig extends JsonConfig<MobDirectionData> {
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
    public int getEntityColor(Entity entity){
        if (entity != null){
            ResourceLocation res = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
            if (res != null){
                return getEntityColor(res.toLanguageKey());
            }
        }
        return _ColorCDT.white;
    }

    public int getEntityColor(String entity){
        return getDatas().getColor(entity);
    }
    public boolean isShowPoi(Entity entity){
        if (entity != null){
            ResourceLocation res = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
            if (res != null){
                return isShowPoi(res.toLanguageKey());
            }
        }
        return false;
    }
    public boolean isShowPoi(String entity){
        return getDatas().isShow(entity);
    }

    public void writeEggColor(){
        ForgeRegistries.ENTITY_TYPES.forEach((entityType) -> {
            String eid = ForgeRegistries.ENTITY_TYPES.getKey(entityType).toLanguageKey();
            if (getDatas().getEntityColors().get(eid) == null){
                int color = getSpawnEggColors(entityType)[getDatas().getEggLayerIndex()];
                if (color == -1) return;
                getDatas().getEntityColors().put(eid, ColorHelper.intToHexColor(color));
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
