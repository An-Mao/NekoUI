package anmao.mc.nekoui.config.menu;

import anmao.mc.amlib.json.JsonConfig;
import anmao.mc.nekoui.config.Configs;
import com.google.gson.reflect.TypeToken;

public class MenuScreenConfig extends JsonConfig<MenuScreenData> {
    public static final String file = Configs.ConfigDir+"menu.json";
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
}
