package dev.anye.mc.nekoui.register;

import dev.anye.mc.cores.register.Register;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.register.screen_element.ScreenElement;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.RegistryBuilder;

public class Registers {

	public static final Register<ScreenElement> SCREEN_ELEMENT = new Register<>(NekoUI.MOD_ID, "screen_element", RegistryBuilder::disableSync);
	public static final Register<MenuProject> MENU_PROJECT = new Register<>(NekoUI.MOD_ID, "menu_project", RegistryBuilder::disableSync);
	public static final Register<MenuPage> MENU_PAGE = new Register<>(NekoUI.MOD_ID, "menu_page", RegistryBuilder::disableSync);


	//public static final Register<Listen> LISTEN = new Register<>(ListenRegister.LISTEN_REGISTER.getRegistry(),NekoUI.MOD_ID);





	//public static final DeferredHolder<Listen, NekoUIListen> NEKO_UI_LISTEN = LISTEN.register("nekoui", NekoUIListen::new);

	private Registers(){}

	public static void register(BusGroup eventBus) {
		SCREEN_ELEMENT.register(eventBus);
		MENU_PROJECT.register(eventBus);
		MENU_PAGE.register(eventBus);
		//LISTEN.register(eventBus);
	}
}
