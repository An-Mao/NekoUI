package dev.anye.mc.nekoui.config.hotbar;

import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;
import org.slf4j.Logger;

public class HotBarConfig extends _JsonConfig<HotBarData> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String filePath = _File.getFilePath(Configs.ConfigDir ,"hotbar.json");
    public static final HotBarConfig INSTANCE = new HotBarConfig();
    public HotBarConfig() {
        super(filePath, """
                    {
                      "enable": false,
                      "dynamicDisplay": false,
                      "startX": "center",
                      "startY": "bottom",
                      "x": 7,
                      "y": -24,
                      "space": 17,
                      "direction": "horizontal"
                    }""", new TypeToken<>(){});
    }

    @Override
    public HotBarData getData() {
        if (data == null) return new HotBarData();
        return super.getData();
    }
}
