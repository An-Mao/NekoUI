package anmao.mc.nekoui.screen;

import anmao.mc.amlib.screen.widget.Labels;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class Label extends Labels {
    private final int bgc;
    private final int tc;
    private final int dx = getX()+width/2;
    private final int dy = getY()+height/ 2 - font.lineHeight/2;
    public Label(int x, int y, int w, int h, Component pMessage, int color, int textColor) {
        super(x, y, w, h, pMessage, color, textColor);
        this.bgc = color;
        this.tc = textColor;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        this.drawSquare(guiGraphics, this.bgc);
        guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
        guiGraphics.drawCenteredString(font,getMessage(),dx,dy,tc);
    }
}
