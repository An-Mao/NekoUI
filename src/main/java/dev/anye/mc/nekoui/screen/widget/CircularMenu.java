package dev.anye.mc.nekoui.screen.widget;

import com.mojang.logging.LogUtils;
import dev.anye.mc.cores.helper.item.ItemHelper;
import dev.anye.mc.cores.screen.widget.CircularWidget;
import dev.anye.mc.cores.screen.widget.SimpleListBoxData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2fStack;
import org.slf4j.Logger;

import java.util.List;

public class CircularMenu extends CircularWidget {
	private static final Logger LOGGER = LogUtils.getLogger();
	public CircularMenu(int x, int y, int w, int h, Component message, SimpleListBoxData... data) {
		super(x, y, w, h, message, data);
	}

	public CircularMenu(int x, int y, int w, int h, Component message, List<SimpleListBoxData> data) {
		super(x, y, w, h, message, data);
	}

	public CircularMenu(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, Component message, SimpleListBoxData... data) {
		super(x, y, w, h, sectors, innerRadius, outerRadius, message, data);
	}

	public CircularMenu(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, Component message, List<SimpleListBoxData> data) {
		super(x, y, w, h, sectors, innerRadius, outerRadius, message, data);
	}

	public CircularMenu(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, int highlightColor, int normalColor, Component message, SimpleListBoxData... data) {
		super(x, y, w, h, sectors, innerRadius, outerRadius, highlightColor, normalColor, message, data);
	}

	public CircularMenu(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, int highlightColor, int normalColor, Component message, List<SimpleListBoxData> data) {
		super(x, y, w, h, sectors, innerRadius, outerRadius, highlightColor, normalColor, message, data);
	}



	@Override
	protected void drawName(GuiGraphicsExtractor guiGraphics, double rad, String name, int color, float size) {
		Matrix3x2fStack pose = guiGraphics.pose();
		pose.pushMatrix();
		String[] parts = name.split("\\?");
		if (parts.length == 2) {
			//pose.mulPose(Axis.ZP.rotation((float) -rad));
			double r = getFanTextInnerSpace() + (outerRadius - innerRadius) / 2d;
			double x = Math.cos(rad) * r;
			double y = Math.sin(rad) * r;
			pose.translate((float) x, (float) y);
			pose.scale(size, size);
			switch (parts[0]) {
				case "slot" -> {
					LocalPlayer player = Minecraft.getInstance().player;
					if (player != null) {
						ItemStack item = player.getInventory().getItem(Integer.parseInt(parts[1]));
						guiGraphics.item(item, -8, -8);
						guiGraphics.itemDecorations(font, item, -8, -8);
					}
				}
				case "item" -> {
					Item item = ItemHelper.getItem(parts[1]);
					guiGraphics.item(new ItemStack(item), -8, -8);
				}
				case "image" -> {
					Identifier res = Identifier.tryParse(parts[1]);
					if (res != null) {
						guiGraphics.blit(RenderPipelines.GUI_TEXTURED, res,
								-8, -8,
								0, 0,
								0, 16,
								16, 16,
								16);
					}
				}
				case null, default -> LOGGER.warn("unknow type {}",parts[0]);
			}
		} else {
			pose.scale(size, size);
			drawTextName(guiGraphics, name, color);
		}
		pose.popMatrix();
	}
}
