package dev.anye.mc.nekoui.config.screen$element;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.dat$type.ScreenRender;

public class ScreenRenderIO extends _JsonConfig<ScreenRender> {
    public ScreenRenderIO(String filePath, String defaultData) {
        super(Configs.ConfigDir_ScreenElement + filePath, defaultData, new TypeToken<>(){},false);
    }
    public ScreenRenderIO(String filePath) {
        this(filePath,"");
    }
    public void setData(ScreenRender data) {
        this.datas = data;
    }

}