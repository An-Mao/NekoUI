package anmao.mc.nekoui.screen.widget;

import anmao.dev.core.math._Math;
import anmao.mc.amlib.render.Draw;
import anmao.mc.amlib.screen.widget.RenderWidgetCore;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class SimpleWidgetCore<T extends SimpleWidgetCore<T>> extends RenderWidgetCore<T> {
    protected int radius;
    protected int borderUsualColor,
            borderHoverColor;
    protected int contentX, contentY, contentW, contentH;
    protected SimpleWidgetCore(int x, int y, int w, int h, Component pMessage) {
        this(x, y, w, h,2,0xFF000000,0xFF00FF00,0xffffffff,0xff0000ff, pMessage);
    }
    protected SimpleWidgetCore(int x, int y, int w, int h, int radius, int borderUsualColor, int borderHoverColor,int textUsualColor,int textHoverColor, Component pMessage) {
        super(x, y, w, h, pMessage);
        setTextUsualColor(textUsualColor);
        setTextHoverColor(textHoverColor);
        setBorderUsualColor(borderUsualColor);
        setBorderHoverColor(borderHoverColor);
        setRadius(radius);
    }

    @Override
    public void setWidth(int pWidth) {
        super.setWidth(pWidth);
        setRadius(getRadius());
    }

    @Override
    public void setHeight(int pHeight) {
        super.setHeight(pHeight);
        setRadius(getRadius());
    }

    //-------------------------------------
    public T setBorderUsualColor(int borderUsualColor) {
        this.borderUsualColor = borderUsualColor;
        return self();
    }

    public int getBorderUsualColor() {
        return borderUsualColor;
    }

    public T setBorderHoverColor(int borderHoverColor) {
        this.borderHoverColor = borderHoverColor;
        return self();
    }

    public int getBorderHoverColor() {
        return borderHoverColor;
    }

    public T setRadius(int radius) {
        this.radius = radius;
        setContentX(getX() + radius);
        setContentY(getY() + radius);
        setContentH(getHeight() - 2 * radius);
        setContentW(getWidth() - 2 * radius);
        return self();
    }

    public int getRadius() {
        return radius;
    }

    public T setContentH(int contentH) {
        this.contentH = contentH;
        return self();
    }

    public int getContentH() {
        return contentH;
    }

    public T setContentW(int contentW) {
        this.contentW = contentW;
        return self();
    }

    public int getContentW() {
        return contentW;
    }

    public T setContentX(int contentX) {
        this.contentX = contentX;
        return self();
    }

    public int getContentX() {
        return contentX;
    }
    public T setContentY(int contentY) {
        this.contentY = contentY;
        return self();
    }

    public int getContentY() {
        return contentY;
    }

    //-------------------------------------
    protected void renderShape(GuiGraphics guiGraphics,int borderColor,int fillColor) {
        renderShape(guiGraphics,getX(), getY(), getWidth(), getHeight(), getRadius(), borderColor, fillColor);
    }
    protected void renderShape(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
        drawRoundedRect(guiGraphics,x, y, width, height, radius, borderColor, fillColor);
    }

    protected void drawRoundedRect(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, int borderColor, int fillColor) {
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
    protected void drawBorder(BufferBuilder buffer, int x, int y, int width, int height, int radius, int borderColor) {
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
    protected void drawCorners(GuiGraphics guiGraphics, int x, int y, int width, int height, int radius, int color) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(getX(),getY(),getLayerZ());
        poseStack.translate(radius, radius, getLayerZ());
        Draw.drawSector(poseStack.last().pose(),0,radius, _Math.ARC_180,_Math.ARC_270,color);
        poseStack.translate(width-2*radius, 0, getLayerZ());
        Draw.drawSector(poseStack.last().pose(),0,radius,_Math.ARC_270,_Math.ARC_360,color);
        poseStack.translate(0, height-2 * radius, getLayerZ());
        Draw.drawSector(poseStack.last().pose(),0,radius,0,_Math.ARC_90,color);
        poseStack.translate(-(width-2*radius), 0, getLayerZ());
        Draw.drawSector(poseStack.last().pose(),0,radius,_Math.ARC_90,_Math.ARC_180,color);
        poseStack.popPose();
    }
    protected void addVertex(BufferBuilder buffer, int x, int y, int color) {
        buffer.addVertex(x, y, 0).setColor((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, (color >> 24) & 0xFF);//.endVertex();
    }
    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.visible) {
            int borderColor = getBorderUsualColor();
            int fillColor = getBackgroundUsualColor();
            if (isMouseOver(pMouseX, pMouseY)) {
                borderColor = getBorderHoverColor();
                fillColor = getBackgroundHoverColor();
            }
            PoseStack poseStack = pGuiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(0, 0, layerZ);
            renderShape(pGuiGraphics,borderColor,fillColor);
            poseStack.popPose();
            renderContent(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }
    }
    protected abstract void renderContent(GuiGraphics guiGraphics,int mouseX, int mouseY, float partialTick);
}
