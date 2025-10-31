package dev.anye.mc.nekoui.config.menu;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.mc.nekoui.config.Configs;

import java.util.List;

public class MenuPageConfig extends _JsonConfig<List<String>> {
    public static final String File = Configs.ConfigDir_Menu + "PageIndex.json";

    public static final MenuPageConfig I = new MenuPageConfig();

    public MenuPageConfig() {
        super(File, """
                [
                    "page1",
                    "page2",
                    "page3"
                ]
                """, new TypeToken<>(){},false);
    }
}
