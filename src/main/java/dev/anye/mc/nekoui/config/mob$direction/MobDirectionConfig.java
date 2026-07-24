package dev.anye.mc.nekoui.config.mob$direction;

import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import dev.anye.core.color._ColorCDT;
import dev.anye.core.color._ColorSupport;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.Configs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class MobDirectionConfig extends _JsonConfig<MobDirectionData> {
	private static final Logger LOGGER = LogUtils.getLogger();
	private static final String FILE_PATH = _File.getFilePath(Configs.CONFIG_DIR, "mob-direction.json");
	public static final MobDirectionData DEFAULT = new MobDirectionData(
			true,
			true,
			40,
			11,
			22,
			9,
			2,
			-0.5,
			true,
			true,
			entityList(),
			"0xFF000000",
			false,
			0,
			entityColors());
	public static final MobDirectionConfig I = new MobDirectionConfig();

	public MobDirectionConfig() {
		super(FILE_PATH, DEFAULT, new TypeToken<>() {});
		writeColor();
	}

	public int getEntityColor(Entity entity) {
		if (entity != null) {
			ResourceLocation res = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
			return getEntityColor(res.toLanguageKey());
		}
		return _ColorCDT.white;
	}

	public int getEntityColor(String entity) {
		return map(mobDirectionData -> mobDirectionData.getColor(entity)).orElseThrow();
	}

	public boolean isShowPoi(Entity entity) {
		if (entity != null) {
			ResourceLocation res = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
			return isShowPoi(res.toLanguageKey());
		}
		return false;
	}

	public boolean isShowPoi(String entity) {
		return map(mobDirectionData -> mobDirectionData.isShow(entity)).orElse(false);
	}

	public static int getEntityColor(MobCategory mobCategory) {
		if (mobCategory == MobCategory.MONSTER) return _ColorCDT.red;
		if (mobCategory.isPersistent()) return _ColorCDT.blue;
		return mobCategory.isFriendly() ? _ColorCDT.green : _ColorCDT.yellow;
	}

	public void writeColor() {
		ifPresent(mobDirectionData -> {
			BuiltInRegistries.ENTITY_TYPE.forEach(entityType -> {
				String eid = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toLanguageKey();
				if (mobDirectionData.entityColors().get(eid) == null) {
					int color = getEntityColor(entityType.getCategory());
					if (color == -1) return;
					mobDirectionData.entityColors().put(eid, _ColorSupport.intToHexColor(color));
				}
			});
		});
		save();
	}

	private static Map<String,Boolean> entityList(){
		Map<String,Boolean> map = new HashMap<>();
		map.put("minecraft.chest_minecart",true);
		return map;
	}
	private static Map<String,String> entityColors(){
		Map<String,String> map = new HashMap<>();
		map.put("minecraft.player","0x56FFFFFF");
		map.put("minecraft.chest_minecart","0xFFFFFF00");
		return map;
	}
}