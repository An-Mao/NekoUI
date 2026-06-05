package dev.anye.mc.nekoui.register.screen_element;

import dev.anye.mc.cores.Cores;
import dev.anye.mc.nekoui.NekoUI;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class ScreenElementRegister {
    public static final Identifier KEY =  Identifier.tryBuild(NekoUI.MOD_ID, "screen_element");
    public static final ResourceKey<Registry<ScreenElement>> REGISTRY_KEY = ResourceKey.createRegistryKey(KEY);
    public static final Registry<ScreenElement> REGISTRY = new RegistryBuilder<>(REGISTRY_KEY)
            .sync(false)
            .create();
    public static final DeferredRegister<ScreenElement> SCREEN_ELEMENT = DeferredRegister.create(REGISTRY, NekoUI.MOD_ID);

}
