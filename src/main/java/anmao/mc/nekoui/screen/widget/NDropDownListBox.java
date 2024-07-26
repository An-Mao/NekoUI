package anmao.mc.nekoui.screen.widget;

import anmao.mc.amlib.screen.widget.DT_ListBoxData;
import anmao.mc.amlib.screen.widget.DT_XYWH;
import anmao.mc.amlib.screen.widget.DropDownListBox;
import net.minecraft.network.chat.Component;

import java.util.List;

public class NDropDownListBox extends DropDownListBox {
    public NDropDownListBox(DT_XYWH dt_xywh, Component pMessage, DT_ListBoxData... data) {
        super(dt_xywh, pMessage, data);
    }

    public NDropDownListBox(DT_XYWH dt_xywh, Component pMessage, List<DT_ListBoxData> data) {
        super(dt_xywh, pMessage, data);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double sx, double sy) {
        System.out.println("mouseScrolled  = "+sx + " : "+sy);
        System.out.println("mouseScrolled1  = "+pMouseX + " : "+pMouseY);
        System.out.println("mouseScrolled2  = "+getX() +"-"+ (getX() + getWidth()) + " : "+getY() +"-"+ (getY() + getHeight()));
        return super.mouseScrolled(pMouseX, pMouseY, sx,sy);
    }
}
