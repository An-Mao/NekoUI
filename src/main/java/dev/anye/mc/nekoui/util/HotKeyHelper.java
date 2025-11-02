package dev.anye.mc.nekoui.util;

import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.ClientHooks;
import org.slf4j.Logger;

import java.util.HashMap;

public class HotKeyHelper {
    public static final HotKeyHelper I = new HotKeyHelper();
    protected final Logger LOGGER = LogUtils.getLogger();
    public final HashMap<String, KeyMapping> keys;
    public HotKeyHelper(){
        keys = new HashMap<>();
    }
    public boolean add(String k, KeyMapping v){
        if (keys.containsKey(k)) return false;
        LOGGER.debug("add {} => {}",k,v.getKey());
        keys.put(k,v);
        return true;
    }
    public boolean check(String k){
        if (keys.containsKey(k)) {
            KeyMapping keyMapping = keys.get(k);
            keyMapping.setDown(true);
            ClientHooks.onKeyInput(keyMapping.getKey().getValue(),0,0,0);
            keyMapping.setDown(false);
            return true;
        }
        return false;
    }
}
