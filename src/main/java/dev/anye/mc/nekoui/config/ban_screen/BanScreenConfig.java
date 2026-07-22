package dev.anye.mc.nekoui.config.ban_screen;

import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;
import org.slf4j.Logger;

import java.util.List;

public class BanScreenConfig extends _JsonConfig<List<String>> {
	private static final Logger LOGGER = LogUtils.getLogger();
	private static final String FILE_PATH = _File.getFilePath(Configs.CONFIG_DIR, "ban-screen.json");
	public static final BanScreenConfig I = new BanScreenConfig();

	public BanScreenConfig() {
		super(FILE_PATH, List.of(), new TypeToken<>() {});
	}


	public boolean isBan(String s) {
		return map(strings -> strings.contains(s)).orElse(false);
	}
}
