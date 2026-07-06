package dev.anye.mc.nekoui.register;

import dev.anye.mc.cores.am.listen.Listen;
import dev.anye.mc.cores.am.listen.ListenRegister;
import dev.anye.mc.cores.register.Register;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.register.listen.NekoUIListen;
import dev.anye.mc.nekoui.register.screen_element.ScreenElement;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

public class Registers {

	public static final Register<ScreenElement> SCREEN_ELEMENT = new Register<>(NekoUI.MOD_ID, "screen_element", builder -> builder.sync(false));
	public static final Register<MenuProject> MENU_PROJECT = new Register<>(NekoUI.MOD_ID, "menu_project", builder -> builder.sync(false));
	public static final Register<MenuPage> MENU_PAGE = new Register<>(NekoUI.MOD_ID, "menu_page", builder -> builder.sync(false));
	public static final Register<Listen> LISTEN = new Register<>(ListenRegister.LISTEN_REGISTER.getRegistry(),NekoUI.MOD_ID);





	public static final DeferredHolder<Listen, NekoUIListen> NEKO_UI_LISTEN = LISTEN.register("nekoui", NekoUIListen::new);

	private Registers(){}

	public static void register(IEventBus eventBus) {
		SCREEN_ELEMENT.register(eventBus);
		MENU_PROJECT.register(eventBus);
		MENU_PAGE.register(eventBus);
		LISTEN.register(eventBus);
	}
}
