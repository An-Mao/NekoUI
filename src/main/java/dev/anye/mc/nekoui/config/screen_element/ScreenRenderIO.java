package dev.anye.mc.nekoui.config.screen_element;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.dat_type.ScreenRender;

public class ScreenRenderIO extends _JsonConfig<ScreenRender> {
	public ScreenRenderIO(String filePath, String defaultData) {
		super(_File.getFilePath(Configs.ConfigDir_ScreenElement, filePath), defaultData, new TypeToken<>() {
		}, false);
	}

	public ScreenRenderIO(String filePath) {
		this(filePath, "");
	}


}