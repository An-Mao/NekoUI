package anmao.mc.nekoui.config.mob$direction;

import anmao.dev.core.json.JsonConfig;
import anmao.mc.nekoui.config.Configs;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

@OnlyIn(Dist.CLIENT)
public class MobDirectionConfig extends JsonConfig<MobDirectionData> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String filePath = Configs.ConfigDir +"mob-direction.json";
    public static final MobDirectionConfig I = new MobDirectionConfig();

    public MobDirectionConfig() {
        super(filePath, """
                {
                                      "enable": true,
                                      "dynamicDisplay":true,
                                      "poiShowRadius":80,
                                      "poiRadius":22,
                                      "poiSize":11,
                                      "poiMaxSize": 9,
                                      "poiMinSize": 2,
                                      "ratio":-0.5,
                                      "entityColors":{
                                          "slime": "0xFF0000"
                                      }
                                    }""", new TypeToken<>(){});
    }
}
