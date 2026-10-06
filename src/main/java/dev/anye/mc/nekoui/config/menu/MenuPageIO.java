package dev.anye.mc.nekoui.config.menu;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfigS;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.dat_type.MenuPageData;

public class MenuPageIO extends _JsonConfigS<MenuPageData> {
	public MenuPageIO(String filePath) {
		super(_File.getFilePath(Configs.CONFIG_DIR_MENU_PAGE, filePath), MenuPageData.EMPTY, new TypeToken<>() {
		},false);
	}
}
