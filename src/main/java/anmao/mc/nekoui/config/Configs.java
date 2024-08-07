package anmao.mc.nekoui.config;

import anmao.dev.core.system._File;
import anmao.mc.nekoui.config.hide$hud.HideGuiConfig;
import anmao.mc.nekoui.config.hotbar.HotBarConfig;
import anmao.mc.nekoui.config.mob$direction.MobDirectionConfig;
import anmao.mc.nekoui.config.screen$element.ScreenElementConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Configs {
    public static final String ConfigDir = _File.getFileFullPathWithRun("config/NekoUI/");
    public static final String ConfigDir_JS = ConfigDir +"JavaScript/";
    static {
        _File.checkAndCreateDir(ConfigDir);
        _File.checkAndCreateDir(ConfigDir_JS);
        HideGuiConfig.init();
    }
}
