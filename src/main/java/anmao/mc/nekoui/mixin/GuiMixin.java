package anmao.mc.nekoui.mixin;

import anmao.mc.nekoui.config.Config;
import anmao.mc.nekoui.config.hotbar.HotBarConfig;
import anmao.mc.nekoui.gui.MobDirectionGui;
import anmao.mc.nekoui.gui.ScreenElementGui;
import anmao.mc.nekoui.gui.hot$bar.HotBarGui;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Final
    @Shadow
    private LayeredDraw layers;
    @Inject(method = "<init>", at = @At("RETURN"))
    public void nekoui$gui(Minecraft pMinecraft, CallbackInfo ci){
        ScreenElementGui nekoUI$screenElementGui = new ScreenElementGui(pMinecraft);
        MobDirectionGui nekoUI$MobDirectionGui = new MobDirectionGui(pMinecraft);
        HotBarGui nekoUI$HotBarGui = new HotBarGui(pMinecraft);
        LayeredDraw seg = new LayeredDraw()
                .add(nekoUI$screenElementGui::render)
                .add(nekoUI$MobDirectionGui::render)
                .add(nekoUI$HotBarGui::render);
        layers.add(seg, () -> !pMinecraft.options.hideGui);
    }
    @Inject(method = "renderHotbarAndDecorations", at = @At("HEAD"), cancellable = true)
    private void nekoui$renderHotbarAndDecorations$ban(GuiGraphics p_333625_, DeltaTracker p_344796_, CallbackInfo ci){
        if(HotBarConfig.INSTANCE.getDatas().isEnable()){
            ci.cancel();
        }
    }
    @Inject(method = "renderExperienceLevel", at = @At("HEAD"), cancellable = true)
    private void nekoui$renderExperienceLevel$ban(GuiGraphics p_335340_, DeltaTracker p_344840_, CallbackInfo ci){
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
