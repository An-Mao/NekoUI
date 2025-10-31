package dev.anye.mc.nekoui.mixin;

import dev.anye.mc.nekoui.config.Config;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ContextualBarRenderer.class)
public interface ContextualBarRendererMixin {

    @Inject(method = "renderExperienceLevel", at = @At("HEAD"), cancellable = true)
    private static void nekoui$renderExperienceLevel$disable(GuiGraphics pGuiGraphics, Font pFont, int pLevel, CallbackInfo ci){
        if(Config.INSTANCE.getDatas().isRenderScreenElement()){
            ci.cancel();
        }
    }
}
