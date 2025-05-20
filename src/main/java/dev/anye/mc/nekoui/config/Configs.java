package dev.anye.mc.nekoui.config;

import com.mojang.logging.LogUtils;
import dev.anye.core.system._File;
import dev.anye.mc.nekoui.config.ban$screen.BanScreenConfig;
import dev.anye.mc.nekoui.config.health$bar.HealthBarConfig;
import dev.anye.mc.nekoui.config.hide$hud.HideHudConfig;
import dev.anye.mc.nekoui.config.hotbar.HotBarConfig;
import dev.anye.mc.nekoui.config.menu.MenuPageConfig;
import dev.anye.mc.nekoui.config.menu.MenuPageIO;
import dev.anye.mc.nekoui.config.menu.MenuProjectIO;
import dev.anye.mc.nekoui.config.menu.MenuScreenConfig;
import dev.anye.mc.nekoui.config.mob$direction.MobDirectionConfig;
import dev.anye.mc.nekoui.config.screen$element.ScreenRenderIO;
import dev.anye.mc.nekoui.dat$type.MenuPageData;
import dev.anye.mc.nekoui.dat$type.MenuProjectData;
import dev.anye.mc.nekoui.dat$type.ScreenRender;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class Configs {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String ConfigDir = _File.getFileFullPathWithRun("config/NekoUI/");
    public static final String ConfigDir_JS = ConfigDir +"JavaScript/";
    public static final String ConfigDir_ScreenElement = ConfigDir +"ScreenElement/";
    public static final String ConfigDir_Menu = ConfigDir +"Menu/";
    public static final String ConfigDir_MenuProject = ConfigDir_Menu +"Project/";
    public static final String ConfigDir_MenuPage = ConfigDir_Menu +"Page/";
    private static boolean isInit = true;

    public static final List<ScreenRender> ScreenRenders = new ArrayList<>();
    public static final HashMap<String, MenuProjectData> MenuProjects = new HashMap<>();
    public static final List<MenuPageData> MenuPage = new ArrayList<>();

    static {
        _File.checkAndCreateDir(ConfigDir);
        _File.checkAndCreateDir(ConfigDir_JS);
        _File.checkAndCreateDir(ConfigDir_ScreenElement);
        _File.checkAndCreateDir(ConfigDir_Menu);
        _File.checkAndCreateDir(ConfigDir_MenuProject);
        _File.checkAndCreateDir(ConfigDir_MenuPage);
    }

    public static void init() {
        LOGGER.info("load config");
        Config.INSTANCE.init();
        if (isInit) {
            isInit = false;
            OldToNew.ToNewScreenElementConfig();
            OldToNew.ToNewMenuProjectConfig();
            OldToNew.ToNewMenuPageConfig();
            if (Config.INSTANCE.getDatas().isPutDefault()){
                Config.INSTANCE.getDatas().setPutDefault(false);
                Config.INSTANCE.save();
            }
        }
        BanScreenConfig.I.init();
        HealthBarConfig.I.init();
        HideHudConfig.I.init();
        HotBarConfig.INSTANCE.init();
        MenuScreenConfig.INSTANCE.init();

        //MenuConfig.INSTANCE.init();

        MobDirectionConfig.I.init();
        //PageConfig.INSTANCE.init();
        LoadScreenRender();
        LoadMenuProject();
        LoadMenuPage();
    }

    public static void LoadScreenRender() {
        ScreenRenders.clear();
        _File.getFiles(ConfigDir_ScreenElement,".json").forEach(path -> {
            ScreenRender screenRender = new ScreenRenderIO(path.getFileName().toString()).getDatas();
            if (screenRender != null) ScreenRenders.add(screenRender);
        });
    }

    public static void LoadMenuProject() {
        MenuProjects.clear();
        _File.getFiles(ConfigDir_MenuProject,".json").forEach(path -> {
            MenuProjectData menuProjectData = new MenuProjectIO(path.getFileName().toString()).getDatas();
            if (menuProjectData != null) MenuProjects.put(getFileNameWithoutExtension(path.getFileName().toString()), menuProjectData);
        });
    }
    public static void LoadMenuPage() {
        MenuPage.clear();
        MenuPageConfig.I.getDatas().forEach(s -> {
            String filePath = ConfigDir_MenuPage+s+".json";
            File file = new File(filePath);
            if (file.exists()){
                MenuPageData menuPageData = new MenuPageIO(s + ".json").getDatas();
                if (menuPageData != null ) MenuPage.add(menuPageData);
            }
        });
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
