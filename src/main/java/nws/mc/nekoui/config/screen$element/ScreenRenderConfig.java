package nws.mc.nekoui.config.screen$element;

import com.google.gson.reflect.TypeToken;
import nws.dev.core.json._JsonConfig;
import nws.mc.nekoui.config.Configs;
import nws.mc.nekoui.dat$type.ScreenRender;

public class ScreenRenderConfig extends _JsonConfig<ScreenRender> {
    public ScreenRenderConfig(String filePath, String defaultData) {
        super(Configs.ConfigDir_ScreenElement + filePath, defaultData, new TypeToken<>(){},false);
    }
    public ScreenRenderConfig(String filePath) {
        this(filePath,"");
    }
    public void setData(ScreenRender data) {
        this.datas = data;
    }

}