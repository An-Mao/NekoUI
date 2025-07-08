package dev.anye.mc.nekoui.config.page;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class PageData {
    public String title;
    public int projectNumber;
    public int innerRadius;
    public int outerRadius;
    public Map<String,ProjectData> projects;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setProjectNumber(int projectNumber) {
        this.projectNumber = projectNumber;
    }

    public int getProjectNumber() {
        return projectNumber;
    }

    public int getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(int innerRadius) {
        this.innerRadius = innerRadius;
    }

    public int getOuterRadius() {
        return outerRadius;
    }

    public void setOuterRadius(int outerRadius) {
        this.outerRadius = outerRadius;
    }

    public void setProjects(Map<String, ProjectData> projects) {
        this.projects = projects;
    }

    public ProjectData getProject(String key) {
        return projects.get(key);
    }

    public Map<String, ProjectData> getProjects() {
        return projects;
    }

    public record ProjectData(String key, String textNormalColor, String textHighlightColor, String backgroundNormalColor, String backgroundHighlightColor) {
        public ProjectData(String key) {
            this(key,"auto","auto");
        }
        public ProjectData(String key, String textNormalColor,String textHighlightColor) {
            this(key, textNormalColor,textHighlightColor,"auto","auto");
        }
        public ProjectData(String key, String textNormalColor,String textHighlightColor, String backgroundNormalColor, String backgroundHighlightColor) {
            this.key = key;
            this.textNormalColor = textNormalColor;
            this.textHighlightColor = textHighlightColor;
            this.backgroundNormalColor = backgroundNormalColor;
            this.backgroundHighlightColor = backgroundHighlightColor;
        }

        private int toInt(String s) {
            if (s == null) {
                return 0x00000000;
            }
            if (s.startsWith("0x")) {
                s = s.substring(2);
            }
            return (int) Long.parseLong(s, 16);
        }
    }
}
