package dev.anye.mc.nekoui.config.hide$hud;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.mc.nekoui.config.Configs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;

@OnlyIn(Dist.CLIENT)
public class HideHudConfig extends _JsonConfig<ArrayList<String>> {
    private static final String filePath = Configs.ConfigDir +"hide-gui.json";
    public static final HideHudConfig I = new HideHudConfig();
    private final ArrayList<ResourceLocation> list = new ArrayList<>();
    public HideHudConfig() {
        super(filePath, """
                    [
                      "minecraft:experience_level",
                      "minecraft:experience_bar",
                      "-minecraft:hotbar",
                      "minecraft:player_health",
                      "minecraft:food_level",
                      "minecraft:armor_level",
                      "minecraft:air_level"
                    ]""", new TypeToken<>(){} , false);
        toRes();
    }

    @Override
    public ArrayList<String> getDatas() {
        if (datas == null) new ArrayList<>();
        return super.getDatas();
    }

    private void toRes() {
        list.clear();
        getDatas().forEach((s) -> list.add(ResourceLocation.parse(s)));
    }
    public boolean isHide(ResourceLocation id) {
        return list.contains(id);
    }
}
