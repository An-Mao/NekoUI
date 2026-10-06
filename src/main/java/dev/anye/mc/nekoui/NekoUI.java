package dev.anye.mc.nekoui;


import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.register.Registers;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;

@Mod(NekoUI.MOD_ID)
public class NekoUI {
    public static final String MOD_ID = "nekoui";
    static {
        Configs.init();
    }
    public NekoUI(FMLJavaModLoadingContext context) {
		//Registers.register(context.getModBusGroup());
    }
}
