package nws.mc.nekoui.config.ban$screen;

import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nws.dev.core.json._JsonConfig;
import nws.mc.nekoui.config.Configs;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BanScreenConfig extends _JsonConfig<List<String>> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String configFile = Configs.ConfigDir +"ban-screen.json";
    public static final BanScreenConfig I = new BanScreenConfig();
    public BanScreenConfig() {
        super(configFile, """
                    [
                      "something"
                    ]""", new TypeToken<>(){});
    }

    @Override
    public List<String> getDatas() {
        if (datas == null)  return new ArrayList<>();
        return super.getDatas();
    }

    public boolean isBan(String s){
        return getDatas() != null && getDatas().contains(s);
    }
}
