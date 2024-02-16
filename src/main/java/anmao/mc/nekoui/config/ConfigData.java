package anmao.mc.nekoui.config;

public class ConfigData {
    private boolean renderScreenElement;
    private boolean outputGuiId;

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
}
