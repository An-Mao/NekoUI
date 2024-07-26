package anmao.mc.nekoui.config;

import anmao.dev.core.json.JsonConfig;
import com.google.gson.reflect.TypeToken;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Config extends JsonConfig<ConfigData> {
    private static final String configFile = Configs.ConfigDir +"config.json";

    public static final Config INSTANCE = new Config();

    public Config() {
        super(configFile, """
                {
                  "renderScreenElement": true,
                  "outputGuiId": false,
                  "outputScreenPathName": false,
                  "menu":true,
                  "autoPage":false
                }""", new TypeToken<>(){});
    }
}
