package dev.anye.mc.nekoui.mixin;

import com.mojang.logging.LogUtils;
import dev.anye.mc.nekoui.config.Config;
import dev.anye.mc.nekoui.config.ban_screen.BanScreenConfig;
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

	@Inject(method = "setScreenAndShow", at = @At("HEAD"), cancellable = true)
	public void nekoui$setScreenAndShow$ban(Screen pGuiScreen, CallbackInfo ci) {
		if (pGuiScreen != null) {
			String path = pGuiScreen.getClass().getName();
			if (Config.INSTANCE.getData().isOutputScreenPathName()) {
				nekoUI$LOGGER.info(path);
			}
			if (BanScreenConfig.I.isBan(path)) {
				ci.cancel();
			}
		}
	}
}
