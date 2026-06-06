package dev.anye.mc.nekoui;


import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.register.menu_page.MenuPageRegister;
import dev.anye.mc.nekoui.register.menu_project.MenuProjectRegister;
import dev.anye.mc.nekoui.register.screen_element.ScreenElementRegister;
import dev.anye.mc.register.nekoui.NekoUIRegister;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@Mod(NekoUI.MOD_ID)
public class NekoUI {
    public static final String MOD_ID = "nekoui";
    static {
        Configs.init();
        NekoUIRegister.reg();
    }
    public NekoUI(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::registerRegistries);
        ScreenElementRegister.register(modEventBus);
        MenuProjectRegister.register(modEventBus);
        MenuPageRegister.register(modEventBus);
    }


    private void registerRegistries(NewRegistryEvent event) {
        event.register(ScreenElementRegister.REGISTRY);
        event.register(MenuProjectRegister.REGISTRY);
		event.register(MenuPageRegister.REGISTRY);
    }
}
