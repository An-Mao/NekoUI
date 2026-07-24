package dev.anye.mc.nekoui.event;

import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.Config;
import dev.anye.mc.nekoui.config.hotbar.HotBarConfig;
import dev.anye.mc.nekoui.config.mob$direction.MobDirectionConfig;
import dev.anye.mc.nekoui.gui.MobDirectionGui;
import dev.anye.mc.nekoui.gui.ScreenElementGui;
import dev.anye.mc.nekoui.gui.hot$bar.HotBarGui;
import dev.anye.mc.nekoui.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
@Mod.EventBusSubscriber(modid = NekoUI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvent {
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){

		event.registerAboveAll("screen-element", ScreenElementGui::render);
		event.registerAboveAll("mob-direction", MobDirectionGui::render);
		event.registerAboveAll("hot-bar", HotBarGui::render);

    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
    }
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event){
		Config.INSTANCE.ifPresent(configData -> {
			if (configData.menu()) {
				event.register(KeyBinding.OPEN_MENU);
				event.register(KeyBinding.OPEN_SET_MENU);
			}
		});
    }
}
