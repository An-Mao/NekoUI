package dev.anye.mc.nekoui.gui;

import dev.anye.core.system._File;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.register.Registers;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;

public class ScreenElementGui {
	public static final Identifier KEY = Identifier.fromNamespaceAndPath(NekoUI.MOD_ID, "screen_element");
	private static int screenWidthCache = 0, screenHeightCache = 0;

	public static void render(GuiGraphicsExtractor guiGraphics, DeltaTracker pPartialTick) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.gui.hud.isHidden()) return;
		Level clientLevel = minecraft.level;
		LocalPlayer localPlayer = minecraft.player;
		if (clientLevel != null && clientLevel.isClientSide() && localPlayer != null) {
			int screenHeight = minecraft.getWindow().getGuiScaledHeight();
			int screenWidth = minecraft.getWindow().getGuiScaledWidth();

			boolean needResize = screenWidth != screenWidthCache || screenHeight != screenHeightCache;
			if (needResize) {
				screenWidthCache = screenWidth;
				screenHeightCache = screenHeight;
			}
			Configs.ScreenRenders.forEach(screenElement -> {
				if (needResize) screenElement.reSize(screenWidth, screenHeight);
				screenElement.render(minecraft, localPlayer, guiGraphics, screenWidth, screenHeight);
			});

			Registers.SCREEN_ELEMENT.getRegistry().forEach(screenElement -> {
				if (needResize) screenElement.reSize(screenWidth, screenHeight);
				screenElement.render(minecraft, localPlayer, guiGraphics, screenWidth, screenHeight);
			});
		}
	}

	public static void clearCache() {
		screenWidthCache = 0;
		screenHeightCache = 0;
	}


	private static final HashMap<String, Reader> fileTemp = new HashMap<>();

	public static Reader getJsFile(String name) throws FileNotFoundException {
		if (!fileTemp.containsKey(name))
			fileTemp.put(name, new FileReader(_File.getFilePath(Configs.ConfigDir_JS, name)));
		return fileTemp.get(name);
	}

}
