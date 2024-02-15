package anmao.mc.nekoui;

import anmao.mc.amlib.system._File;
import anmao.mc.nekoui.config.CC;
import anmao.mc.nekoui.config.ScreenConfig;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.io.File;


@Mod(NekoUI.MOD_ID)
public class NekoUI
{
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "nekoui";
    public static final String ConfigDir = _File.getFilePath("config/NekoUI/");
    public NekoUI()
    {
        init();
        ScreenConfig.init();
        CC._start();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void init(){
        File folder = new File(ConfigDir);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (created) {
                LOGGER.info("文件夹创建成功");
            } else {
                LOGGER.error("文件夹创建失败");
            }
        }
    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }
}
