package anmao.mc.nekoui.event;

import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.lib.am._Sys;
import anmao.mc.nekoui.config.CC;
import anmao.mc.nekoui.constant._MC;
import anmao.mc.nekoui.hud.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
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
        @SubscribeEvent
        public static void onMouseScroll(InputEvent.MouseScrollingEvent event){
            //Messages.sendToServer(new TimeIsLifeS());
            if (_MC.MC.player != null) {
                if(_MC.MC.player.isAlive()){
                    _Sys.setNowTime();
                }
            }
        }
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){
            if (_MC.MC.player != null) {
                if(_MC.MC.player.isAlive()){
                    //GLFW.GLFW_KEY_1
                    if (event.getKey() >= 49 && event.getKey()<= 57) {
                        _Sys.setNowTime();
                    }
                }
            }
        }
    }
    @Mod.EventBusSubscriber(modid = NekoUI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            if (CC.hudInfoMode){
                event.registerAboveAll(HUD_Info.id,HUD_Info.UI);
            }
            if (CC.hudItemMode){
                event.registerAboveAll(HUD_Item.id,HUD_Item.UI);
            }
            if (CC.hudMobMode) {
                event.registerAboveAll(HUD_Mob.id, HUD_Mob.UI_INFO);
            }
            //event.registerAboveAll("uitem", UI_Item.UI_ITEM);
            //event.registerAboveAll("uiplayer", UI_Player.UI_PLAYER);
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }

    }
}
