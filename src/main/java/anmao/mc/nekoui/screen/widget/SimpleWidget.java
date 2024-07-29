package anmao.mc.nekoui.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;

public class SimpleWidget {
    public static void drawRoundedRect(int x, int y, int width, int height, int radius, int color) {
        // Enable blending for transparency support
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // Set the color for the rectangle
        float alpha = (color >> 24 & 255) / 255.0F;
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        RenderSystem.setShaderColor(red, green, blue, alpha);

        // Start drawing the shape
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder builder = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        // Draw the rectangle with rounded corners
        // Top-left corner
        drawCircleSegment(builder, x + radius, y + radius, radius, 180, 270);
        // Top-right corner
        drawCircleSegment(builder, x + width - radius, y + radius, radius, 270, 360);
        // Bottom-right corner
        drawCircleSegment(builder, x + width - radius, y + height - radius, radius, 0, 90);
        // Bottom-left corner
        drawCircleSegment(builder, x + radius, y + height - radius, radius, 90, 180);

        // Fill the center and sides
        fillRect(builder, x + radius, y, width - 2 * radius, height); // Middle
        fillRect(builder, x, y + radius, radius, height - 2 * radius); // Left side
        fillRect(builder, x + width - radius, y + radius, radius, height - 2 * radius); // Right side
        fillRect(builder, x + radius, y, width - 2 * radius, radius); // Top side
        fillRect(builder, x + radius, y + height - radius, width - 2 * radius, radius); // Bottom side

        BufferUploader.drawWithShader(builder.buildOrThrow());
        //tessellator.end();

        // Disable blending
        RenderSystem.disableBlend();
    }

    private static void drawCircleSegment(VertexConsumer builder, float cx, float cy, float radius, float startAngle, float endAngle) {
        int segments = 360;
        double startRadians = Math.toRadians(startAngle);
        double endRadians = Math.toRadians(endAngle);
        double angleIncrement = (endRadians - startRadians) / segments;

        for (int i = 0; i < segments; i++) {
            double angle1 = startRadians + i * angleIncrement;
            double angle2 = startRadians + (i + 1) * angleIncrement;

            float x1 = cx + (float) (Math.cos(angle1) * radius);
            float y1 = cy + (float) (Math.sin(angle1) * radius);
            float x2 = cx + (float) (Math.cos(angle2) * radius);
            float y2 = cy + (float) (Math.sin(angle2) * radius);

            builder.addVertex(cx, cy, 0.0f);
            builder.addVertex(x1, y1, 0.0f);
            builder.addVertex(x2, y2, 0.0f);
        }
    }

    private static void fillRect(VertexConsumer builder, float x, float y, float width, float height) {
        builder.addVertex(x, y + height, 0.0f);
        builder.addVertex(x + width, y + height, 0.0f);
        builder.addVertex(x + width, y, 0.0f);
        builder.addVertex(x, y, 0.0f);
    }
}
