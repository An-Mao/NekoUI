package anmao.mc.nekoui.event;

import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.config.Config;
import anmao.mc.nekoui.gui.hot$bar.HotBarSys;
import anmao.mc.nekoui.render.MobHealthBar;
import anmao.mc.nekoui.screen.MenuScreen;
import anmao.mc.nekoui.screen.SetMenuScreen;
import anmao.mc.nekoui.util.KeyBinding;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import org.slf4j.Logger;
@EventBusSubscriber(modid = NekoUI.MOD_ID,value = Dist.CLIENT)
public class ClientEvent {
    private static final Logger LOGGER = LogUtils.getLogger();

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
                                Minecraft.getInstance().setScreen(new SetMenuScreen());
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
