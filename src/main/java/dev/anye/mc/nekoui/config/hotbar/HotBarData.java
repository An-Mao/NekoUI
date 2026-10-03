package dev.anye.mc.nekoui.config.hotbar;

public record HotBarData(
		boolean enable,
		boolean dynamicDisplay,
		String startX,
		String startY,
		int x,
		int y,
		int space,
		String direction) {
	public static final HotBarData DEFAULT = new HotBarData(
			false,
			false,
			"center",
			"bottom",
			7,
			-24,
			17,
			"horizontal"
	);

}
