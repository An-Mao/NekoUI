package dev.anye.mc.nekoui.register.screen_element;

import dev.anye.core.color._ColorSupport;
import dev.anye.core.format._FormatToString;
import dev.anye.core.system._File;
import dev.anye.mc.cores.helper.component.ComponentStyle;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.dat_type.ScreenRender;
import dev.anye.mc.nekoui.other.CJS;
import dev.anye.mc.nekoui.player.PlayerInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.joml.Vector3i;

import java.util.HashMap;

public class ScreenElement {
	public static final HashMap<String, Identifier> Resources = new HashMap<>();
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

	public void render(Minecraft minecraft, LocalPlayer localPlayer, GuiGraphicsExtractor guiGraphics, int screenWidth, int screenHeight) {
		config.elements().forEach(element -> {
			Vector3i ePos = element.pos();
			int dx = this.x + ePos.x, dy = this.y + ePos.y;
			guiGraphics.pose().pushMatrix();
			guiGraphics.pose().translate(0, 0);
			String tmpS = "";
			switch (element.type()) {
				case Image -> {
					Identifier res = Resources.getOrDefault(element.key(), Identifier.tryParse(element.key()));
					if (res != null)
						guiGraphics.blit(RenderPipelines.GUI_TEXTURED, res, dx, dy, 0, 0, element.width(), element.height(), element.width(), element.height());
				}
				case Self -> tmpS = element.key();
				case Custom -> tmpS = PlayerInfo.getPlayerDat(element.key());
				case PlayerData -> tmpS = _FormatToString.formatValue(PlayerInfo.getPlayerAttribute(element.key()), 2);
				case Js -> {
					String key = element.key();
					tmpS = CJS.E.addParameter("x", dx)
							.addParameter("y", dy)
							.addParameter("screenWidth", screenWidth)
							.addParameter("screenHeight", screenHeight)
							.addParameter("server", minecraft.getCurrentServer())
							.addParameter("player", localPlayer)
							.addParameter("playerNbt", localPlayer.getPersistentData())
							.addParameter("guiGraphics", guiGraphics)
							.addParameter("minecraft", minecraft)
							.runFile(key, _File.getFilePath(Configs.ConfigDir_JS, key)).toString();
				}
				case Slot ->
						guiGraphics.item(localPlayer.getInventory().getItem(Integer.parseInt(element.key())), dx, dy);
			}
			if (!tmpS.isEmpty()) {
				String color = element.color();
				ComponentStyle.RainbowType rainbowType = ComponentStyle.RainbowType.GetByName(color);
				if (rainbowType != null) {
					guiGraphics.text(minecraft.font, ComponentStyle.Flash(tmpS, rainbowType), dx, dy, 0xffffffff);
				} else {
					guiGraphics.text(minecraft.font, tmpS, dx, dy, _ColorSupport.HexToColor(color));
				}
			}
			guiGraphics.pose().popMatrix();
		});
	}

	public void reload() {
	}

	;
}
