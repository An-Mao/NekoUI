package dev.anye.mc.nekoui.config;

import com.mojang.logging.LogUtils;
import dev.anye.core.cdt._SuffixCDT;
import dev.anye.core.pack._Pack;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.ban_screen.BanScreenConfig;
import dev.anye.mc.nekoui.config.health_bar.HealthBarConfig;
import dev.anye.mc.nekoui.config.hide_hud.HideHudConfig;
import dev.anye.mc.nekoui.config.hotbar.HotBarConfig;
import dev.anye.mc.nekoui.config.menu.MenuPageIO;
import dev.anye.mc.nekoui.config.menu.MenuProjectIO;
import dev.anye.mc.nekoui.config.menu.MenuScreenConfig;
import dev.anye.mc.nekoui.config.mob_direction.MobDirectionConfig;
import dev.anye.mc.nekoui.config.screen_element.ScreenRenderIO;
import dev.anye.mc.nekoui.dat_type.MenuPageData;
import dev.anye.mc.nekoui.register.MenuProject;
import dev.anye.mc.nekoui.register.screen_element.ScreenElement;

import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configs {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final String CONFIG_DIR = _File.getFileFullPathWithRun("config", "NekoUI");
	public static final String CONFIG_DIR_JS = _File.getFilePath(CONFIG_DIR, "JavaScript");
	public static final String CONFIG_DIR_SCREEN_ELEMENT = _File.getFilePath(CONFIG_DIR, "ScreenElement");
	public static final String CONFIG_DIR_MENU = _File.getFilePath(CONFIG_DIR, "Menu");
	public static final String CONFIG_DIR_MENU_PROJECT = _File.getFilePath(CONFIG_DIR_MENU, "Project");
	public static final String CONFIG_DIR_MENU_PAGE = _File.getFilePath(CONFIG_DIR_MENU, "Page");

	private static boolean isInit = true;
	public static final List<ScreenElement> SCREEN_RENDERS = new ArrayList<>();
	public static final Map<String, MenuProject> MENU_PROJECTS = new HashMap<>();
	public static final List<MenuPageData> MENU_PAGE = new ArrayList<>();

	static {
		_File.checkAndCreateDir(CONFIG_DIR);
		_File.checkAndCreateDir(CONFIG_DIR_JS);
		_File.checkAndCreateDir(CONFIG_DIR_SCREEN_ELEMENT);
		_File.checkAndCreateDir(CONFIG_DIR_MENU);
		_File.checkAndCreateDir(CONFIG_DIR_MENU_PROJECT);
		_File.checkAndCreateDir(CONFIG_DIR_MENU_PAGE);
	}

	private Configs(){}

	public static void init() {
		LOGGER.info("load config");
		Config.INSTANCE.reload();
		if (isInit) {
			isInit = false;
			Config.INSTANCE.ifPresent(configData -> {
				if (configData.putDefault()) {
					_Pack.writeFiles("assets/nekoui/config/screen/", Configs.CONFIG_DIR_SCREEN_ELEMENT + File.separator, _SuffixCDT.JSON_SUFFIX, "air", "armor", "armor_toughness", "damage", "exp", "fps", "health", "hunger", "luck", "speed");
					_Pack.writeFiles("assets/nekoui/config/menu/project/", Configs.CONFIG_DIR_MENU_PROJECT + File.separator, _SuffixCDT.JSON_SUFFIX, "SwitchItemSlot0", "SwitchItemSlot1", "SwitchItemSlot2", "SwitchItemSlot3", "SwitchItemSlot4", "SwitchItemSlot5", "SwitchItemSlot6", "SwitchItemSlot7", "SwitchItemSlot8", "TestCommand", "TestCommand1", "TestMessage");
					_Pack.writeFiles("assets/nekoui/config/menu/page/", Configs.CONFIG_DIR_MENU_PAGE + File.separator, _SuffixCDT.JSON_SUFFIX, "page1", "page2", "page3");
					configData.setPutDefault(false);
					Config.INSTANCE.save();
				}
			});

		}
		BanScreenConfig.I.reload();
		HealthBarConfig.I.reload();
		HideHudConfig.I.reload();
		HotBarConfig.INSTANCE.reload();
		MenuScreenConfig.INSTANCE.reload();

		MobDirectionConfig.I.reload();

		LoadScreenRender();
		LoadMenuProject();
		LoadMenuPage();
	}

	public static void LoadScreenRender() {
		SCREEN_RENDERS.clear();
		_File.getFiles(CONFIG_DIR_SCREEN_ELEMENT, _SuffixCDT.JSON_SUFFIX).forEach(path -> new ScreenRenderIO(path.getFileName().toString()).ifPresent(screenRender -> {
			LOGGER.debug("sce {}",screenRender.elements());
			SCREEN_RENDERS.add(new ScreenElement(screenRender));
		}));
	}

	public static void LoadMenuProject() {
		MENU_PROJECTS.clear();
		_File.getFiles(CONFIG_DIR_MENU_PROJECT, _SuffixCDT.JSON_SUFFIX).forEach(path -> new MenuProjectIO(path.getFileName().toString()).ifPresent(menuProjectData -> MENU_PROJECTS.put(getFileNameWithoutExtension(path.getFileName().toString()), new MenuProject(menuProjectData))));
	}

	public static void LoadMenuPage() {
		MENU_PAGE.clear();
		_File.getFiles(CONFIG_DIR_MENU_PAGE, _SuffixCDT.JSON_SUFFIX).forEach(path -> new MenuPageIO(path.getFileName().toString()).ifPresent(MENU_PAGE::add));
	}

	public static String getFileNameWithoutExtension(String filename) {
		int dotIndex = filename.lastIndexOf('.');
		if (dotIndex > 0 && dotIndex < filename.length() - 1) {
			return filename.substring(0, dotIndex);
		} else {
			return filename;
		}
	}
}
