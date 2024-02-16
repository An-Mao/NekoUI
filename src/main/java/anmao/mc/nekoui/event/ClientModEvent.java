package anmao.mc.nekoui.event;

import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.config.Config;
import anmao.mc.nekoui.config.hotbar.HotBarConfig;
import anmao.mc.nekoui.config.mob$direction.MobDirectionConfig;
import anmao.mc.nekoui.gui.HotBarGui;
import anmao.mc.nekoui.gui.MobDirectionGui;
import anmao.mc.nekoui.gui.ScreenElementGui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
@Mod.EventBusSubscriber(modid = NekoUI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvent {
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
        if (Config.mobDirectionConfig.isRenderScreenElement()){
            System.out.println("render reg : screen");
            event.registerAboveAll(ScreenElementGui.id, ScreenElementGui.UI);
        }
        if (HotBarConfig.hotBarData.isEnable()){
            event.registerAboveAll(HotBarGui.id, HotBarGui.UI);
        }
        if (MobDirectionConfig.mobDirectionConfig.isEnable()) {
            event.registerAboveAll(MobDirectionGui.id, MobDirectionGui.GUI);
        }
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
    }
}
