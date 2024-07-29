package anmao.mc.nekoui.screen.widget;

import anmao.dev.core.math._Math;
import anmao.mc.amlib.render.Draw;
import anmao.mc.amlib.screen.widget.Labels;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SimpleLabel extends Labels {
    private final int bgc;
    private final int tc;
    private final int dx = getX()+width/2;
    private final int dy = getY()+height/ 2 - font.lineHeight/2;
    public SimpleLabel(int x, int y, int w, int h, Component pMessage, int color, int textColor) {
        super(x, y, w, h, pMessage, color, textColor);
        this.bgc = color;
        this.tc = textColor;
    }
    private static final int radius = 2;
    private static final int SEGMENTS = 5; // Number of segments to approximate a quarter circle

    private static final int BORDER_COLOR = 0xFF000000; // Black
    private static final int FILL_COLOR = 0x00CCCCCC; // Light gray

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        render(guiGraphics);
        guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
        guiGraphics.drawCenteredString(font,getMessage(),dx,dy,tc);
    }
    public void render(GuiGraphics guiGraphics, int x, int y) {
        drawRoundedRect(guiGraphics, x, y, getWidth(), getHeight(), radius, BORDER_COLOR, FILL_COLOR);
    }

    /*
    private void drawRoundedRect(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
        // Draw the four corners
        guiGraphics.fill(x, y, x + radius, y + radius, borderColor);
        guiGraphics.fill(x + width - radius, y, x + width, y + radius, borderColor);
        guiGraphics.fill(x, y + height - radius, x + radius, y + height, borderColor);
        guiGraphics.fill(x + width - radius, y + height - radius, x + width, y + height, borderColor);

        // Draw the borders
        guiGraphics.fill(x + radius, y, x + width - radius, y + radius, borderColor);
        guiGraphics.fill(x + radius, y + height - radius, x + width - radius, y + height, borderColor);
        guiGraphics.fill(x, y + radius, x + radius, y + height - radius, borderColor);
        guiGraphics.fill(x + width - radius, y + radius, x + width, y + height - radius, borderColor);

        // Draw the fill
        guiGraphics.fill(x + radius, y + radius, x + width - radius, y + height - radius, fillColor);
    }

     */





    public void render(GuiGraphics guiGraphics) {
        drawRoundedRect(guiGraphics,getX(), getY(), getWidth(), getHeight(), radius, BORDER_COLOR, FILL_COLOR);
    }
    private void addVertex(BufferBuilder buffer, int x, int y, int color) {
        buffer.addVertex(x, y, 0).setColor((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF);//.endVertex();
    }

    public void render(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
        drawRoundedRect(guiGraphics,x, y, width, height, radius, borderColor, fillColor);
    }


    private void drawRoundedRect(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
        // Setup render state
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
        BufferUploader.drawWithShader(buffer.buildOrThrow());
        drawCorners(guiGraphics, x, y, width, height, radius, borderColor);

        RenderSystem.disableBlend();
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

    private void drawCorners(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, int color) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(getX(),getY(),0);
        poseStack.translate(radius, radius, 0);
        Draw.drawSector(poseStack.last().pose(),0,radius, _Math.ARC_180,_Math.ARC_270,color);
        poseStack.translate(width-2*radius, 0, 0);
        Draw.drawSector(poseStack.last().pose(),0,radius,_Math.ARC_270,_Math.ARC_360,color);
        poseStack.translate(0, height-2 * radius, 0);
        Draw.drawSector(poseStack.last().pose(),0,radius,0,_Math.ARC_90,color);
        poseStack.translate(-(width-2*radius), 0, 0);
        Draw.drawSector(poseStack.last().pose(),0,radius,_Math.ARC_90,_Math.ARC_180,color);
        poseStack.popPose();
    }

    private void drawArc(Tesselator tessellator, int cx, int cy, int radius, int startAngle, int endAngle, int color) {
        int segments = Math.max(20, radius * 2); // Increase segments for smoother arcs
        double angleIncrement = Math.PI / 180.0 * (endAngle - startAngle) / segments;

        BufferBuilder buffer = tessellator.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);

        addVertex(buffer, cx, cy, color); // Center vertex

        for (int i = 0; i <= segments; i++) {
            double angle = Math.PI / 180.0 * startAngle + i * angleIncrement;
            int x = (int) (cx + Math.cos(angle) * radius);
            int y = (int) (cy + Math.sin(angle) * radius);
            addVertex(buffer, x, y, color);
        }

        BufferUploader.drawWithShader(buffer.buildOrThrow());
    }


}
