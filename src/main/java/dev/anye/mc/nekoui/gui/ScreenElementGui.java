package dev.anye.mc.nekoui.gui;

import dev.anye.core.system._File;
import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.gui.overlay.ForgeGui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;

public class ScreenElementGui {
	public static final ResourceLocation KEY = new ResourceLocation(NekoUI.MOD_ID, "screen_element");
	private static int screenWidthCache = 0;
	private static int screenHeightCache = 0;

	private ScreenElementGui(){}


	public static void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float v, int i, int i1) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.options.hideGui) return;
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
			Configs.SCREEN_RENDERS.forEach(screenElement -> {
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
			fileTemp.put(name, new FileReader(_File.getFilePath(Configs.CONFIG_DIR_JS, name)));
		return fileTemp.get(name);
	}
}
