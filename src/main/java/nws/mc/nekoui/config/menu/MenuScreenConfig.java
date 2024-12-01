package nws.mc.nekoui.config.menu;

import com.google.gson.reflect.TypeToken;
import nws.dev.core.json._JsonConfig;
import nws.mc.nekoui.config.Configs;

public class MenuScreenConfig extends _JsonConfig<MenuScreenData> {
    public static final String file = Configs.ConfigDir+"menu-config.json";
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
    public MenuScreenData getDatas() {
        if (datas== null) return new MenuScreenData();
        return super.getDatas();
    }
}
