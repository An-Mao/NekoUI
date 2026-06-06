package dev.anye.mc.nekoui.register.menu_page;

import dev.anye.mc.nekoui.NekoUI;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class MenuPageRegister {
    public static final Identifier KEY =  Identifier.fromNamespaceAndPath(NekoUI.MOD_ID, "menu_page");
    public static final ResourceKey<Registry<MenuPage>> REGISTRY_KEY = ResourceKey.createRegistryKey(KEY);
    public static final Registry<MenuPage> REGISTRY = new RegistryBuilder<>(REGISTRY_KEY)
            .sync(false)
            .create();
    public static final DeferredRegister<MenuPage> SCREEN_ELEMENT = DeferredRegister.create(REGISTRY, NekoUI.MOD_ID);

    public static <I extends MenuPage> DeferredHolder<MenuPage, I>  reg(String name, Supplier<? extends I> sup) {
        return SCREEN_ELEMENT.register(name, sup);
    }
    public static void register(IEventBus eventBus){
        SCREEN_ELEMENT.register(eventBus);
    }
}
