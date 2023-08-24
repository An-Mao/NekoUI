package anmao.idoll.nekoui;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = NekoUI.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.BooleanValue POI_DYNAMIC_SIZE = BUILDER
            .comment("[poi]Dynamic size or not")
            .define("poiDynamicSize", true);
    private static final ForgeConfigSpec.IntValue POI_D_RADIUS = BUILDER
            .comment("[poi]Detection radius")
            .defineInRange("poiDetectionRadius", 20, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue POI_S_RADIUS = BUILDER
            .comment("[poi]Show radius")
            .defineInRange("poiShowRadius", 80, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue POI_SD_RADIUS = BUILDER
            .comment("[poi]Dynamic radius (square) .if you want set 10,this write 100 .(10*10)")
            .defineInRange("poiDynamicRadius", 100, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue POI_SIZE = BUILDER
            .comment("[poi]poi size")
            .defineInRange("poiSize", 7, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue POI_SIZE_DYNAMIC = BUILDER
            .comment("[poi]poi size (Dynamic)")
            .defineInRange("poiSizeDynamic", 10, 0, Integer.MAX_VALUE);
    static final ForgeConfigSpec SPEC = BUILDER.build();
    public static boolean poiDynamicSize;
    public static int poiDetectionRadius;
    public static int poiShowRadius;
    public static int poiDynamicRadius;
    public static int poiSize;
    public static int poiSizeDynamic;
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        poiDynamicSize = POI_DYNAMIC_SIZE.get();
        poiDetectionRadius = POI_D_RADIUS.get();
        poiShowRadius = POI_S_RADIUS.get();
        poiDynamicRadius = POI_SD_RADIUS.get();
        poiSize = POI_SIZE.get();
        poiSizeDynamic = POI_SIZE_DYNAMIC.get();
    }
}
