package anmao.mc.nekoui.screen.widget;

import anmao.mc.amlib.screen.widget.square.SquareLabels;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Label extends SquareLabels {
    private final int bgc;
    private final int tc;
    private final int dx;
    private final int dy;
    public Label(int x, int y, int h, Component pMessage, int color, int textColor) {
        this(x, y, -1, h, pMessage, color, textColor);
    }
    public Label(int x, int y, int w, int h, Component pMessage, int color, int textColor) {
        super(x, y, w, h, pMessage, color, textColor);
        this.bgc = color;
        this.tc = textColor;
        if (w == -1){
            this.setWidth(this.font.width(getMessage())+8);
            setX(getX() - getWidth());
        }else  if (w == -2){
            this.setWidth(this.font.width(getMessage())+8);
        }
        dx = getX()+width/2;
        dy = getY()+height/ 2 - font.lineHeight/2;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        this.drawSquare(guiGraphics, this.bgc);
        guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);
        guiGraphics.drawCenteredString(font,getMessage(),dx,dy,tc);
    }
}
