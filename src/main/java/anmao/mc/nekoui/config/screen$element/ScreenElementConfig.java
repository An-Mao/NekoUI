package anmao.mc.nekoui.config.screen$element;

import anmao.dev.core.json.JsonConfig;
import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.config.Configs;
import anmao.mc.nekoui.gui.ScreenElementGui;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.io.*;
import java.util.HashMap;
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
