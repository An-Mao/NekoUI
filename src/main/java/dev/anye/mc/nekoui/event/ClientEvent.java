package dev.anye.mc.nekoui.event;

import com.mojang.logging.LogUtils;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.Config;
import dev.anye.mc.nekoui.config.hide$hud.HideGuiConfig;
import dev.anye.mc.nekoui.gui.hot$bar.HotBarSys;
import dev.anye.mc.nekoui.render.MobHealthBar;
import dev.anye.mc.nekoui.screen.MenuScreen;
import dev.anye.mc.nekoui.screen.SettingScreen;
import dev.anye.mc.nekoui.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
@Mod.EventBusSubscriber(modid = NekoUI.MOD_ID,value = Dist.CLIENT)
public class ClientEvent {
    private static final Logger LOGGER = LogUtils.getLogger();
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if (Config.INSTANCE.getDatas().isOutputGuiId()) {
            LOGGER.info("Render Gui => " + event.getOverlay().id().toString());
        }
        if (HideGuiConfig.guiList.contains(event.getOverlay().id().toString())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        //System.out.println("______________tick______________");
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
