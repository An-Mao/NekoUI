package anmao.mc.nekoui.screen;

import anmao.mc.amlib.screen.widget.DT_ListBoxData;
import anmao.mc.nekoui.screen.widget.SimpleButton;
import anmao.mc.nekoui.screen.widget.SimpleDropDownSelectBox;
import anmao.mc.nekoui.screen.widget.SimpleEditBox;
import anmao.mc.nekoui.screen.widget.SimpleLabel;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TestScreen extends ScreenCore {
    int clickCount = 0;
    SimpleLabel simpleLabel = null;
    SimpleEditBox simpleEditBox = null;
    protected TestScreen() {
        super("screen.nekoui.test");
    }

    @Override
    protected void init() {
        super.init();
        simpleLabel = new SimpleLabel(100,100,100,50, Component.literal("test"),0xff000000,0x55000000,0xffffffff,true,true,true);
        addRenderableWidget(simpleLabel);
        addRenderableWidget(new SimpleButton(200,100,100,50, Component.literal("按钮，点击更改标签内容"),0xff000000,0x55000000,0xffffffff,true,true,true,() -> {
            clickCount ++;
            this.simpleLabel.setMessage(Component.literal("点击次数："+clickCount));
        }).setBorderHoverColor(0x77000000));
        simpleEditBox = new SimpleEditBox(100,200,100,17,simpleEditBox, Component.literal("test"))
                .setBorderHoverColor(0x77000000).setBorderUsualColor(0x55000000)
                .setBackgroundHoverColor(0x55000000).setBackgroundUsualColor(0x55000000);

        addRenderableWidget(simpleEditBox);
        addRenderableWidget(new SimpleDropDownSelectBox(100,15,32,17, Component.literal("test"),new DT_ListBoxData(Component.literal("test"),"test"))
                .setBorderHoverColor(0x77000000).setBorderUsualColor(0x55000000)
                .setBackgroundHoverColor(0x55000000).setBackgroundUsualColor(0x55000000));
    }
}
