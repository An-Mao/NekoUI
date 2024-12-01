package nws.mc.nekoui.config.health$bar;

import com.google.gson.reflect.TypeToken;
import nws.dev.core.json._JsonConfig;
import nws.mc.nekoui.config.Configs;

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

    @Override
    public HealthBarData getDatas() {
        if (datas== null) return new HealthBarData();
        return super.getDatas();
    }
}
