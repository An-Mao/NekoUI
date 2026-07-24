package dev.anye.mc.nekoui.config.hotbar;

import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;
import org.slf4j.Logger;

public class HotBarConfig extends _JsonConfig<HotBarData> {
	private static final Logger LOGGER = LogUtils.getLogger();
	private static final String FILE_PATH = _File.getFilePath(Configs.CONFIG_DIR, "hotbar.json");
	public static final HotBarConfig INSTANCE = new HotBarConfig();

	public HotBarConfig() {
		super(FILE_PATH, HotBarData.DEFAULT, new TypeToken<>() {});
	}
}
