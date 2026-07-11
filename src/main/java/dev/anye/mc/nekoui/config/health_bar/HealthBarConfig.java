package dev.anye.mc.nekoui.config.health_bar;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;

public class HealthBarConfig extends _JsonConfig<HealthBarData> {
	public static final String file = _File.getFilePath(Configs.ConfigDir, "health-bar.json");
	public static final HealthBarConfig I = new HealthBarConfig();

	public HealthBarConfig() {
		super(file, """
				{
				  "enable": true,
				  "renderDistance":30,
				  "renderTop":0.5,
				  "renderHealthBar": true,
				  "renderHealthBarText": true,
				  "healthBarTextColor": "0xFFFFD700",
				  "renderEffect": true,
				  "effectRenderImage": true,
				  "effectImageRotationAngle":10000,
				  "renderOnlyView": true
				}""", new TypeToken<>() {
		});
	}

	@Override
	public HealthBarData getData() {
		if (data == null) return HealthBarData.DEFAULT;
		return super.getData();
	}
}
