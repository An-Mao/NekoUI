package dev.anye.mc.nekoui.render.element;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2fStack;

import javax.annotation.Nullable;

public record ImageRenderState(
		RenderPipeline pipeline,
		TextureSetup textureSetup,
		Matrix3x2fStack matrix4f,
		Identifier pAtlasLocation,
		int pX1,
		int pX2,
		int pY1,
		int pY2,
		int pBlitOffset,
		float pMinU,
		float pMaxU,
		float pMinV,
		float pMaxV,
		int color,
		@Nullable ScreenRectangle scissorArea,
		@Nullable ScreenRectangle bounds) implements GuiElementRenderState {
	@Override
	public void buildVertices(VertexConsumer vertexConsumer) {
		vertexConsumer.addVertexWith2DPose(matrix4f, pX1, pY1)
				.setUv(pMinU, pMinV)
				.setColor(color);
		vertexConsumer.addVertexWith2DPose(matrix4f, pX1, pY2)
				.setUv(pMinU, pMaxV)
				.setColor(color);
		vertexConsumer.addVertexWith2DPose(matrix4f, pX2, pY2)
				.setUv(pMaxU, pMaxV)
				.setColor(color);
		vertexConsumer.addVertexWith2DPose(matrix4f, pX2, pY1)
				.setUv(pMaxU, pMinV)
				.setColor(color);
	}
}
