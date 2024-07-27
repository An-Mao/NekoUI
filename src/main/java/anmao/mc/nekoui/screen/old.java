package anmao.mc.nekoui.screen;

public class old {
/*
    private void drawRoundedRect(int e,int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
        // Setup render state

        Tesselator tessellator = Tesselator.getInstance();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder buffer = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        // Draw fill
        //buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        addVertex(buffer, x + radius, y + radius, fillColor);
        addVertex(buffer, x + width - radius, y + radius, fillColor);
        addVertex(buffer, x + width - radius, y + height - radius, fillColor);
        addVertex(buffer, x + radius, y + height - radius, fillColor);
        //tessellator.end();
        BufferUploader.drawWithShader(buffer.buildOrThrow());

        // Draw borders
        buffer = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        // Top border
        addVertex(buffer, x + radius, y, borderColor);
        addVertex(buffer, x + width - radius, y, borderColor);
        addVertex(buffer, x + width - radius, y + radius, borderColor);
        addVertex(buffer, x + radius, y + radius, borderColor);
        // Bottom border
        addVertex(buffer, x + radius, y + height - radius, borderColor);
        addVertex(buffer, x + width - radius, y + height - radius, borderColor);
        addVertex(buffer, x + width - radius, y + height, borderColor);
        addVertex(buffer, x + radius, y + height, borderColor);
        // Left border
        addVertex(buffer, x, y + radius, borderColor);
        addVertex(buffer, x + radius, y + radius, borderColor);
        addVertex(buffer, x + radius, y + height - radius, borderColor);
        addVertex(buffer, x, y + height - radius, borderColor);
        // Right border
        addVertex(buffer, x + width - radius, y + radius, borderColor);
        addVertex(buffer, x + width, y + radius, borderColor);
        addVertex(buffer, x + width, y + height - radius, borderColor);
        addVertex(buffer, x + width - radius, y + height - radius, borderColor);
        //tessellator.end();
        BufferUploader.drawWithShader(buffer.buildOrThrow());
        //RenderSystem.disableBlend();
    }
    public void render(int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
        drawRoundedRect(x, y, width, height, radius, borderColor, fillColor);
    }
    private void drawRoundedRect(int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
        Tesselator tessellator = Tesselator.getInstance();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        BufferBuilder buffer = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        addVertex(buffer, x + radius, y + radius, fillColor);
        addVertex(buffer, x + width - radius, y + radius, fillColor);
        addVertex(buffer, x + width - radius, y + height - radius, fillColor);
        addVertex(buffer, x + radius, y + height - radius, fillColor);
        BufferUploader.drawWithShader(buffer.buildOrThrow());

        // Draw borders and corners
        buffer = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        drawBorder(buffer, x, y, width, height, radius, borderColor);
        drawCorners(buffer, x, y, width, height, radius, borderColor);
        BufferUploader.drawWithShader(buffer.buildOrThrow());

    }
    private void drawBorder(BufferBuilder buffer, int x, int y, int width, int height, int radius, int borderColor) {
        // Top border
        addVertex(buffer, x + radius, y, borderColor);
        addVertex(buffer, x + width - radius, y, borderColor);
        addVertex(buffer, x + width - radius, y + radius, borderColor);
        addVertex(buffer, x + radius, y + radius, borderColor);
        // Bottom border
        addVertex(buffer, x + radius, y + height - radius, borderColor);
        addVertex(buffer, x + width - radius, y + height - radius, borderColor);
        addVertex(buffer, x + width - radius, y + height, borderColor);
        addVertex(buffer, x + radius, y + height, borderColor);
        // Left border
        addVertex(buffer, x, y + radius, borderColor);
        addVertex(buffer, x + radius, y + radius, borderColor);
        addVertex(buffer, x + radius, y + height - radius, borderColor);
        addVertex(buffer, x, y + height - radius, borderColor);
        // Right border
        addVertex(buffer, x + width - radius, y + radius, borderColor);
        addVertex(buffer, x + width, y + radius, borderColor);
        addVertex(buffer, x + width, y + height - radius, borderColor);
        addVertex(buffer, x + width - radius, y + height - radius, borderColor);
    }
    private void drawCorners(BufferBuilder buffer, int x, int y, int width, int height, int radius, int color) {
        // Top-left corner
        drawArc(buffer, x + radius, y + radius, radius, 180, 270, color);
        // Top-right corner
        drawArc(buffer, x + width - radius, y + radius, radius, 270, 360, color);
        // Bottom-right corner
        drawArc(buffer, x + width - radius, y + height - radius, radius, 0, 90, color);
        // Bottom-left corner
        drawArc(buffer, x + radius, y + height - radius, radius, 90, 180, color);
    }
    private void drawArc(BufferBuilder buffer, int cx, int cy, int radius, int startAngle, int endAngle, int color) {
        int segments = SEGMENTS * (endAngle - startAngle) / 360;
        double angleIncrement = Math.PI / 180.0 * (endAngle - startAngle) / segments;

        for (int i = 0; i < segments; i++) {
            double angle1 = Math.PI / 180.0 * startAngle + i * angleIncrement;
            double angle2 = Math.PI / 180.0 * startAngle + (i + 1) * angleIncrement;

            addVertex(buffer, cx, cy, color);
            addVertex(buffer, (int) (cx + Math.cos(angle1) * radius), (int) (cy + Math.sin(angle1) * radius), color);
            addVertex(buffer, (int) (cx + Math.cos(angle2) * radius), (int) (cy + Math.sin(angle2) * radius), color);
        }
    }

 */
}
