package dev.anye.mc.nekoui.config.ban$screen;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import dev.anye.mc.nekoui.config.Configs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BanScreenConfig  {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String configFile = Configs.ConfigDir +"ban-screen.json";
    public static List<String> screens;
    static {
        init();
    }
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
                      "something"
                    ]""");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    private static void load(){
        Gson gson = new Gson();
        try (Reader reader = new FileReader(configFile)) {
            Type listType = new TypeToken<List<String>>(){}.getType();
            screens = gson.fromJson(reader, listType);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
    public static boolean isBan(String s){
        return screens != null && screens.contains(s);
    }
}
