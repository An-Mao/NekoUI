package dev.anye.mc.nekoui.config.hide_hud;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public class HideHudConfig extends _JsonConfig<List<String>> {
	private static final String FILE_PATH = _File.getFilePath(Configs.CONFIG_DIR, "hide-gui.json");
	public static final HideHudConfig I = new HideHudConfig();
	private final List<Identifier> list = new ArrayList<>();

	public HideHudConfig() {
		super(FILE_PATH, List.of(
				"minecraft:experience_level",
				"minecraft:experience_bar",
				"-minecraft:hotbar",
				"minecraft:player_health",
				"minecraft:food_level",
				"minecraft:armor_level",
				"minecraft:air_level"), new TypeToken<>() {
		}, false);
		toRes();
	}

	private void toRes() {
		list.clear();
		ifPresent(strings -> strings.forEach(s -> list.add(Identifier.parse(s))));
	}

	public boolean isHide(Identifier id) {
		return list.contains(id);
	}
}
