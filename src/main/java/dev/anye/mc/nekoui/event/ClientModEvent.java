package dev.anye.mc.nekoui.event;

import dev.anye.mc.nekoui.NekoUI;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = NekoUI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvent {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
    }

    /*
    @SubscribeEvent
    public static void onGuiReg(RegisterGuiOverlaysEvent event){
        event.registerAboveAll(ScreenElementGui.RESOURCE_LOCATION, ScreenElementGui::render);
        event.registerAboveAll(MobDirectionGui.RESOURCE_LOCATION, MobDirectionGui::render);
        event.registerAboveAll(HotBarGui.RESOURCE_LOCATION, HotBarGui::render);
    }

     */
}
