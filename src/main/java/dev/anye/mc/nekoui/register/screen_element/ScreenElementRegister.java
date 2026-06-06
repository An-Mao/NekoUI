package dev.anye.mc.nekoui.register.screen_element;

import dev.anye.mc.nekoui.NekoUI;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class ScreenElementRegister {
    public static final Identifier KEY =  Identifier.fromNamespaceAndPath(NekoUI.MOD_ID, "screen_element");
    public static final ResourceKey<Registry<ScreenElement>> REGISTRY_KEY = ResourceKey.createRegistryKey(KEY);
    public static final Registry<ScreenElement> REGISTRY = new RegistryBuilder<>(REGISTRY_KEY)
            .sync(false)
            .create();
    public static final DeferredRegister<ScreenElement> SCREEN_ELEMENT = DeferredRegister.create(REGISTRY, NekoUI.MOD_ID);

    /*
	static {
		//regFromConfig();
	}
	public static void regFromConfig(){
		_File.getFiles(Configs.ConfigDir_ScreenElement,".json").forEach(path -> {
			String k = path.getFileName().toString();
            ScreenRender sr = new ScreenRenderIO(k).getDatas();
			k = k.substring(0, k.length() - 5);
            if (sr != null && !REGISTRY.containsKey(Identifier.fromNamespaceAndPath(Cores.MOD_ID, k))) reg(k, () -> new ScreenElement(sr));
        });
	}

     */


    public static <I extends ScreenElement> DeferredHolder<ScreenElement, I>  reg(String name, Supplier<? extends I> sup) {
        return SCREEN_ELEMENT.register(name, sup);
    }

    public static void register(IEventBus eventBus){
        SCREEN_ELEMENT.register(eventBus);
    }
}
