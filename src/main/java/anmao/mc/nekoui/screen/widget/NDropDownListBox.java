package anmao.mc.nekoui.screen.widget;

import anmao.mc.amlib.screen.widget.DT_ListBoxData;
import anmao.mc.amlib.screen.widget.DT_XYWH;
import anmao.mc.amlib.screen.widget.DropDownListBox;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
@OnlyIn(Dist.CLIENT)
public class NDropDownListBox extends DropDownListBox {
    public NDropDownListBox(DT_XYWH dt_xywh, Component pMessage, DT_ListBoxData... data) {
        super(dt_xywh, pMessage, data);
    }

    public NDropDownListBox(DT_XYWH dt_xywh, Component pMessage, List<DT_ListBoxData> data) {
        super(dt_xywh, pMessage, data);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }
}
