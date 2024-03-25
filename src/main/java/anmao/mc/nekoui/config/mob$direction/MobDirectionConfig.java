package anmao.mc.nekoui.config.mob$direction;

import anmao.mc.nekoui.NekoUI;
import com.google.gson.Gson;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.io.*;

@OnlyIn(Dist.CLIENT)
public class MobDirectionConfig {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String configFile = NekoUI.ConfigDir +"mob-direction.json";

    public static MobDirectionData mobDirectionConfig;
    public static void init(){
        File file = new File(configFile);
        if (!file.exists()){
            reset();
        }
        load();
    }
    private static void reset(){
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write("""
                    {
                      "enable": true,
                      "dynamicDisplay":true,
                      "poiShowRadius":80,
                      "poiRadius":22,
                      "poiSize":11,
                      "poiMaxSize": 9,
                      "poiMinSize": 2,
                      "ratio":-0.5
                    }""");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    private static void load(){
        Gson gson = new Gson();
        try (Reader reader = new FileReader(configFile)) {
            mobDirectionConfig = gson.fromJson(reader, MobDirectionData.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
