package dev.anye.mc.nekoui.config.health_bar;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;

public class HealthBarConfig extends _JsonConfig<HealthBarData> {
	public static final String FILE = _File.getFilePath(Configs.CONFIG_DIR, "health-bar.json");
	public static final HealthBarConfig I = new HealthBarConfig();

	public HealthBarConfig() {
		super(FILE, HealthBarData.DEFAULT, new TypeToken<>() {});
	}

}
