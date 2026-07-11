package dev.anye.mc.nekoui;

import com.mojang.logging.LogUtils;
import dev.anye.mc.nekoui.config.Config;
import dev.anye.mc.nekoui.config.hide_hud.HideHudConfig;
import dev.anye.mc.nekoui.event.CommandList;
import dev.anye.mc.nekoui.gui.MobDirectionGui;
import dev.anye.mc.nekoui.gui.ScreenElementGui;
import dev.anye.mc.nekoui.gui.hot$bar.HotBarGui;
import dev.anye.mc.nekoui.gui.hot$bar.HotBarSys;
import dev.anye.mc.nekoui.screen.MenuScreen;
import dev.anye.mc.nekoui.screen.SettingScreen;
import dev.anye.mc.nekoui.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

@Mod(value = NekoUI.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = NekoUI.MOD_ID, value = Dist.CLIENT)
public class NekoUIClient {
	public NekoUIClient() {}

	private static final Logger LOGGER = LogUtils.getLogger();

	@SubscribeEvent
	public static void regCommand(RegisterCommandsEvent event) {
		CommandList commandList = new CommandList(event.getDispatcher());
		commandList.register();
	}

	@SubscribeEvent
	public static void onGuiRender(RenderGuiLayerEvent.Pre event) {
		if (Config.INSTANCE.getData().isOutputGuiId()) {
			LOGGER.info("GUI ID: {}", event.getName());
		}
		if (HideHudConfig.I.isHide(event.getName())) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null && player.isAlive()) {
			HotBarSys.setNowTime();
		}
		
	}

	@SubscribeEvent
	public static void onKeyInput(InputEvent.Key event) {
		Player player = Minecraft.getInstance().player;
		if (player != null && player.isAlive()) {
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
					} else if (event.getAction() == 1 && screen == null) {
							Minecraft.getInstance().setScreenAndShow(new MenuScreen());
						}
					
				}
				if (event.getKey() == KeyBinding.OPEN_SET_MENU.getKey().getValue()) {
					Screen screen = Minecraft.getInstance().gui.screen();
					if (event.getAction() == 1 && screen == null) {
							Minecraft.getInstance().setScreenAndShow(new SettingScreen());
						}
					
				}
			}
		}
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
	}

	@SubscribeEvent
	public static void onKeyRegister(RegisterKeyMappingsEvent event) {
		if (Config.INSTANCE.getData().isMenu()) {
			event.register(KeyBinding.OPEN_MENU);
			event.register(KeyBinding.OPEN_SET_MENU);
		}
	}

	@SubscribeEvent
	public static void onGuiReg(RegisterGuiLayersEvent event) {
		event.registerAboveAll(ScreenElementGui.KEY, ScreenElementGui::render);
		event.registerAboveAll(MobDirectionGui.RESOURCE_LOCATION, MobDirectionGui::render);
		event.registerAboveAll(HotBarGui.RESOURCE_LOCATION, HotBarGui::render);
	}

	/*
	@SubscribeEvent
	public static void on(RegisterRenderStateModifiersEvent event){
		event.registerEntityModifier(new TypeToken<>() {}, (entity, renderState) -> {
			renderState.setRenderData(MobHealthBar.EntityKey,entity);
			if (entity instanceof LivingEntity livingEntity) {
				renderState.setRenderData(MobHealthBar.HealthKey, livingEntity.getHealth());
				renderState.setRenderData(MobHealthBar.MaxHealthKey, livingEntity.getMaxHealth());
			}
		});
	}

	 */
	public static void onELRSE(ExtractLevelRenderStateEvent event) {

	}

	//@SubscribeEvent
	public static void onRender(RenderLivingEvent.Post<LivingEntity, ?, ?> event) {

		//MobHealthBar.render(event.getRenderState(),event.getRenderer(),event.getSubmitNodeCollector(),event.getPoseStack());
        /*


        PoseStack poseStack = event.getPoseStack();
        var health = event.getRenderState().getRenderData(MobHealthBar.HealthKey);
        var maxHealth = event.getRenderState().getRenderData(MobHealthBar.MaxHealthKey);

        Minecraft minecraft = Minecraft.getInstance();
        Quaternionf camera = minecraft.getEntityRenderDispatcher().camera.rotation();
        poseStack.pushPose();
        float y = event.getRenderState().boundingBoxHeight + HealthBarConfig.I.getData().renderTop;
        poseStack.translate(0, y, 0);
        poseStack.mulPose(camera);
        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        poseStack.scale(-0.0125F, -0.005F, 0.0125F);

        event.getSubmitNodeCollector().submitCustomGeometry(poseStack, MobHealthBar.HBRT,(pose,vertexConsumer)->{
            RenderSupport.image(vertexConsumer,pose.pose(), -64, 0,
                    0, 0,
                    128, 32,
                    128, 128,
                    0,event.getRenderState().lightCoords);
            RenderSupport.image(vertexConsumer,pose.pose(), -64, 0,
                    0, 32,
                    Math.min(121, 121), 32,
                    128, 128,
                    0,event.getRenderState().lightCoords);
        });
        poseStack.popPose();
        //event.getSubmitNodeCollector().submitCustomGeometry();
         */
	}
}
