package anmao.mc.nekoui;

import anmao.mc.amlib.system._File;
import anmao.mc.nekoui.config.Config;
import anmao.mc.nekoui.config.hide$hud.HideGuiConfig;
import anmao.mc.nekoui.config.hotbar.HotBarConfig;
import anmao.mc.nekoui.config.mob$direction.MobDirectionConfig;
import anmao.mc.nekoui.config.screen$element.ScreenElementConfig;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;


@Mod(NekoUI.MOD_ID)
public class NekoUI
{
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "nekoui";
    public static final String ConfigDir = _File.getFileFullPathWithRun("config/NekoUI/");
    public NekoUI()
    {
        init();
        //IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //modEventBus.addListener(this::commonSetup);
        //MinecraftForge.EVENT_BUS.register(this);
    }
    private void init() {
        if (_File.checkAndCreateDir(ConfigDir)) {
            LOGGER.info("create dir success");
        } else {
            LOGGER.error("create dir failed");
        }
        Config.init();
        ScreenElementConfig.init();
        HideGuiConfig.init();
        MobDirectionConfig.init();
        HotBarConfig.init();
    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }
}
