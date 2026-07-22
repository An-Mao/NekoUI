package dev.anye.mc.nekoui.config.menu;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;

public class MenuScreenConfig extends _JsonConfig<MenuScreenData> {
	public static final String FILE = _File.getFilePath(Configs.CONFIG_DIR, "menu-config.json");
	public static final MenuScreenConfig INSTANCE = new MenuScreenConfig();

	public MenuScreenConfig() {
		super(FILE, MenuScreenData.DEFAULT, new TypeToken<>() {});
	}

}
