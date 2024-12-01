package nws.mc.nekoui.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import nws.mc.nekoui.NekoUI;
import nws.mc.nekoui.config.Config;
import nws.mc.nekoui.gui.MobDirectionGui;
import nws.mc.nekoui.gui.ScreenElementGui;
import nws.mc.nekoui.gui.hot$bar.HotBarGui;
import nws.mc.nekoui.util.KeyBinding;

@EventBusSubscriber(modid = NekoUI.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvent {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
    }
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event){
        if (Config.INSTANCE.getDatas().isMenu()){
            event.register(KeyBinding.OPEN_MENU);
            event.register(KeyBinding.OPEN_SET_MENU);
        }
    }
    @SubscribeEvent
    public static void onGuiReg(RegisterGuiLayersEvent event){
        event.registerAboveAll(ScreenElementGui.RESOURCE_LOCATION, ScreenElementGui::render);
        event.registerAboveAll(MobDirectionGui.RESOURCE_LOCATION, MobDirectionGui::render);
        event.registerAboveAll(HotBarGui.RESOURCE_LOCATION, HotBarGui::render);
    }
}
