package nws.mc.nekoui.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class RenderSupport {
    public static void renderWarpedImage(PoseStack poseStack, ResourceLocation texture, float x, float y, float width, float height, float angle) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        Matrix4f matrix = poseStack.last().pose();
        drawWarpedPlaneZ(matrix, width, height, 20, 20, angle);
        poseStack.popPose();
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
    }
    public static void drawWarpedPlane(Matrix4f matrix, float width, float height, int segmentsX, int segmentsY, float angle) {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder builder = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        for (int i = 0; i < segmentsX; i++) {
            for (int j = 0; j < segmentsY; j++) {
                float x1 = (float) i / segmentsX * width;
                float y1 = (float) j / segmentsY * height;
                float x2 = (float) (i + 1) / segmentsX * width;
                float y2 = (float) (j + 1) / segmentsY * height;
                double v = Math.sin(x1 / width * Math.PI) * angle;
                float warpedY1 = (float) (y1 + v);
                double v3 = Math.sin(x2 / width * Math.PI) * angle;
                float warpedY2 = (float) (y1 + v3);
                float warpedY3 = (float) (y2 + v);
                float warpedY4 = (float) (y2 + v3);

                float u1 = (float) i / segmentsX;
                float v1 = (float) j / segmentsY;
                float u2 = (float) (i + 1) / segmentsX;
                float v2 = (float) (j + 1) / segmentsY;
                builder.addVertex(matrix, x1, warpedY1, 0).setUv(u1, v1);
                builder.addVertex(matrix, x2, warpedY2, 0).setUv(u2, v1);
                builder.addVertex(matrix, x2, warpedY4, 0).setUv(u2, v2);
                builder.addVertex(matrix, x1, warpedY3, 0).setUv(u1, v2);
            }
        }
        BufferUploader.drawWithShader(builder.buildOrThrow());
    }
    public static void drawWarpedPlaneX(Matrix4f matrix, float width, float height, int segmentsX, int segmentsY, float angle) {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder builder = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        for (int i = 0; i < segmentsX; i++) {
            for (int j = 0; j < segmentsY; j++) {
                float x1 = (float) i / segmentsX * width;
                float y1 = (float) j / segmentsY * height;
                float x2 = (float) (i + 1) / segmentsX * width;
                float y2 = (float) (j + 1) / segmentsY * height;

                // 计算弯曲后的 X 坐标
                float warpedX1 = (float) (x1 + Math.sin(y1 / height * Math.PI) * angle);
                float warpedX2 = (float) (x1 + Math.sin(y2 / height * Math.PI) * angle);
                float warpedX3 = (float) (x2 + Math.sin(y1 / height * Math.PI) * angle);
                float warpedX4 = (float) (x2 + Math.sin(y2 / height * Math.PI) * angle);


                float u1 = (float) i / segmentsX;
                float v1 = (float) j / segmentsY;
                float u2 = (float) (i + 1) / segmentsX;
                float v2 = (float) (j + 1) / segmentsY;

                builder.addVertex(matrix, warpedX1, y1, 0).setUv(u1, v1);
                builder.addVertex(matrix, warpedX3, y1, 0).setUv(u2, v1);
                builder.addVertex(matrix, warpedX4, y2, 0).setUv(u2, v2);
                builder.addVertex(matrix, warpedX2, y2, 0).setUv(u1, v2);
            }
        }

        BufferUploader.drawWithShader(builder.buildOrThrow());
    }
    public static void drawWarpedPlaneZ(Matrix4f matrix, float width, float height, int segmentsX, int segmentsY, float angle) {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder builder = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        for (int i = 0; i < segmentsX; i++) {
            for (int j = 0; j < segmentsY; j++) {
                float x1 = (float) i / segmentsX * width;
                float y1 = (float) j / segmentsY * height;
                float x2 = (float) (i + 1) / segmentsX * width;
                float y2 = (float) (j + 1) / segmentsY * height;

                // 计算弯曲后的 Z 坐标
                float warpedZ1 = (float) (Math.sin(x1 / width * Math.PI) * angle);
                float warpedZ2 = (float) (Math.sin(x2 / width * Math.PI) * angle);
                float warpedZ3 = (float) (Math.sin(x1 / width * Math.PI) * angle);
                float warpedZ4 = (float) (Math.sin(x2 / width * Math.PI) * angle);

                float u1 = (float) i / segmentsX;
                float v1 = (float) j / segmentsY;
                float u2 = (float) (i + 1) / segmentsX;
                float v2 = (float) (j + 1) / segmentsY;

                builder.addVertex(matrix, x1, y1, warpedZ1).setUv(u1, v1);
                builder.addVertex(matrix, x2, y1, warpedZ2).setUv(u2, v1);
                builder.addVertex(matrix, x2, y2, warpedZ4).setUv(u2, v2);
                builder.addVertex(matrix, x1, y2, warpedZ3).setUv(u1, v2);
            }
        }

        BufferUploader.drawWithShader(builder.buildOrThrow());
    }
}
