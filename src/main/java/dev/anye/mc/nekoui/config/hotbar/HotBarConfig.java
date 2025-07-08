package dev.anye.mc.nekoui.config.hotbar;

import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import dev.anye.core.json._JsonConfig;
import dev.anye.mc.nekoui.config.Configs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

@OnlyIn(Dist.CLIENT)
public class HotBarConfig extends _JsonConfig<HotBarData> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String filePath = Configs.ConfigDir +"hotbar.json";
    public static final HotBarConfig INSTANCE = new HotBarConfig();
    public HotBarConfig() {
        super(filePath, """
                    {
                      "enable": true,
                      "dynamicDisplay": false,
                      "startX": "center",
                      "startY": "bottom",
                      "x": 7,
                      "y": -24,
                      "space": 17,
                      "direction": "horizontal"
                    }""", new TypeToken<>(){});
    }
}
