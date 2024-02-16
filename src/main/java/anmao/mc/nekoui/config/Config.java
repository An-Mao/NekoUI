package anmao.mc.nekoui.config;

import anmao.mc.nekoui.NekoUI;
import com.google.gson.Gson;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.*;

public class Config {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String configFile = NekoUI.ConfigDir +"config.json";

    public static ConfigData mobDirectionConfig;
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
                      "mobDirection": true,
                      "renderScreenElement": true,
                      "outputGuiId": false
                    }""");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    private static void load(){
        Gson gson = new Gson();
        try (Reader reader = new FileReader(configFile)) {
            mobDirectionConfig = gson.fromJson(reader, ConfigData.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
