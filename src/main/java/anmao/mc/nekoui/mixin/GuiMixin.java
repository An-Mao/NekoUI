package anmao.mc.nekoui.mixin;

import anmao.mc.nekoui.NekoUI;
import anmao.mc.nekoui.config.Config;
import anmao.mc.nekoui.config.hotbar.HotBarConfig;
import anmao.mc.nekoui.gui.MobDirectionGui;
import anmao.mc.nekoui.gui.ScreenElementGui;
import anmao.mc.nekoui.gui.hot$bar.HotBarGui;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.GuiLayerManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Unique
    private final ResourceLocation nekoUI_NeoForge$screenElement =ResourceLocation.tryBuild(NekoUI.MOD_ID,"hud/screen_element");
    @Unique
    private final ResourceLocation nekoUI_NeoForge$mobDirection = ResourceLocation.tryBuild(NekoUI.MOD_ID,"hud/mob_direction");
    @Unique
    private final ResourceLocation nekoUI_NeoForge$hotBar = ResourceLocation.tryBuild(NekoUI.MOD_ID,"hud/hotbar");
    @Final
    @Shadow
    private GuiLayerManager layerManager;
    @Inject(method = "<init>", at = @At("RETURN"))
    public void nekoui$gui(Minecraft pMinecraft, CallbackInfo ci){
        ScreenElementGui nekoUI$screenElementGui = new ScreenElementGui(pMinecraft);
        MobDirectionGui nekoUI$MobDirectionGui = new MobDirectionGui(pMinecraft);
        HotBarGui nekoUI$HotBarGui = new HotBarGui(pMinecraft);
        GuiLayerManager seg = new GuiLayerManager()
                .add(nekoUI_NeoForge$screenElement,nekoUI$screenElementGui::render)
                .add(nekoUI_NeoForge$mobDirection,nekoUI$MobDirectionGui::render)
                .add(nekoUI_NeoForge$hotBar,nekoUI$HotBarGui::render);
        layerManager.add(seg, () -> !pMinecraft.options.hideGui);
    }
    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    private void nekoui$renderHotbar$ban(GuiGraphics p_316628_, DeltaTracker p_348543_, CallbackInfo ci){
        if(HotBarConfig.INSTANCE.getDatas().isEnable()){
            ci.cancel();
        }
    }
    @Inject(method = "renderExperienceLevel", at = @At("HEAD"), cancellable = true)
    private void nekoui$renderExperienceLevel$ban(GuiGraphics p_320582_, DeltaTracker p_348622_, CallbackInfo ci){
        if(Config.INSTANCE.getDatas().isRenderScreenElement()){
            ci.cancel();
        }
    }
    @Inject(method = "renderHealthLevel", at = @At("HEAD"), cancellable = true)
    private void nekoui$renderHealthLevel$ban(GuiGraphics pGuiGraphics, CallbackInfo ci){
        if(Config.INSTANCE.getDatas().isRenderScreenElement()){
            ci.cancel();
        }
    }
    @Inject(method = "renderArmorLevel", at = @At("HEAD"), cancellable = true)
    private void nekoui$renderArmorLevel$ban(GuiGraphics pGuiGraphics, CallbackInfo ci){
        if(Config.INSTANCE.getDatas().isRenderScreenElement()){
            ci.cancel();
        }
    }
    @Inject(method = "renderFoodLevel", at = @At("HEAD"), cancellable = true)
    private void nekoui$renderFoodLevel$ban(GuiGraphics pGuiGraphics, CallbackInfo ci){
        if(Config.INSTANCE.getDatas().isRenderScreenElement()){
            ci.cancel();
        }
    }
    @Inject(method = "renderAirLevel", at = @At("HEAD"), cancellable = true)
    private void nekoui$renderAirLevel$ban(GuiGraphics pGuiGraphics, CallbackInfo ci){
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
    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private void nekoui$renderExperienceBar$ban(GuiGraphics pGuiGraphics, int pX, CallbackInfo ci){
        if(Config.INSTANCE.getDatas().isRenderScreenElement()){
            ci.cancel();
        }
    }
}
