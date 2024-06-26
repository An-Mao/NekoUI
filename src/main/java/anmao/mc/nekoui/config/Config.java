package anmao.mc.nekoui.config;

import anmao.mc.amlib.json.JsonConfig;
import anmao.mc.nekoui.NekoUI;
import com.google.gson.reflect.TypeToken;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(Dist.CLIENT)
public class Config extends JsonConfig<ConfigData> {
    private static final String configFile = NekoUI.ConfigDir +"config.json";

    public static final Config INSTANCE = new Config();

    public Config() {
        super(configFile, """
                {
                  "renderScreenElement": true,
                  "outputGuiId": false,
                  "menu":true
                }""", new TypeToken<>(){});
    }
}
