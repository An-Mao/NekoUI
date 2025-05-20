package dev.anye.mc.nekoui.config;

import dev.anye.core.pack._Pack;
import dev.anye.mc.nekoui.config.menu.MenuConfig;
import dev.anye.mc.nekoui.config.menu.MenuPageConfig;
import dev.anye.mc.nekoui.config.menu.MenuPageIO;
import dev.anye.mc.nekoui.config.menu.MenuProjectIO;
import dev.anye.mc.nekoui.config.page.PageConfig;
import dev.anye.mc.nekoui.config.screen$element.ScreenElementConfig;
import dev.anye.mc.nekoui.config.screen$element.ScreenElementDataElement;
import dev.anye.mc.nekoui.config.screen$element.ScreenRenderIO;
import dev.anye.mc.nekoui.dat$type.MenuPageData;
import dev.anye.mc.nekoui.dat$type.MenuProjectData;
import dev.anye.mc.nekoui.dat$type.ScreenRender;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3i;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
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
                ScreenRenderIO screenRenderIO = new ScreenRenderIO(s + ".json");
                screenRenderIO.setData(screenRender);
                screenRenderIO.save();
            });
            int i = 0;
            String filePath = ScreenElementConfig.filePath + ".del";
            while (!file.renameTo(new File(filePath))){
                filePath = ScreenElementConfig.filePath+"."+ i++ + ".del";
            }
        }else if (Config.INSTANCE.getDatas().isPutDefault()) _Pack.writeFiles("assets/nekoui/config/screen/",Configs.ConfigDir_ScreenElement,".json","air","armor","armor_toughness","damage","exp","fps","health","hunger","luck","speed");
    }
    public static void ToNewMenuProjectConfig() {
        File file = new File(MenuConfig.file);
        if (file.exists()) {
            new MenuConfig().getDatas().forEach((s, menuData) -> {
                MenuProjectIO menuProjectIO = new MenuProjectIO(s + ".json");
                menuProjectIO.setData(new MenuProjectData(menuData.getName(), menuData.getType(), menuData.getValue()));
                menuProjectIO.save();
            });
            int i = 0;
            String filePath = MenuConfig.file + ".del";
            while (!file.renameTo(new File(filePath))){
                filePath = MenuConfig.file+"."+ i++ + ".del";
            }
        }else if (Config.INSTANCE.getDatas().isPutDefault()) _Pack.writeFiles("assets/nekoui/config/menu/project/",Configs.ConfigDir_MenuProject,".json","SwitchItemSlot0","SwitchItemSlot1","SwitchItemSlot2","SwitchItemSlot3","SwitchItemSlot4","SwitchItemSlot5","SwitchItemSlot6","SwitchItemSlot7","SwitchItemSlot8","TestCommand","TestCommand1","TestMessage");
    }

    public static void ToNewMenuPageConfig() {
        File file = new File(PageConfig.file);
        if (file.exists()) {
            MenuPageConfig.I.getDatas().clear();
            new PageConfig().getDatas().forEach((s, pageData) -> {
                MenuPageConfig.I.getDatas().add(s);
                List<MenuPageData.ProjectInfo> projectInfos = new ArrayList<>();
                pageData.projects.forEach((s1, projectData) -> projectInfos.add(new MenuPageData.ProjectInfo(projectData.key(),projectData.textNormalColor(), projectData.textHighlightColor(), projectData.backgroundNormalColor(), projectData.backgroundHighlightColor())));

                MenuPageIO menuPageIO = new MenuPageIO(s + ".json");
                menuPageIO.setData(new MenuPageData(pageData.getTitle(), pageData.getProjectNumber(), pageData.getInnerRadius(),pageData.getOuterRadius(),projectInfos));
                menuPageIO.save();
            });
            MenuPageConfig.I.save();
            int i = 0;
            String filePath = PageConfig.file + ".del";
            while (!file.renameTo(new File(filePath))){
                filePath = PageConfig.file+"."+ i++ + ".del";
            }
        }else if (Config.INSTANCE.getDatas().isPutDefault()) _Pack.writeFiles("assets/nekoui/config/menu/page/",Configs.ConfigDir_MenuPage,".json","page1","page2","page3");
    }
}
