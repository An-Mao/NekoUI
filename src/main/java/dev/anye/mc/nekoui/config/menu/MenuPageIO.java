package dev.anye.mc.nekoui.config.menu;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.dat$type.MenuPageData;

public class MenuPageIO extends _JsonConfig<MenuPageData> {
    public MenuPageIO(String filePath) {
        super(Configs.ConfigDir_MenuPage + filePath, "", new TypeToken<>(){});
    }

    public void setData(MenuPageData menuPageData) {
        this.datas = menuPageData;
    }
}
