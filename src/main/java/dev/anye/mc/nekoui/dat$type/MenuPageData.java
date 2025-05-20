package dev.anye.mc.nekoui.dat$type;

import java.util.List;

public record MenuPageData(String title, int projectNumber, int innerRadius, int outerRadius,
                           List<ProjectInfo> projects) {

    public record ProjectInfo(String key, String textNormalColor, String textHighlightColor, String backgroundNormalColor, String backgroundHighlightColor) {
        public static final ProjectInfo EMPTY = new ProjectInfo("-empty-");
        public ProjectInfo(String key) {
            this(key,"auto","auto");
        }
        public ProjectInfo(String key, String textNormalColor, String textHighlightColor) {
            this(key, textNormalColor,textHighlightColor,"auto","auto");
        }
    }
}
