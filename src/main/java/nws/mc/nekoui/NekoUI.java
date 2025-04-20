package nws.mc.nekoui;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import nws.mc.nekoui.config.Configs;

@Mod(NekoUI.MOD_ID)
public class NekoUI {
    public static final String MOD_ID = "nekoui";
    static {
        Configs.init();
    }
    public NekoUI(IEventBus modEventBus, ModContainer modContainer) {

    }
}
