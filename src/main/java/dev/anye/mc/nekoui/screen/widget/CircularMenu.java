package dev.anye.mc.nekoui.screen.widget;

import dev.anye.mc.cores.screen.widget.CircularWidget;
import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
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

import java.util.List;

public class CircularMenu extends CircularWidget {
	public CircularMenu(int x, int y, int w, int h, Component message, DT_ListBoxData... data) {
		super(x, y, w, h, message, data);
	}

	public CircularMenu(int x, int y, int w, int h, Component message, List<DT_ListBoxData> data) {
		super(x, y, w, h, message, data);
	}

	public CircularMenu(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, Component message, DT_ListBoxData... data) {
		super(x, y, w, h, sectors, innerRadius, outerRadius, message, data);
	}

	public CircularMenu(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, Component message, List<DT_ListBoxData> data) {
		super(x, y, w, h, sectors, innerRadius, outerRadius, message, data);
	}

	public CircularMenu(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, int highlightColor, int normalColor, Component message, DT_ListBoxData... data) {
		super(x, y, w, h, sectors, innerRadius, outerRadius, highlightColor, normalColor, message, data);
	}

	public CircularMenu(int x, int y, int w, int h, int sectors, int innerRadius, int outerRadius, int highlightColor, int normalColor, Component message, List<DT_ListBoxData> data) {
		super(x, y, w, h, sectors, innerRadius, outerRadius, highlightColor, normalColor, message, data);
	}

	public void setData(List<DT_ListBoxData> data) {
		this.data = data;
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
					Item item = BuiltInRegistries.ITEM.get(Identifier.parse(parts[1])).get().value();
					guiGraphics.item(new ItemStack(item), -8, -8);
				}
				case "image" -> {
					Identifier res = Identifier.tryParse(parts[1]);
					if (res != null) {
						guiGraphics.blit(RenderPipelines.GUI_TEXTURED, res, -8, -8, 0, 0, 0, 16, 16, 16, 16);
					}
				}
			}
		} else {
			pose.scale(size, size);
			drawTextName(guiGraphics, name, color);
		}
		pose.popMatrix();
	}
}
