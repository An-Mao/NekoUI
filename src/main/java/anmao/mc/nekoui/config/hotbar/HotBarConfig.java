package anmao.mc.nekoui.config.hotbar;

import anmao.mc.nekoui.NekoUI;
import com.google.gson.Gson;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.io.*;

@OnlyIn(Dist.CLIENT)
public class HotBarConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String configFile = NekoUI.ConfigDir +"hotbar.json";

    public static HotBarData hotBarData;
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
                      "dynamicDisplay": false,
                      "startX": "center",
                      "startY": "bottom",
                      "x": 7,
                      "y": -24,
                      "space": 17,
                      "direction": "horizontal"
                    }""");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    private static void load(){
        Gson gson = new Gson();
        try (Reader reader = new FileReader(configFile)) {
            hotBarData = gson.fromJson(reader, HotBarData.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
