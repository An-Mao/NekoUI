package anmao.idoll.nekoui.event;

import anmao.idoll.nekoui.NekoUI;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvent {
    @Mod.EventBusSubscriber(modid = NekoUI.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }

        public static void onClientTick(TickEvent.ClientTickEvent event){
            System.out.println("______________tick______________");
        }
    }
}
