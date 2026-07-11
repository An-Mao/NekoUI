package dev.anye.mc.nekoui.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;

public class RenderSupport {
	private RenderSupport(){}

	public static void image(VertexConsumer vertexConsumer, Matrix4f matrix4f,
	                         int x,
	                         int y,
	                         float uOffset,
	                         float vOffset,
	                         int uWidth,
	                         int vHeight,
	                         int textureWidth,
	                         int textureHeight, float z, int packedLight
	) {
		image(vertexConsumer, matrix4f, x, y, uOffset, vOffset, uWidth, vHeight, uWidth, vHeight, textureWidth, textureHeight, z, packedLight);
	}

	public static void image(VertexConsumer vertexConsumer, Matrix4f matrix4f,
	                         int x,
	                         int y,
	                         float uOffset,
	                         float vOffset,
	                         int uWidth,
	                         int vHeight,
	                         int width,
	                         int height,
	                         int textureWidth,
	                         int textureHeight, float z, int packedLight
	) {
		image(vertexConsumer, matrix4f, x, y, uOffset, vOffset, uWidth, vHeight, width, height, textureWidth, textureHeight, z, -1, packedLight);
	}

	public static void image(VertexConsumer vertexConsumer, Matrix4f matrix4f,
	                         int x,
	                         int y,
	                         float uOffset,
	                         float vOffset,
	                         int uWidth,
	                         int vHeight,
	                         int width,
	                         int height,
	                         int textureWidth,
	                         int textureHeight, float z,
	                         int color, int packedLight
	) {
		toImage(
				vertexConsumer,
				matrix4f,
				x,
				x + uWidth,
				y,
				y + vHeight,
				(uOffset + 0.0F) / textureWidth,
				(uOffset + width) / textureWidth,
				(vOffset + 0.0F) / textureHeight,
				(vOffset + height) / textureHeight,
				z,
				color, packedLight
		);
	}

	public static void toImage(VertexConsumer vertexConsumer, Matrix4f matrix4f, float x1, float x2, float y1, float y2, float minU, float maxU, float minV, float maxV, float z, int color, int packedLight) {

		vertexConsumer.addVertex(matrix4f, x1, y1, z)
				.setUv(minU, minV)
				.setColor(color).setLight(packedLight);
		vertexConsumer.addVertex(matrix4f, x1, y2, z)
				.setUv(minU, maxV)
				.setColor(color).setLight(packedLight);
		vertexConsumer.addVertex(matrix4f, x2, y2, z)
				.setUv(maxU, maxV)
				.setColor(color).setLight(packedLight);
		vertexConsumer.addVertex(matrix4f, x2, y1, z)
				.setUv(maxU, minV)
				.setColor(color).setLight(packedLight);
	}
}
