package nws.mc.nekoui.event;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import nws.mc.nekoui.NekoUI;
import nws.mc.nekoui.config.Config;
import nws.mc.nekoui.config.hide$hud.HideHudConfig;
import nws.mc.nekoui.gui.hot$bar.HotBarSys;
import nws.mc.nekoui.render.MobHealthBar;
import nws.mc.nekoui.screen.MenuScreen;
import nws.mc.nekoui.screen.SettingScreen;
import nws.mc.nekoui.util.KeyBinding;
import org.slf4j.Logger;
@EventBusSubscriber(modid = NekoUI.MOD_ID,bus = EventBusSubscriber.Bus.GAME ,value = Dist.CLIENT)
public class ClientEvent {
    private static final Logger LOGGER = LogUtils.getLogger();


    @SubscribeEvent
    public static void regCommand(RegisterCommandsEvent event) {
        CommandList commandList = new CommandList(event.getDispatcher());
        commandList.register();
    }

    @SubscribeEvent
    public static void onGuiRender(RenderGuiLayerEvent.Pre event){
        if (Config.INSTANCE.getDatas().isOutputGuiId()){
            LOGGER.info(event.getName().toString());
        }
        if (HideHudConfig.I.isHide(event.getName())){
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        //Messages.sendToServer(new TimeIsLifeS());
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.isAlive()) {
                HotBarSys.setNowTime();
            }
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.isAlive()) {
                //GLFW.GLFW_KEY_1
                if (event.getKey() >= 49 && event.getKey() <= 57) {
                    HotBarSys.setNowTime();
                }
                if (Config.INSTANCE.getDatas().isMenu()) {
                    if (event.getKey() == KeyBinding.OPEN_MENU.getKey().getValue()) {
                        Screen screen = Minecraft.getInstance().screen;
                        if (event.getAction() == 0) {
                            if (screen instanceof MenuScreen menuScreen) {
                                menuScreen.onClose();
                            }
                        } else if (event.getAction() == 1) {
                            if (screen == null) {
                                Minecraft.getInstance().setScreen(new MenuScreen());
                            }
                        }
                    }
                    if (event.getKey() == KeyBinding.OPEN_SET_MENU.getKey().getValue()) {
                        Screen screen = Minecraft.getInstance().screen;
                        if (event.getAction() == 1) {
                            if (screen == null) {
                                Minecraft.getInstance().setScreen(new SettingScreen());
                            }
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void onNameTagRender(RenderNameTagEvent event){
        MobHealthBar.render(event.getEntity(),event.getPoseStack(),event.getPackedLight());
    }
}
