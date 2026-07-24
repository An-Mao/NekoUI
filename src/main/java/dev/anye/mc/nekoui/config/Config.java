package dev.anye.mc.nekoui.config;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;

public class Config extends _JsonConfig<ConfigData> {
	private static final String CONFIG_FILE = _File.getFilePath(Configs.CONFIG_DIR, "config.json");

	public static final Config INSTANCE = new Config();

	public Config() {
		super(CONFIG_FILE, ConfigData.DEFAULT, new TypeToken<>() {});
	}





}
