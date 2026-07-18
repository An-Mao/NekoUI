package dev.anye.mc.nekoui.config.screen$element;

import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ScreenElementConfig extends _JsonConfig<Map<String , ScreenElementData>> {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String filePath = _File.getFilePath(Configs.ConfigDir ,"screen-elements.json");
    //public static final ScreenElementConfig I = new ScreenElementConfig();

    public ScreenElementConfig() {
        super(filePath, ScreenDefaultConfig.DefaultConfig, new TypeToken<>(){},false);
    }

    @Override
    public Map<String, ScreenElementData> getData() {
        if (this.data == null) return new HashMap<>();
        return super.getData();
    }
}
