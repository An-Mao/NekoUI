package anmao.mc.nekoui.lib.dat;

import net.minecraft.resources.ResourceLocation;
import org.w3c.dom.Element;

public class CustomDataTypes_InfoConfig_Icon {
    private ResourceLocation icon;
    private int w;
    private int h;

    public void setDat(Element element) {
        icon = new ResourceLocation(element.getElementsByTagName("mod").item(0).getTextContent(),
                element.getElementsByTagName("path").item(0).getTextContent());
        w = Integer.parseInt(element.getElementsByTagName("width").item(0).getTextContent());
        h = Integer.parseInt(element.getElementsByTagName("height").item(0).getTextContent());

    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }
}
