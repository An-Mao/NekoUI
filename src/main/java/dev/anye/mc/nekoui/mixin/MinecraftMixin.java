package dev.anye.mc.nekoui.mixin;

import com.mojang.blaze3d.platform.Window;
import com.mojang.logging.LogUtils;
import dev.anye.mc.nekoui.config.Config;
import dev.anye.mc.nekoui.config.ban$screen.BanScreenConfig;
import net.minecraft.client.InputType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Unique
    private static final Logger nekoUI$LOGGER = LogUtils.getLogger();
    @Nullable
    @Shadow
    public ClientLevel level;
    @Nullable
    @Shadow
    public LocalPlayer player;
    @Nullable
    @Shadow
    public Screen screen;
    @Shadow
    private Thread gameThread;
    @Shadow
    private boolean clientLevelTeardownInProgress;
    @Shadow
    private InputType lastInputType;
    @Final
    @Shadow
    public MouseHandler mouseHandler;
    @Final
    @Shadow
    private Window window;
    @Shadow
    public boolean noRender;
    @Final
    @Shadow
    private SoundManager soundManager;

    @Shadow
    public abstract void updateTitle();

    @Inject(method = "setScreen" ,at = @At("HEAD"), cancellable = true)
    public void nekoui$setScreen$ban(Screen pGuiScreen, CallbackInfo ci){
        if (pGuiScreen != null) {
            String path = pGuiScreen.getClass().getName();
            if (Config.INSTANCE.getDatas().isOutputScreenPathName()) {
                nekoUI$LOGGER.info(path);
            }
            if (BanScreenConfig.I.isBan(path)) {
                ci.cancel();
            }
        }
    }
}
