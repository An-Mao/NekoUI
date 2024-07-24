package anmao.mc.nekoui.config.screen$element;

import com.google.gson.JsonObject;

public class ScreenElementDataElement {
    private int[] pos;
    private int type;
    private JsonObject parameter;

    public int[] getPos() {
        return pos;
    }

    public void setPos(int[] pos) {
        this.pos = pos;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public JsonObject getParameter() {
        return parameter;
    }

    public void setParameter(JsonObject parameter) {
        this.parameter = parameter;
    }

}
