package anmao.mc.nekoui.lib;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public class Debug {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static void debug(String s){
        LOGGER.debug("§b" + s);
    }
    public static void error(String s){
        LOGGER.error("§4" + s);
    }
    public static void warn(String s){
        LOGGER.warn("§e" + s);
    }
}
