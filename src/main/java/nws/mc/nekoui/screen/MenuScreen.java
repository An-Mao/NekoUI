package nws.mc.nekoui.screen;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nws.dev.core.array._Array3D;
import nws.dev.core.javascript._EasyJS;
import nws.dev.core.system._KeySimulate;
import nws.dev.core.system._System;
import nws.mc.cores.screen.widget.DT_ListBoxData;
import nws.mc.nekoui.config.Config;
import nws.mc.nekoui.config.Configs;
import nws.mc.nekoui.config.menu.MenuConfig;
import nws.mc.nekoui.config.menu.MenuData;
import nws.mc.nekoui.config.menu.MenuScreenConfig;
import nws.mc.nekoui.config.page.PageConfig;
import nws.mc.nekoui.config.page.PageData;
import nws.mc.nekoui.screen.widget.CircularMenu;
import nws.mc.nekoui.screen.widget.CircularNewMenu;
import org.slf4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (Config.INSTANCE.getDatas().isAutoPage()) {
            List<DT_ListBoxData> data = new ArrayList<>();
            MenuConfig.INSTANCE.getDatas().forEach((s, menuData) -> data.add(new DT_ListBoxData(Component.literal(menuData.getName()), s, this::run)));
            circularMenu = new CircularMenu(width / 2, height / 2, width, height, MenuScreenConfig.INSTANCE.getDatas().sectors, MenuScreenConfig.INSTANCE.getDatas().innerRadius, MenuScreenConfig.INSTANCE.getDatas().outerRadius, Component.empty(), data);
            circularMenu.setFlipMode(CircularMenu.FlipMode.button);

            //circularMenu.setBackgroundHoverColor(Integer.parseInt(MenuScreenConfig.INSTANCE.getDatas().SelectColor, 16));
            //circularMenu.setBackgroundUsualColor(Integer.parseInt(MenuScreenConfig.INSTANCE.getDatas().UsualColor, 16));
            addRenderableWidget(circularMenu);
        }else {
            _Array3D<String, PageData,Map<String,DT_ListBoxData>> array3D = new _Array3D<>();

            PageConfig.INSTANCE.getDatas().forEach((s, pageData) -> {
                Map<String,DT_ListBoxData> data = new HashMap<>();
                pageData.getProjects().forEach((s1, projectData) -> {
                    MenuData menuData = MenuConfig.INSTANCE.getDatas().get(projectData.key());
                    String name = "";
                    if (menuData != null) {
                        name = menuData.getName();
                    }
                    data.put(s1,new DT_ListBoxData(Component.literal(name), projectData.key(), this::run));
                });
                array3D.add(s,pageData,data);
            });

            circularNewMenu = new CircularNewMenu(width / 2, height / 2, width, height, Component.empty(),array3D);
            circularNewMenu.setFlipMode(CircularNewMenu.FlipMode.button);
            addRenderableWidget(circularNewMenu);

        }
    }
    public void run(Object v){
        if (v instanceof String s){
            MenuData menuData = MenuConfig.INSTANCE.getDatas().get(s);
            if (menuData != null){
                int type = menuData.getType();
                String value = menuData.getValue();
                if (!value.isEmpty()){
                    ProjectsSettingScreen.RunType runType = ProjectsSettingScreen.RunType.fromInt(type);
                    switch (runType){
                        case ProjectsSettingScreen.RunType.message -> sendChat(menuData.getValue());
                        case ProjectsSettingScreen.RunType.command -> sendCommand(menuData.getValue());
                        case ProjectsSettingScreen.RunType.button -> {
                            String[] keyA = menuData.getValue().split(" ");
                            int[] keys = new int[keyA.length];
                            //System.out.println(menuData.getValue()+ "  KeyA:"+Arrays.toString(keyA));
                            for (int i = 0; i < keyA.length;i++){
                                keys[i] = Integer.parseInt(keyA[i]);
                            }
                            simulateKey(keys);
                        }
                        case ProjectsSettingScreen.RunType.js -> _EasyJS.creat()
                                .addParameter("minecraft", Minecraft.getInstance())
                                .runFile(Configs.ConfigDir_JS + menuData.getValue());
                    }
                }
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
