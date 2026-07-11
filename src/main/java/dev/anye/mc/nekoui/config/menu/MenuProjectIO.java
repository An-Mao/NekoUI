package dev.anye.mc.nekoui.config.menu;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.dat_type.MenuProjectData;

public class MenuProjectIO extends _JsonConfig<MenuProjectData> {
	public MenuProjectIO(String filePath) {
		super(_File.getFilePath(Configs.ConfigDir_MenuProject, filePath), "", new TypeToken<>() {
		}, false);
	}
}
