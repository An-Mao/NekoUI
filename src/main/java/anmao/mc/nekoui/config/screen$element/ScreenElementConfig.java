package anmao.mc.nekoui.config.screen$element;

import anmao.mc.nekoui.NekoUI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ScreenElementConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String configFile = NekoUI.ConfigDir +"screen-elements.json";
    public static Map<String , ScreenElementData> screenElements = new HashMap<>();
    public static void init(){
        File file = new File(configFile);
        if (!file.exists()){
            reset();
        }
        load();
    }
    private static void reset(){
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(ScreenDefaultConfig.DefaultConfig);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    private static void load(){
        Gson gson = new Gson();
        try (Reader reader = new FileReader(configFile)) {
            screenElements = gson.fromJson(reader, new TypeToken<Map<String, ScreenElementData>>() {}.getType());
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}
