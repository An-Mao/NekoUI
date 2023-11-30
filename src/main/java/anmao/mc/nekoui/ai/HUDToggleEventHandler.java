package anmao.mc.nekoui.ai;

import anmao.mc.nekoui.Config;
import anmao.mc.nekoui.NekoUI;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NekoUI.MOD_ID,value = Dist.CLIENT)
public class HUDToggleEventHandler {
    private static final ResourceLocation[] RM = {
            new ResourceLocation("minecraft:experience_bar"),
            new ResourceLocation("minecraft:hotbar"),
            new ResourceLocation("minecraft:player_health"),
            new ResourceLocation("minecraft:food_level")
    };
    private static boolean hideHUD = false;

    public static void setHideHUD(boolean hide) {
        hideHUD = hide;
    }

    public static boolean isHideHUD() {
        return hideHUD;
    }
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event){
        if (!Config.playerInfo){return;}
        for (ResourceLocation resourceLocation : RM) {
            if (event.getOverlay().id().equals(resourceLocation)) {
                event.setCanceled(true);
            }
        }
    }
}
