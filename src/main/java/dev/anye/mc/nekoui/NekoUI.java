package dev.anye.mc.nekoui;


import dev.anye.mc.nekoui.config.Configs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.io.IOException;

@Mod(NekoUI.MOD_ID)
public class NekoUI {
    public static final String MOD_ID = "nekoui";
    static {
        Configs.init();
        //NekoUIRegister.reg();


        try {
            EntityRenderStateAsm.Add();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public NekoUI(FMLJavaModLoadingContext context) {

    }
}
