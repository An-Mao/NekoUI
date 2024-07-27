package anmao.mc.nekoui.config.health$bar;

import anmao.dev.core.json.JsonConfig;
import anmao.mc.nekoui.config.Configs;
import com.google.gson.reflect.TypeToken;

public class HealthBarConfig extends JsonConfig<HealthBarData> {
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
                  "effectImageRotationAngle": 10000,
                  "renderOnlyView": true
                }""", new TypeToken<>(){});
    }
}
