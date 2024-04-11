package anmao.mc.nekoui.config;

import anmao.mc.amlib.system._File;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Configs {
    public static final String ConfigDir = _File.getFileFullPathWithRun("config/NekoUI/");
    public static final String ConfigDir_JS = ConfigDir +"JavaScript/";
    static {
        _File.checkAndCreateDir(ConfigDir);
        _File.checkAndCreateDir(ConfigDir_JS);
    }
}
