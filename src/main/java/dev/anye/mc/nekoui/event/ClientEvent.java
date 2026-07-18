package dev.anye.mc.nekoui.event;

import com.google.common.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import dev.anye.mc.cores.cr.CoresRegs;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.Config;
import dev.anye.mc.nekoui.gui.hot$bar.HotBarSys;
import dev.anye.mc.nekoui.screen.MenuScreen;
import dev.anye.mc.nekoui.screen.SettingScreen;
import dev.anye.mc.nekoui.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.AddFramePassEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = NekoUI.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE ,value = Dist.CLIENT)
public class ClientEvent {
    private static final Logger LOGGER = LogUtils.getLogger();


    @SubscribeEvent
    public static void regCommand(RegisterCommandsEvent event) {
        CommandList commandList = new CommandList(event.getDispatcher());
        commandList.register();
    }

    /*
    @SubscribeEvent
    public static void onGuiRender(RenderGuiLayerEvent.Pre event){
        if (Config.INSTANCE.getDatas().isOutputGuiId()){
            LOGGER.info(event.getName().toString());
        }
        if (HideHudConfig.I.isHide(event.getName())){
            event.setCanceled(true);
        }
    }

     */

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
                if (Config.INSTANCE.getData().isMenu()) {
                    if (event.getKey() == KeyBinding.OPEN_MENU.getKey().getValue()) {
                        Screen screen = Minecraft.getInstance().gui.screen();
                        if (event.getAction() == 0) {
                            if (screen instanceof MenuScreen menuScreen) {
                                menuScreen.onClose();
                            }
                        } else if (event.getAction() == 1) {
                            if (screen == null) {
                                Minecraft.getInstance().setScreenAndShow(new MenuScreen());
                            }
                        }
                    }
                    if (event.getKey() == KeyBinding.OPEN_SET_MENU.getKey().getValue()) {
                        Screen screen = Minecraft.getInstance().gui.screen();
                        if (event.getAction() == 1) {
                            if (screen == null) {
                                Minecraft.getInstance().setScreenAndShow(new SettingScreen());
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingRender(AddFramePassEvent event){
        event.addPass(Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"health_bar"),new HealthBarFramePassRender());
    }

//	@SubscribeEvent
//	public static void onRender(RenderLivingEvent.Post<LivingEntity, ?, ?> event) {
//		event.getState().
//		if (event.getRenderState().getRenderData(RenderStateEntityKey) instanceof LivingEntity livingEntity) {
//			CoresRegs.ENTITY_RENDER_REG.forEach((s, iEntityRender) -> iEntityRender.render(livingEntity,
//					event.getRenderState(), event.getRenderer(), event.getSubmitNodeCollector(), event.getPoseStack()));
//            event.getState().
//		}
//	}


	@SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event){
        if (Config.INSTANCE.getData().isMenu()){
            event.register(KeyBinding.OPEN_MENU);
            event.register(KeyBinding.OPEN_SET_MENU);
        }
    }
}
