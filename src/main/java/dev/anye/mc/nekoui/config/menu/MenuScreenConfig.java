package dev.anye.mc.nekoui.config.menu;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;

public class MenuScreenConfig extends _JsonConfig<MenuScreenData> {
    public static final String file = _File.getFilePath(Configs.ConfigDir,"menu-config.json");
    public static final MenuScreenConfig INSTANCE = new MenuScreenConfig();
    public MenuScreenConfig() {
        super(file, """
                {
                  "sectors": 9,
                  "innerRadius": 20,
                  "outerRadius": 80,
                  "SelectColor": "50ffffff",
                  "UsualColor": "70000000"
                }""", new TypeToken<>(){});
    }

    @Override
    public MenuScreenData getData() {
        if (data== null) return new MenuScreenData();
        return super.getData();
    }
}
