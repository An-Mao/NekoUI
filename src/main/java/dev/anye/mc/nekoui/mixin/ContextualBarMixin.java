package dev.anye.mc.nekoui.mixin;

import dev.anye.mc.nekoui.config.Config;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ContextualBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ContextualBar.class,remap = false)
public interface ContextualBarMixin {

    @Inject(method = "extractExperienceLevel", at = @At("HEAD"), cancellable = true)
    private static void nekoui$renderExperienceLevel$disable(GuiGraphicsExtractor graphics, Font font, int experienceLevel, CallbackInfo ci){
        if(Config.INSTANCE.getData().isRenderScreenElement()){
            ci.cancel();
        }
    }
}
