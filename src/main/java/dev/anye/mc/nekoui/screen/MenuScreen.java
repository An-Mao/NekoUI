package dev.anye.mc.nekoui.screen;

import com.mojang.logging.LogUtils;
import dev.anye.core.system._KeySimulate;
import dev.anye.core.system._System;
import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
import dev.anye.mc.nekoui.config.Config;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.config.menu.MenuScreenConfig;
import dev.anye.mc.nekoui.dat_type.MenuProject;
import dev.anye.mc.nekoui.screen.widget.CircularMenu;
import dev.anye.mc.nekoui.screen.widget.CircularNewMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
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
		Config.INSTANCE.ifPresent(configData -> {

			if (configData.autoPage()) {
				MenuScreenConfig.INSTANCE.ifPresent(menuScreenData -> {
					List<DT_ListBoxData> data = new ArrayList<>();

					Configs.MENU_PROJECTS.forEach((s, menuData) -> data.add(new DT_ListBoxData(Component.literal(menuData.name()), s, this::run)));

					circularMenu = new CircularMenu(width / 2, height / 2, width, height, menuScreenData.sectors(), menuScreenData.innerRadius(), menuScreenData.outerRadius(), Component.empty(), data);
					circularMenu.setFlipMode(CircularMenu.FlipMode.button);

					addRenderableWidget(circularMenu);
				});
			}else {

				List<CircularNewMenu.RenderPageData> pd = new ArrayList<>();
				Configs.MENU_PAGE.forEach(pageData -> pd.add(CircularNewMenu.RenderPageData.creat(pageData, this::run)));

				circularNewMenu = new CircularNewMenu(width / 2, height / 2, width, height, Component.empty(), pd);
				circularNewMenu.setFlipMode(CircularNewMenu.FlipMode.button);
				addRenderableWidget(circularNewMenu);

			}
		});
    }


	public void run(Object v) {
		if (v instanceof String s) {
			MenuProject menuData = Configs.MENU_PROJECTS.get(s);
			if (menuData != null) {
				menuData.onClick();
			}
		}
	}


    public static void sendChat(String msg) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null){
            player.connection.sendChat(msg);
        }
    }
    public static void sendCommand(String command) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null){
            player.connection.sendCommand(command);
        }
    }
    public static void simulateKey(int[] keyCode) {
        if (_System.isWindows){
            for (int key : keyCode) {
                _KeySimulate.simulateKeyPress(key);
            }
            for (int i = keyCode.length - 1 ; i >= 0 ;i--) {
                _KeySimulate.simulateKeyRelease(keyCode[i]);
            }
        }else {
            try {
                Robot robot = new Robot();
                for (int key : keyCode) {
                    robot.keyPress(key);
                }
                for (int i = keyCode.length - 1 ; i >= 0 ;i--) {
                    robot.keyRelease(keyCode[i]);
                }
            } catch (AWTException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

	@Override
	public void onClose() {
		Config.INSTANCE.ifPresent(configData -> {
			DT_ListBoxData dtListBoxData = configData.autoPage() ? circularMenu.getData() : circularNewMenu.getData();
			if (dtListBoxData != null) {
				dtListBoxData.OnPress(dtListBoxData.getValue());
			}
		});
		super.onClose();
	}
}
