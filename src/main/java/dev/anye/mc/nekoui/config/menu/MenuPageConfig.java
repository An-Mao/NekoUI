package dev.anye.mc.nekoui.config.menu;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;

import java.util.List;

public class MenuPageConfig extends _JsonConfig<List<String>> {
	public static final String FILE = _File.getFilePath(Configs.CONFIG_DIR_MENU, "PageIndex.json");

	public static final MenuPageConfig I = new MenuPageConfig();

	public MenuPageConfig() {
		super(FILE, List.of(
				"page1",
				"page2",
				"page3"), new TypeToken<>() {}, false);
	}
}
