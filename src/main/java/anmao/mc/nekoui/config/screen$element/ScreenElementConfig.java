package anmao.mc.nekoui.config.screen$element;

import anmao.dev.easy_json.JsonConfig;
import anmao.mc.nekoui.config.Configs;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ScreenElementConfig extends JsonConfig<Map<String , ScreenElementData>> {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String filePath = Configs.ConfigDir +"screen-elements.json";
    public static final ScreenElementConfig I = new ScreenElementConfig();

    public ScreenElementConfig() {
        super(filePath, ScreenDefaultConfig.DefaultConfig, new TypeToken<>(){});
    }
}
