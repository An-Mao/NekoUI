package dev.anye.mc.nekoui.config.menu;

public record MenuScreenData(
		int sectors,
		int innerRadius,
		int outerRadius,
		String selectColor,
		String usualColor) {
	public static final MenuScreenData DEFAULT = new MenuScreenData(
			9,
			20,
			80,
			"50ffffff",
			"70000000");
}