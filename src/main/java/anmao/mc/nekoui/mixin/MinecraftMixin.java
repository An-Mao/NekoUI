package anmao.mc.nekoui.mixin;

import anmao.mc.nekoui.config.Config;
import anmao.mc.nekoui.config.ban$screen.BanScreenConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Unique
    private static final Logger nekoUI$LOGGER = LogUtils.getLogger();

    @Inject(method = "setScreen" ,at = @At("HEAD"), cancellable = true)
    public void nekoui$setScreen$ban(Screen pGuiScreen, CallbackInfo ci){
        if (pGuiScreen != null) {
            String path = pGuiScreen.getClass().getName();
            if (Config.INSTANCE.getDatas().isOutputScreenPathName()) {
                nekoUI$LOGGER.info(path);
            }
            if (BanScreenConfig.isBan(path)) {
                ci.cancel();
            }
        }
    }
}
