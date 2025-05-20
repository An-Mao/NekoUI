package dev.anye.mc.nekoui.config;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Config extends _JsonConfig<ConfigData> {
    private static final String configFile = Configs.ConfigDir +"config.json";

    public static final Config INSTANCE = new Config();

    public Config() {
        super(configFile, """
                {
                  "putDefault": true,
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
