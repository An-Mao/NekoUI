package dev.anye.mc.nekoui.config.health_bar;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfigR;
import dev.anye.core.json._JsonConfigS;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;

public class HealthBarConfig extends _JsonConfigR<HealthBarData> {
	public static final String FILE = _File.getFilePath(Configs.CONFIG_DIR, "health-bar.json");
	public static final HealthBarConfig I = new HealthBarConfig();

	public HealthBarConfig() {
		super(FILE, HealthBarData.DEFAULT, new TypeToken<>() {});
	}

}
