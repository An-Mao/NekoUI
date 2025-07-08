package dev.anye.mc.nekoui.config;

public class ConfigData {
    private boolean renderScreenElement;
    private boolean outputGuiId;
    private boolean outputScreenPathName;
    private boolean menu;
    private boolean autoPage;

    public void setMenu(boolean menu) {
        this.menu = menu;
    }

    public boolean isMenu() {
        return menu;
    }

    public void setRenderScreenElement(boolean renderScreenElement) {
        this.renderScreenElement = renderScreenElement;
    }

    public boolean isRenderScreenElement() {
        return renderScreenElement;
    }

    public void setOutputGuiId(boolean outputGuiId) {
        this.outputGuiId = outputGuiId;
    }

    public boolean isOutputGuiId() {
        return outputGuiId;
    }

    public void setOutputScreenPathName(boolean outputScreenPathName) {
        this.outputScreenPathName = outputScreenPathName;
    }

    public boolean isOutputScreenPathName() {
        return outputScreenPathName;
    }
    public void setAutoPage(boolean autoPage) {
        this.autoPage = autoPage;
    }

    public boolean isAutoPage() {
        return autoPage;
    }
}
