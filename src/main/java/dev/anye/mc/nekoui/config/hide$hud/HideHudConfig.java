package dev.anye.mc.nekoui.config.hide$hud;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;

public class HideHudConfig extends _JsonConfig<ArrayList<String>> {
    private static final String filePath = _File.getFilePath(Configs.ConfigDir ,"hide-gui.json");
    public static final HideHudConfig I = new HideHudConfig();
    private final ArrayList<Identifier> list = new ArrayList<>();
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
    public ArrayList<String> getData() {
        if (data == null) new ArrayList<>();
        return super.getData();
    }

    private void toRes() {
        list.clear();
        getData().forEach((s) -> list.add(Identifier.parse(s)));
    }
    public boolean isHide(Identifier id) {
        return list.contains(id);
    }
}
