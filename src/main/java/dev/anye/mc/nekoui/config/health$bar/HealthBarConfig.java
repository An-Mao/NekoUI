package dev.anye.mc.nekoui.config.health$bar;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.mc.nekoui.config.Configs;

public class HealthBarConfig extends _JsonConfig<HealthBarData> {
    public static final String file = Configs.ConfigDir+"health-bar.json";
    public static final HealthBarConfig I = new HealthBarConfig();
    public HealthBarConfig() {
        super(file, """
                {
                  "enable": true,
                  "renderDistance":30,
                  "renderTop":0.5,
                  "renderHealthBar": true,
                  "renderHealthBarText": true,
                  "renderEffect": true,
                  "effectRenderImage": true,
                  "effectImageRotationAngle":10000,
                  "renderOnlyView": true
                }""", new TypeToken<>(){});
    }
}
