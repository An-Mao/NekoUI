package dev.anye.mc.nekoui.screen;

import com.mojang.logging.LogUtils;
import dev.anye.core.dt._BoundingBox;
import dev.anye.mc.cores.screen.bs.BorderStyles;
import dev.anye.mc.cores.screen.widget.DT_ListBoxData;
import dev.anye.mc.cores.screen.widget.RenderWidgetCore;
import dev.anye.mc.cores.screen.widget.c.CWidgetButton;
import dev.anye.mc.cores.screen.widget.c.CWidgetDropDownSelectBox;
import dev.anye.mc.cores.screen.widget.c.CWidgetEditBox;
import dev.anye.mc.cores.screen.widget.c.CWidgetLabel;
import dev.anye.mc.cores.screen.widget.simple.SimpleButton;
import dev.anye.mc.cores.screen.widget.simple.SimpleDropDownSelectBox;
import dev.anye.mc.cores.screen.widget.simple.SimpleEditBox;
import dev.anye.mc.cores.screen.widget.simple.SimpleLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ScreenCore extends Screen {
    protected final Logger LOGGER = LogUtils.getLogger();
    public final String translateId;
    protected ScreenCore(String translateId) {
        super(Component.translatable(translateId+".title"));
        this.translateId = translateId +".";
    }
    public Component getComponent(String s){
        return Component.translatable(this.translateId + s);
    }
    public CWidgetLabel createNewLabel(int x, int y, int w, int h, Component pMessage) {
        return new CWidgetLabel(new _BoundingBox(x,y,w,h), BorderStyles.DEFAULT,pMessage,true,true);
    }
    public CWidgetDropDownSelectBox createNewSelectBox(int x, int y, int w, int h, Component pMessage, List<DT_ListBoxData> data) {
        return new CWidgetDropDownSelectBox(new _BoundingBox(x,y,w,h),BorderStyles.DEFAULT,pMessage,7,true,data);
    }
    public CWidgetButton createNewButton(int x, int y, int w, int h, Component pMessage , RenderWidgetCore.OnPress c) {
        return new CWidgetButton(new _BoundingBox(x, y, w, h),  BorderStyles.DEFAULT,pMessage, c);
    }
    public CWidgetEditBox createNewEditBox(int x, int y, int w, int h, CWidgetEditBox editBox, Component pMessage) {
        return new CWidgetEditBox(new _BoundingBox(x,y,w,h),BorderStyles.DEFAULT,editBox, pMessage);
    }
}
