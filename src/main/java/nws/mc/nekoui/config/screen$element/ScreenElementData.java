package nws.mc.nekoui.config.screen$element;

import java.util.List;

public class ScreenElementData {
    private String x;
    private String y;
    private int[] pos;
    private List<ScreenElementDataElement> elements;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public int[] getPos() {
        return pos;
    }

    public void setPos(int[] pos) {
        this.pos = pos;
    }

    public List<ScreenElementDataElement> getElements() {
        return elements;
    }

    public void setElements(List<ScreenElementDataElement> elements) {
        this.elements = elements;
    }

}

