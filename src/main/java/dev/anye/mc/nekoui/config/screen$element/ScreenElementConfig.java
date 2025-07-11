package dev.anye.mc.nekoui.config.screen$element;

import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import dev.anye.core.json._JsonConfig;
import dev.anye.mc.nekoui.config.Configs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ScreenElementConfig extends _JsonConfig<Map<String , ScreenElementData>> {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String filePath = Configs.ConfigDir +"screen-elements.json";
    public static final ScreenElementConfig I = new ScreenElementConfig();

    public ScreenElementConfig() {
        super(filePath, ScreenDefaultConfig.DefaultConfig, new TypeToken<>(){});
    }
}
