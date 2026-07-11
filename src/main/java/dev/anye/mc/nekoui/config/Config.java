package dev.anye.mc.nekoui.config;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;

public class Config extends _JsonConfig<ConfigData> {
	private static final String configFile = _File.getFilePath(Configs.ConfigDir, "config.json");

	public static final Config INSTANCE = new Config();

	public Config() {
		super(configFile, """
				{
				  "putDefault": true,
				  "renderScreenElement": true,
				  "outputGuiId": false,
				  "outputScreenPathName": false,
				  "menu":true,
				  "autoPage":false
				}""", new TypeToken<>() {
		});
	}

	@Override
	public ConfigData getData() {
		if (data == null) return new ConfigData();
		return super.getData();
	}




}
