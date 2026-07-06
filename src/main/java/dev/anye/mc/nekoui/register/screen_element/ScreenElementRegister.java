package dev.anye.mc.nekoui.register.screen_element;

import dev.anye.mc.cores.register.AutoRegister;
import dev.anye.mc.cores.register.Register;
import dev.anye.mc.nekoui.NekoUI;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforgespi.language.ModFileScanData;

import java.util.function.Supplier;

public class ScreenElementRegister {
	public static final Register<ScreenElement> SCREEN_ELEMENT_REGISTER = new Register<>(NekoUI.MOD_ID, "screen_element",builder -> builder.sync(false));
	//public static final AutoRegister AUTO_REGISTER = new AutoRegister(NekoUIScreenElement.class,ScreenElement.class, List.of(), _ -> true,ScreenElementRegister::register);


	/*
	static {
		AutoRegisterFactory.register(Identifier.fromNamespaceAndPath(NekoUI.MOD_ID,"screen_element"),NekoUIScreenElement.class,ScreenElement.class, _ -> true,ScreenElementRegister::register);

		//regFromConfig();
	}

	 */

	// public static void regFromConfig(){
	// 	_File.getFiles(Configs.ConfigDir_ScreenElement,".json").forEach(path -> {
	// 		String k = path.getFileName().toString();
	//         ScreenRender sr = new ScreenRenderIO(k).getDatas();
	// 		k = k.substring(0, k.length() - 5);
	//         if (sr != null && !REGISTRY.containsKey(Identifier.fromNamespaceAndPath(Cores.MOD_ID, k))) reg(k, () -> new ScreenElement(sr));
	//     });
	// }

	public static void register(Class<?> clazz, ModFileScanData.AnnotationData data){
		if (data.annotationData().get("name") instanceof String name && data.annotationData().get("modid") instanceof String modid) {
			SCREEN_ELEMENT_REGISTER.register(modid + "_" + name, () -> AutoRegister.simpleInstance(clazz,data));
		}
	}

	public static <I extends ScreenElement> DeferredHolder<ScreenElement, I> reg(String name, Supplier<? extends I> sup) {
		return SCREEN_ELEMENT_REGISTER.register(name, sup);
	}

	public static void register(IEventBus eventBus) {
		//AUTO_REGISTER.register();
		SCREEN_ELEMENT_REGISTER.register(eventBus);
	}
}
