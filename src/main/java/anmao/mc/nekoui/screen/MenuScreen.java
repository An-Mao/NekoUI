package anmao.mc.nekoui.screen;

import anmao.mc.amlib.screen.widget.DT_ListBoxData;
import anmao.mc.amlib.system.KeySimulate;
import anmao.mc.amlib.system._System;
import anmao.mc.nekoui.config.menu.MenuConfig;
import anmao.mc.nekoui.config.menu.MenuData;
import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MenuScreen extends Screen {
    protected static final Logger LOGGER = LogUtils.getLogger();
    private CircularMenu circularMenu;
    public MenuScreen() {
        super(Component.translatable("screen.nekoui.menu.title"));
    }

    @Override
    protected void init() {
        super.init();
        addMenu();
    }

    @Override
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        super.resize(pMinecraft, pWidth, pHeight);
    }

    private void addMenu(){
        List<DT_ListBoxData> data = new ArrayList<>();
        MenuConfig.INSTANCE.getDatas().forEach((s, menuData) -> data.add(new DT_ListBoxData(Component.literal(menuData.getName()),s,this::run)));
        circularMenu = new CircularMenu(width / 2 , height / 2,width,height,9,25,90,Component.empty(),data);
        circularMenu.setFlipMode(CircularMenu.FlipMode.button);
        circularMenu.setBgSelectColor(0x50ffffff);
        circularMenu.setBgUsualColor(0x70000000);
        addRenderableWidget(circularMenu);
    }
    public void run(Object v){
        if (v instanceof String s){
            MenuData menuData = MenuConfig.INSTANCE.getDatas().get(s);
            if (menuData != null){
                int type = menuData.getType();
                String value = menuData.getValue();
                if (!value.isEmpty()){
                    switch (type){
                        case 0 -> sendChat(menuData.getValue());
                        case 1 -> sendCommand(menuData.getValue());
                        case 2 -> {
                            String[] keyA = menuData.getValue().split(" ");
                            int[] keys = new int[keyA.length];
                            System.out.println(menuData.getValue()+ "  KeyA:"+Arrays.toString(keyA));
                            for (int i = 0; i < keyA.length;i++){
                                keys[i] = Integer.parseInt(keyA[i]);
                            }
                            simulateKey(keys);
                        }
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
                KeySimulate.simulateKeyPress(key);
            }
            for (int i = keyCode.length - 1 ; i >= 0 ;i--) {
                KeySimulate.simulateKeyRelease(keyCode[i]);
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
        DT_ListBoxData dtListBoxData = circularMenu.getData();
        if (dtListBoxData != null){
            dtListBoxData.OnPress(dtListBoxData.getValue());
        }
        super.onClose();
    }
}
