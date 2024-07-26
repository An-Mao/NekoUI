package anmao.mc.nekoui.event;

import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.config.Config;
import anmao.mc.nekoui.util.KeyBinding;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

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
}
