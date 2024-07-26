package anmao.mc.nekoui.config;

import anmao.mc.amlib.system._File;
import anmao.mc.nekoui.config.hide$hud.HideGuiConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

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
