package nws.mc.nekoui.config;

import nws.mc.nekoui.config.screen$element.ScreenElementConfig;
import nws.mc.nekoui.config.screen$element.ScreenElementDataElement;
import nws.mc.nekoui.config.screen$element.ScreenRenderConfig;
import nws.mc.nekoui.dat$type.ScreenRender;
import org.joml.Vector3i;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OldToNew {
    public static void ToNewScreenElementConfig() {
        File file = new File(ScreenElementConfig.filePath);
        if (file.exists()) {
            new ScreenElementConfig().getDatas().forEach((s, screenElementData) -> {
                List<ScreenRender.Element> elements = new ArrayList<>();
                screenElementData.getElements().forEach(screenElementDataElement -> {
                    Vector3i pos = new Vector3i(0);
                    int[] p = screenElementDataElement.getPos();
                    if (p.length == 3) pos.set(p[0], p[1], p[2]);
                    if (p.length == 2) pos.set(p[0], p[1], 0);
                    String key;
                    if (screenElementDataElement.getTypeEnum() == ScreenElementDataElement.Type.Image)
                        key = screenElementDataElement.getParameter().get("mod").getAsString() + ":" + screenElementDataElement.getParameter().get("path").getAsString();
                    else key = screenElementDataElement.getParameter().get("key").getAsString();

                    String color = "FFFFFF";
                    if (screenElementDataElement.getParameter().has("color"))
                        color = screenElementDataElement.getParameter().get("color").getAsString();
                    int width = 0;
                    if (screenElementDataElement.getParameter().has("width"))
                        width = screenElementDataElement.getParameter().get("width").getAsInt();
                    int height = 0;
                    if (screenElementDataElement.getParameter().has("height"))
                        height = screenElementDataElement.getParameter().get("height").getAsInt();
                    ScreenRender.Element element = new ScreenRender.Element(pos, screenElementDataElement.getType(), key, color, width, height);
                    elements.add(element);
                });
                Vector3i pos = new Vector3i(screenElementData.getPos()[0], screenElementData.getPos()[1], screenElementData.getPos()[2]);
                ScreenRender screenRender = new ScreenRender(screenElementData.getX(), screenElementData.getY(), pos, elements);
                ScreenRenderConfig screenRenderConfig = new ScreenRenderConfig(s + ".json");
                screenRenderConfig.setData(screenRender);
                screenRenderConfig.save();
            });
            int i = 0;
            String filePath = ScreenElementConfig.filePath + ".del";
            while (!file.renameTo(new File(filePath))){
                filePath = ScreenElementConfig.filePath+"."+ i++ + ".del";
            }
        }
    }
}
