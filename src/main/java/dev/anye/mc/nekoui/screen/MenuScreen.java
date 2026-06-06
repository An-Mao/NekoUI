package dev.anye.mc.nekoui.screen;

import com.mojang.logging.LogUtils;
import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
import dev.anye.mc.nekoui.config.Config;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.config.menu.MenuScreenConfig;
import dev.anye.mc.nekoui.register.menu_page.MenuPageRegister;
import dev.anye.mc.nekoui.register.menu_project.MenuProject;
import dev.anye.mc.nekoui.register.menu_project.MenuProjectRegister;
import dev.anye.mc.nekoui.screen.widget.CircularMenu;
import dev.anye.mc.nekoui.screen.widget.CircularNewMenu;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MenuScreen extends Screen {
    protected static final Logger LOGGER = LogUtils.getLogger();
    private CircularMenu circularMenu;
    private CircularNewMenu circularNewMenu;
    public MenuScreen() {
        super(Component.translatable("screen.nekoui.menu.title"));
    }
    @Override
    protected void init() {
        super.init();
        addMenu();
    }
    private void addMenu(){
        if (Config.INSTANCE.getDatas().isAutoPage()) {
            List<DT_ListBoxData> data = new ArrayList<>();

            Configs.MenuProjects.forEach((s, menuData) -> data.add(new DT_ListBoxData(Component.literal(menuData.name()), s, this::run)));
			MenuProjectRegister.REGISTRY.entrySet().forEach(resourceKeyMenuProjectEntry -> data.add(new DT_ListBoxData(Component.literal(resourceKeyMenuProjectEntry.getValue().name()),resourceKeyMenuProjectEntry.getKey().identifier().toString(),this::run)));

            circularMenu = new CircularMenu(width / 2, height / 2, width, height, MenuScreenConfig.INSTANCE.getDatas().sectors, MenuScreenConfig.INSTANCE.getDatas().innerRadius, MenuScreenConfig.INSTANCE.getDatas().outerRadius, Component.empty(), data);
            circularMenu.setFlipMode(CircularMenu.FlipMode.button);

            addRenderableWidget(circularMenu);
        }else {
            List<CircularNewMenu.RenderPageData> pd = new ArrayList<>();
            Configs.MenuPage.forEach((pageData) -> pd.add(CircularNewMenu.RenderPageData.creat(pageData,this::run)));
			MenuPageRegister.REGISTRY.entrySet().forEach(pageEntry -> pd.add(CircularNewMenu.RenderPageData.creat(pageEntry.getValue().getData(),this::run)));
			
			circularNewMenu = new CircularNewMenu(width / 2, height / 2, width, height, Component.empty(),pd);
            circularNewMenu.setFlipMode(CircularNewMenu.FlipMode.button);
            addRenderableWidget(circularNewMenu);

        }
    }
    public void run(Object v){
        if (v instanceof String s){
            MenuProject menuData = Configs.MenuProjects.get(s);
            if (menuData != null){
				menuData.onClick();
            }else{
				MenuProjectRegister.REGISTRY.get(Identifier.tryParse(s)).ifPresent(holder -> holder.value().onClick());
			}
        }
    }
    @Override
    public void onClose() {
        if (Config.INSTANCE.getDatas().isAutoPage()) {
            DT_ListBoxData dtListBoxData = circularMenu.getData();
            if (dtListBoxData != null) {
                dtListBoxData.OnPress(dtListBoxData.getValue());
            }
        }else {
            DT_ListBoxData dtListBoxData = circularNewMenu.getData();
            if (dtListBoxData != null) {
                dtListBoxData.OnPress(dtListBoxData.getValue());
            }
        }
        super.onClose();
    }
}
