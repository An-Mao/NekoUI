package anmao.mc.nekoui.config.page;

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

    public static class ProjectData {
        public String key;
        public String textNormalColor;
        public String textHighlightColor;
        public String backgroundNormalColor;
        public String backgroundHighlightColor;
        public ProjectData() {

        }
        public ProjectData(String key) {
            this(key,"0xff00ffff","0xff00ff00");
        }
        public ProjectData(String key, String textNormalColor,String textHighlightColor) {
            this(key, textNormalColor,textHighlightColor,"0x70000000","0x50ffffff");
        }
        public ProjectData(String key, String textNormalColor,String textHighlightColor, String backgroundNormalColor, String backgroundHighlightColor) {
            this.key = key;
            this.textNormalColor = textNormalColor;
            this.textHighlightColor = textHighlightColor;
            this.backgroundNormalColor = backgroundNormalColor;
            this.backgroundHighlightColor = backgroundHighlightColor;
        }
        public String getKey() {
            return key;
        }

        public int getTextNormalColor() {
            return toInt(textNormalColor);
        }
        public int getTextHighlightColor() {
            return toInt(textHighlightColor);
        }
        public int getBackgroundNormalColor() {
            return toInt(backgroundNormalColor);
        }
        public int getBackgroundHighlightColor() {
            return toInt(backgroundHighlightColor);
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
