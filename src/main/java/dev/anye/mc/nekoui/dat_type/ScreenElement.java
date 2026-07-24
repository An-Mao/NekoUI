package dev.anye.mc.nekoui.dat_type;

import com.mojang.logging.LogUtils;
import dev.anye.core.color._ColorSupport;
import dev.anye.core.format._FormatToString;
import dev.anye.core.javascript._JavaScript;
import dev.anye.core.javascript._NashornJS;
import dev.anye.core.system._File;
import dev.anye.mc.cores.component.ComponentStyle;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.player.PlayerInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3i;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ScreenElement {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final Map<String, ResourceLocation> Resources = new HashMap<>();
	private static final _JavaScript<?,?> JS = new _NashornJS(false);

	private final ScreenRender config;
	private int x;
	private int y;

	public ScreenElement(ScreenRender screenRender) {
		this.config = screenRender;
	}

	public void reSize(int screenWidth, int screenHeight) {
		x = switch (config.x()) {
			case "left" -> 0;
			case "center" -> screenWidth >> 1;
			case "right" -> screenWidth;
			default -> config.x().isEmpty() ? 0 : Integer.parseInt(config.x());
		};
		y = switch (config.y()) {
			case "top" -> 0;
			case "center" -> screenHeight >> 1;
			case "bottom" -> screenHeight;
			default -> config.y().isEmpty() ? 0 : Integer.parseInt(config.y());
		};
		x += config.pos().x;
		y += config.pos().y;
	}

	public void render(Minecraft minecraft, LocalPlayer localPlayer, GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
		config.elements().forEach(element -> {
			if (element.type().equals(ScreenRender.Type.ERROR)) return;
			Vector3i ePos = element.pos();
			int dx = this.x + ePos.x, dy = this.y + ePos.y;

			guiGraphics.pose().pushPose();
			guiGraphics.pose().translate(0,0, 0);
			String tmpS = "";
			switch (element.type()) {
				case IMAGE -> {
					ResourceLocation res = Resources.getOrDefault(element.key(), ResourceLocation.tryParse(element.key()));
					if (res != null)
						guiGraphics.blit(res, dx, dy, 0, 0, element.width(), element.height(), element.width(), element.height());
				}
				case SELF -> tmpS = element.key();
				case CUSTOM -> tmpS = PlayerInfo.getPlayerDat(element.key());
				case PLAYER_DATA -> tmpS = _FormatToString.formatValue(PlayerInfo.getPlayerAttribute(element.key()), 2);
				case JS -> {
					String key = element.key();
					tmpS = JS.addParameter("x", dx)
							.addParameter("y", dy)
							.addParameter("screenWidth", screenWidth)
							.addParameter("screenHeight", screenHeight)
							.addParameter("server", minecraft.getCurrentServer())
							.addParameter("player", localPlayer)
							.addParameter("playerNbt", localPlayer.getPersistentData())
							.addParameter("guiGraphics", guiGraphics)
							.addParameter("minecraft", minecraft)
							.runFile(key, _File.getFilePath(Configs.CONFIG_DIR_JS, key)).toString();
				}
				case SLOT ->
						guiGraphics.renderItem(localPlayer.getInventory().getItem(Integer.parseInt(element.key())), dx, dy);
			}
			if (!tmpS.isEmpty()) {
				String color = element.color();
				if (color.equalsIgnoreCase("rainbow")) {
					guiGraphics.drawString(minecraft.font, ComponentStyle.Flash(tmpS, minecraft.level.getGameTime()), dx, dy, 0xffffffff);
				} else {
					guiGraphics.drawString(minecraft.font, tmpS, dx, dy, _ColorSupport.HexToColor(color));
				}
			}
			guiGraphics.pose().popPose();
		});
	}

	public void reload() {
	}
}
