package dev.anye.mc.nekoui.dat_type;

import java.util.List;

public record MenuPageData(String title, int projectNumber, int innerRadius, int outerRadius,
                           List<ProjectInfo> projects) {
	public static final MenuPageData EMPTY = new MenuPageData("",0,0,0,List.of());


	public record ProjectInfo(String key, String textNormalColor, String textHighlightColor,
	                          String backgroundNormalColor, String backgroundHighlightColor) {
		public static final ProjectInfo EMPTY = new ProjectInfo("-empty-");

		public ProjectInfo(String key) {
			this(key, "auto", "auto");
		}

		public ProjectInfo(String key, String textNormalColor, String textHighlightColor) {
			this(key, textNormalColor, textHighlightColor, "auto", "auto");
		}
	}
}
