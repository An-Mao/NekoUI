package dev.anye.mc.nekoui.config.mob_direction;

import dev.anye.core.color._ColorSupport;

import java.util.Map;

public record MobDirectionData(
		boolean enable,
		boolean dynamicDisplay,
		int poiShowRadius,
		int poiSize,
		int poiRadius,
		int poiMaxSize,
		int poiMinSize,
		double ratio,
		boolean onlyLivingEntity,
		boolean notInListMode,
		Map<String, Boolean> entityList,
		String defaultColor,
		boolean useEggColor,
		int eggLayerIndex,
		Map<String, String> entityColors) {


	public int getEggLayerIndex() {
		return Math.clamp(eggLayerIndex, 0, 1);
	}

	public int getColor(String name) {
		return _ColorSupport.HexToColor(entityColors.getOrDefault(name, defaultColor));
	}

	public boolean isShow(String name) {
		return entityList.getOrDefault(name, false);
	}


	@Override
	public String toString() {
		return "MobDirectionData{" +
				"enable=" + enable +
				", dynamicDisplay=" + dynamicDisplay +
				", poiShowRadius=" + poiShowRadius +
				", poiSize=" + poiSize +
				", poiRadius=" + poiRadius +
				", poiMaxSize=" + poiMaxSize +
				", poiMinSize=" + poiMinSize +
				", ratio=" + ratio +
				", onlyLivingEntity=" + onlyLivingEntity +
				", notInListMode=" + notInListMode +
				", entityList=" + entityList +
				", defaultColor='" + defaultColor + '\'' +
				", useEggColor=" + useEggColor +
				", eggLayerIndex=" + eggLayerIndex +
				", entityColors=" + entityColors +
				'}';
	}
}
