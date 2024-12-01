package nws.mc.nekoui.config.screen$element;

import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nws.dev.core.json._JsonConfig;
import nws.mc.nekoui.config.Configs;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ScreenElementConfig extends _JsonConfig<Map<String , ScreenElementData>> {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String filePath = Configs.ConfigDir +"screen-elements.json";
    public static final ScreenElementConfig I = new ScreenElementConfig();

    public ScreenElementConfig() {
        super(filePath, ScreenDefaultConfig.DefaultConfig, new TypeToken<>(){},false);
    }

    @Override
    public Map<String, ScreenElementData> getDatas() {
        if (this.datas == null) return new HashMap<>();
        return super.getDatas();
    }
}
