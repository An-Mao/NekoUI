package anmao.mc.nekoui.lib.dat;

import org.w3c.dom.Element;

public class CustomDataTypes_InfoConfig_Text {
    private String tipText;
    private int color;
    public void setDat(Element element ){
        tipText = element.getElementsByTagName("con").item(0).getTextContent();
        color = Integer.parseInt(element.getElementsByTagName("color").item(0).getTextContent(), 16);
    }

    public String getTipText() {
        return tipText;
    }

    public int getColor() {
        return color;
    }
}
