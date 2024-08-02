package anmao.mc.nekoui.screen;

import anmao.mc.amlib.screen.widget.DT_ListBoxData;
import anmao.mc.amlib.screen.widget.RenderWidgetCore;
import anmao.mc.amlib.screen.widget.simple.SimpleButton;
import anmao.mc.amlib.screen.widget.simple.SimpleDropDownSelectBox;
import anmao.mc.amlib.screen.widget.simple.SimpleEditBox;
import anmao.mc.amlib.screen.widget.simple.SimpleLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ScreenCore extends Screen {
    private static final int borderColor = 0xff000000;
    private static final int fillColor = 0x55000000;
    private static final int textColor = 0xffffffff;
    public final String translateId;
    protected ScreenCore(String translateId) {
        super(Component.translatable(translateId+".title"));
        this.translateId = translateId +".";
    }
    public Component getComponent(String s){
        return Component.translatable(this.translateId + s);
    }
    public SimpleLabel createNewLabel(int x, int y, int w, int h, Component pMessage) {
        return new SimpleLabel(x,y,w,h,pMessage,borderColor,fillColor,textColor,false,false,true).setRadius(2);
    }
    public SimpleDropDownSelectBox createNewSelectBox(int x, int y, int w, int h, Component pMessage, List<DT_ListBoxData> data) {
        return new SimpleDropDownSelectBox(x,y,w,h,pMessage,data)
                .setBorderHoverColor(0xff000000)
                .setBorderUsualColor(borderColor)
                .setBackgroundHoverColor(0x99000000)
                .setBackgroundUsualColor(0x77000000)
                .setRadius(2);
    }
    public SimpleButton createNewButton(int x, int y, int w, int h, Component pMessage , RenderWidgetCore.OnPress c) {
        SimpleButton b = new SimpleButton(x, y, w, h, pMessage, borderColor, fillColor, textColor, true, true, true, c);
        b.setBorderHoverColor(0x77000000);
        b.setRadius(2);
        return b;
    }
    public SimpleEditBox createNewEditBox(int x, int y, int w, int h, SimpleEditBox editBox, Component pMessage) {
        return new SimpleEditBox(x,y,w,h,editBox, pMessage)
                .setBorderHoverColor(0x77000000)
                .setBorderUsualColor(borderColor)
                .setBackgroundHoverColor(0x55000000)
                .setBackgroundUsualColor(0x55000000)
                .setRadius(2);
    }
}
