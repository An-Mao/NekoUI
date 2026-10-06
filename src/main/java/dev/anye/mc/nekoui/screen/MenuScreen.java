package dev.anye.mc.nekoui.screen;

import com.mojang.logging.LogUtils;
import dev.anye.mc.cores.screen.widget.CircularWidget;
import dev.anye.mc.cores.screen.widget.SimpleListBoxData;
import dev.anye.mc.nekoui.config.Config;
import dev.anye.mc.nekoui.config.ConfigData;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.config.menu.MenuScreenConfig;
import dev.anye.mc.nekoui.register.MenuProject;
import dev.anye.mc.nekoui.register.Registers;
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

	private void addMenu() {
			if (Boolean.TRUE.equals(Config.INSTANCE.fetch(ConfigData::autoPage))) {
				MenuScreenConfig.INSTANCE.read(menuScreenData -> {
					List<SimpleListBoxData> data = new ArrayList<>();

					Configs.MENU_PROJECTS.forEach((s, menuData) -> data.add(new SimpleListBoxData(Component.literal(menuData.name()), s, this::run)));



					circularMenu = new CircularMenu(width / 2, height / 2, width, height, menuScreenData.sectors(), menuScreenData.innerRadius(), menuScreenData.outerRadius(), Component.empty(), data);
					circularMenu.setFlipMode(CircularWidget.FlipMode.BUTTON);

					addRenderableWidget(circularMenu);
				});
			} else {
				List<CircularNewMenu.RenderPageData> pd = new ArrayList<>();

				circularNewMenu = new CircularNewMenu(width / 2, height / 2, width, height, Component.empty(), pd);
				circularNewMenu.setFlipMode(CircularNewMenu.FlipMode.button);
				addRenderableWidget(circularNewMenu);

			}
	}

	public void run(Object v) {
		if (v instanceof String s) {
			MenuProject menuData = Configs.MENU_PROJECTS.get(s);
			if (menuData != null) {
				menuData.onClick();
			}
		}
	}

	@Override
	public void onClose() {
		Config.INSTANCE.read(configData -> {
			SimpleListBoxData dtListBoxData = configData.autoPage() ? circularMenu.getData() : circularNewMenu.getData();
			if (dtListBoxData != null) {
				dtListBoxData.onPress(dtListBoxData.value());
			}
		});
		super.onClose();
	}
}
