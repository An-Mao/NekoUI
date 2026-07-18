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
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Hud.class,remap = false)
public abstract class HudMixin {
    @Shadow @Final private Minecraft minecraft;

    @Shadow @Final private SpectatorGui spectatorGui;

    @Shadow
    public abstract void extractItemHotbar(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker);

    @Inject(method = "extractRenderState", at = @At("HEAD"))
    private void nekoui$extractRenderState$add(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci){
        if(Config.INSTANCE.getData().isRenderScreenElement()){
            ScreenElementGui.render(graphics, deltaTracker);
        }
        if (HotBarConfig.INSTANCE.getData().isEnable()) {
            HotBarGui.render(graphics, deltaTracker);
        }
        if (MobDirectionConfig.I.getData().isEnable()) {
            MobDirectionGui.render(graphics, deltaTracker);
        }
    }
    @Inject(method = "extractHotbarAndDecorations", at = @At("HEAD"), cancellable = true)
    private void nekoui$extractHotbarAndDecorations$ban(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci){
        if(HotBarConfig.INSTANCE.getData().isEnable()){
            ci.cancel();
        }else {
            if (this.minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR) {
                this.spectatorGui.extractHotbar(graphics);
            } else {
                this.extractItemHotbar(graphics, deltaTracker);
            }
        }
        if(Config.INSTANCE.getData().isRenderScreenElement()){
            ci.cancel();
        }
    }
    @Inject(method = "extractPlayerHealth", at = @At("HEAD"), cancellable = true)
    private void nekoui$extractPlayerHealth$ban(GuiGraphicsExtractor graphics, CallbackInfo ci){
        if(Config.INSTANCE.getData().isRenderScreenElement()){
            ci.cancel();
        }
    }
    @Inject(method = "extractArmor", at = @At("HEAD"), cancellable = true)
    private static void nekoui$extractArmor$ban(GuiGraphicsExtractor graphics, Player player, int yLineBase, int numHealthRows, int healthRowHeight, int xLeft, CallbackInfo ci){
        if(Config.INSTANCE.getData().isRenderScreenElement()){
            ci.cancel();
        }
    }
    @Inject(method = "extractHearts", at = @At("HEAD"), cancellable = true)
    private void nekoui$extractHearts$ban(GuiGraphicsExtractor graphics, Player player, int xLeft, int yLineBase, int healthRowHeight, int heartOffsetIndex, float maxHealth, int currentHealth, int oldHealth, int absorption, boolean blink, CallbackInfo ci){
        if(Config.INSTANCE.getData().isRenderScreenElement()){
            ci.cancel();
        }
    }
    @Inject(method = "extractFood", at = @At("HEAD"), cancellable = true)
    private void nekoui$extractFood$ban(GuiGraphicsExtractor graphics, Player player, int yLineBase, int xRight, CallbackInfo ci){
        if(Config.INSTANCE.getData().isRenderScreenElement()){
            ci.cancel();
        }
    }
}
