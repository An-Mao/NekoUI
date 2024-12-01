package nws.mc.nekoui.config;

import com.google.gson.reflect.TypeToken;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nws.dev.core.json._JsonConfig;

@OnlyIn(Dist.CLIENT)
public class Config extends _JsonConfig<ConfigData> {
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

    @Override
    public ConfigData getDatas() {
        if (datas== null) return new ConfigData();
        return super.getDatas();
    }
}
