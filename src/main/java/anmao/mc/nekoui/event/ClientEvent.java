package anmao.mc.nekoui.event;

import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.hud.UI_Info;
import anmao.mc.nekoui.hud.UI_Item;
import anmao.mc.nekoui.hud.UI_Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvent {
    @Mod.EventBusSubscriber(modid = NekoUI.MOD_ID,value = Dist.CLIENT)
    public static class CE{
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event){
            //System.out.println("______________tick______________");
        }
    }
    @Mod.EventBusSubscriber(modid = NekoUI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){

            event.registerAboveAll("uinfo", UI_Info.UI_INFO);
            event.registerAboveAll("uitem", UI_Item.UI_ITEM);
            event.registerAboveAll("uiplayer", UI_Player.UI_PLAYER);
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }

    }
}
