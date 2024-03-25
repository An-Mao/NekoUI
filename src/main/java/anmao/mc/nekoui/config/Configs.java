package anmao.mc.nekoui.config;

import anmao.mc.amlib.system._File;

public class Configs {
    public static final String ConfigDir = _File.getFileFullPathWithRun("config/NekoUI/");
    static {
        _File.checkAndCreateDir(ConfigDir);
    }
}
