package dev.anye.mc.nekoui;

import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.register.Registers;
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
		Registers.register(modEventBus);
	}


	private void registerRegistries(NewRegistryEvent event) {
		event.register(Registers.SCREEN_ELEMENT.getRegistry());
		event.register(Registers.MENU_PROJECT.getRegistry());
		event.register(Registers.MENU_PAGE.getRegistry());
	}
}
