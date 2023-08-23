package anmao.idoll.nekoui.event;

import anmao.idoll.nekoui.NekoUI;
import anmao.idoll.nekoui.hud.UI_Info;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.List;

public class ClientEvent {
    @Mod.EventBusSubscriber(modid = NekoUI.MODID,value = Dist.CLIENT)
    public static class CE{
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event){
            //System.out.println("______________tick______________");
        }
    }
    @Mod.EventBusSubscriber(modid = NekoUI.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("uinfo", UI_Info.UI_INFO);
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }

    }
}
