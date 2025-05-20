package dev.anye.mc.nekoui.config.menu;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.dat$type.MenuProjectData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MenuProjectIO extends _JsonConfig<MenuProjectData> {
    public MenuProjectIO(String filePath) {
        super(Configs.ConfigDir_MenuProject + filePath, "", new TypeToken<>(){},false);
    }
    public MenuProjectIO setData(MenuProjectData data) {
        this.datas = data;
        return this;
    }
}
