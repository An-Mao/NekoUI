package anmao.mc.nekoui.lib.ai;

import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.config.CC;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NekoUI.MOD_ID,value = Dist.CLIENT)
public class HUDToggleEventHandler {
    private static final ResourceLocation[] RM = {
            new ResourceLocation("minecraft:experience_bar"),
            new ResourceLocation("minecraft:player_health"),
            new ResourceLocation("minecraft:food_level")
    };

    private static final ResourceLocation HOT_BAR = new ResourceLocation("minecraft:hotbar");
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event){
        if (CC.hudItemMode && event.getOverlay().id().equals(HOT_BAR)){
            event.setCanceled(true);
        }
        if (CC.hudInfoMode) {
            for (ResourceLocation resourceLocation : RM) {
                if (event.getOverlay().id().equals(resourceLocation)) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
