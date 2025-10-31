package dev.anye.mc.nekoui.mixin;

import dev.anye.mc.nekoui.config.Config;
import dev.anye.mc.nekoui.config.hotbar.HotBarConfig;
import dev.anye.mc.nekoui.config.mob$direction.MobDirectionConfig;
import dev.anye.mc.nekoui.gui.MobDirectionGui;
import dev.anye.mc.nekoui.gui.ScreenElementGui;
import dev.anye.mc.nekoui.gui.hot$bar.HotBarGui;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow @Final private Minecraft minecraft;

    @Shadow @Final private SpectatorGui spectatorGui;

    @Shadow protected abstract void renderItemHotbar(GuiGraphics pGuiGraphics, DeltaTracker pDeltaTracker);

    @Inject(method = "render", at = @At("HEAD"))
    private void nekoui$render$add(GuiGraphics pGuiGraphics, DeltaTracker pDeltaTracker, CallbackInfo ci){
        if(Config.INSTANCE.getDatas().isRenderScreenElement()){
            ScreenElementGui.render(pGuiGraphics,pDeltaTracker);
        }
        if (HotBarConfig.INSTANCE.getDatas().isEnable()) {
            HotBarGui.render(pGuiGraphics,pDeltaTracker);
        }
        if (MobDirectionConfig.I.getDatas().isEnable()) {
            MobDirectionGui.render(pGuiGraphics,pDeltaTracker);
        }
    }
    @Inject(method = "renderHotbarAndDecorations", at = @At("HEAD"), cancellable = true)
    private void nekoui$renderExperienceLevel$ban(GuiGraphics pGuiGraphics, DeltaTracker pDeltaTracker, CallbackInfo ci){
        if(HotBarConfig.INSTANCE.getDatas().isEnable()){
            ci.cancel();
        }else {
            if (this.minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR) {
                this.spectatorGui.renderHotbar(pGuiGraphics);
            } else {
                this.renderItemHotbar(pGuiGraphics, pDeltaTracker);
            }
        }
        if(Config.INSTANCE.getDatas().isRenderScreenElement()){
            ci.cancel();
        }
    }
    @Inject(method = "renderPlayerHealth", at = @At("HEAD"), cancellable = true)
    private void nekoui$renderPlayerHealth$ban(GuiGraphics pGuiGraphics, CallbackInfo ci){
        if(Config.INSTANCE.getDatas().isRenderScreenElement()){
            ci.cancel();
        }
    }
    @Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
    private static void nekoui$renderArmor$ban(GuiGraphics pGuiGraphics, Player pPlayer, int pY, int pHeartRows, int pHeight, int pX, CallbackInfo ci){
        if(Config.INSTANCE.getDatas().isRenderScreenElement()){
            ci.cancel();
        }
    }
    @Inject(method = "renderHearts", at = @At("HEAD"), cancellable = true)
    private void nekoui$renderHearts$ban(GuiGraphics pGuiGraphics, Player pPlayer, int pX, int pY, int pHeight, int pOffsetHeartIndex, float pMaxHealth, int pCurrentHealth, int pDisplayHealth, int pAbsorptionAmount, boolean pRenderHighlight, CallbackInfo ci){
        if(Config.INSTANCE.getDatas().isRenderScreenElement()){
            ci.cancel();
        }
    }
    @Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
    private void nekoui$renderFood$ban(GuiGraphics pGuiGraphics, Player pPlayer, int pY, int pX, CallbackInfo ci){
        if(Config.INSTANCE.getDatas().isRenderScreenElement()){
            ci.cancel();
        }
    }
}
