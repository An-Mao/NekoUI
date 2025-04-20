package nws.mc.nekoui.config;

import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nws.dev.core.pack._Pack;
import nws.dev.core.system._File;
import nws.mc.nekoui.config.ban$screen.BanScreenConfig;
import nws.mc.nekoui.config.health$bar.HealthBarConfig;
import nws.mc.nekoui.config.hide$hud.HideHudConfig;
import nws.mc.nekoui.config.hotbar.HotBarConfig;
import nws.mc.nekoui.config.menu.MenuConfig;
import nws.mc.nekoui.config.menu.MenuScreenConfig;
import nws.mc.nekoui.config.mob$direction.MobDirectionConfig;
import nws.mc.nekoui.config.page.PageConfig;
import nws.mc.nekoui.config.screen$element.ScreenRenderConfig;
import nws.mc.nekoui.dat$type.ScreenRender;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class Configs {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String ConfigDir = _File.getFileFullPathWithRun("config/NekoUI/");
    public static final String ConfigDir_JS = ConfigDir +"JavaScript/";
    public static final String ConfigDir_ScreenElement = ConfigDir +"ScreenElement/";
    private static boolean isInit = true;

    public static final List<ScreenRender> ScreenRenders = new ArrayList<>();
    static {
        _File.checkAndCreateDir(ConfigDir);
        _File.checkAndCreateDir(ConfigDir_JS);
        _File.checkAndCreateDir(ConfigDir_ScreenElement);
    }

    public static void init() {
        if (isInit) {
            isInit = false;
            OldToNew.ToNewScreenElementConfig();
        }

        Config.INSTANCE.init();
        if (Config.INSTANCE.getDatas().isPutDefault()){
            _Pack.writeFiles("assets/nekoui/config/screen/",ConfigDir_ScreenElement,".json","air","armor","armor_toughness","damage","exp","fps","health","hunger","luck","speed");
            Config.INSTANCE.getDatas().setPutDefault(false);
            Config.INSTANCE.save();
        }
        BanScreenConfig.I.init();
        HealthBarConfig.I.init();
        HideHudConfig.I.init();
        HotBarConfig.INSTANCE.init();
        MenuScreenConfig.INSTANCE.init();
        MenuConfig.INSTANCE.init();
        MobDirectionConfig.I.init();
        PageConfig.INSTANCE.init();
        LoadScreenRender();
    }
    public static void LoadScreenRender() {
        _File.getFiles(ConfigDir_ScreenElement,".json").forEach(path -> {
            ScreenRender screenRender = new ScreenRenderConfig(path.getFileName().toString()).getDatas();
            if (screenRender != null) ScreenRenders.add(screenRender);
        });
    }

}
