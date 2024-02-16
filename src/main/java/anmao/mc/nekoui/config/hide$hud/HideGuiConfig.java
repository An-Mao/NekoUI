package anmao.mc.nekoui.config.hide$hud;

import anmao.mc.nekoui.NekoUI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class HideGuiConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String configFile = NekoUI.ConfigDir +"hide-gui.json";
    public static List<String> guiList;
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
                    [
                      "minecraft:experience_bar",
                      "minecraft:hotbar",
                      "minecraft:player_health",
                      "minecraft:food_level"
                    ]""");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    private static void load(){
        Gson gson = new Gson();
        try (Reader reader = new FileReader(configFile)) {
            Type listType = new TypeToken<List<String>>(){}.getType();
            guiList = gson.fromJson(reader, listType);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
