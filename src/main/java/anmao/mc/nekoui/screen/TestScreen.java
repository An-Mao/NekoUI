package anmao.mc.nekoui.screen;

import anmao.mc.amlib.screen.widget.DT_ListBoxData;
import anmao.mc.amlib.screen.widget.simple.*;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TestScreen extends ScreenCore {
    int clickCount = 0;
    static int r = 3;
    SimpleLabel simpleLabel = null;
    SimpleEditBox simpleEditBox = null;
    protected TestScreen() {
        super("screen.nekoui.test");
    }

    @Override
    protected void init() {
        super.init();
        simpleLabel = new SimpleLabel(100,100,100,50, Component.literal("test"),0xff000000,0x55000000,0xffffffff,true,true,true).setRadius(r);
        addRenderableWidget(simpleLabel);
        addRenderableWidget(new SimpleButton(200,100,100,50, Component.literal("按钮，点击更改标签内容"),0xff000000,0x55000000,0xffffffff,true,true,true,() -> {
            clickCount ++;
            this.simpleLabel.setMessage(Component.literal("点击次数："+clickCount));
        }).setBorderHoverColor(0x77000000).setRadius(r));
        simpleEditBox = new SimpleEditBox(100,200,100,17,simpleEditBox, Component.literal("test"))
                .setBorderHoverColor(0x77000000)
                .setBorderUsualColor(0x55000000)
                .setBackgroundHoverColor(0x55000000)
                .setBackgroundUsualColor(0x55000000)
                .setRadius(r);

        addRenderableWidget(simpleEditBox);
        SimpleDropDownSelectBox simpleDropDownSelectBox = new SimpleDropDownSelectBox(
                100, 15, 32, 21,
                Component.literal("Test"),
                new DT_ListBoxData(Component.literal("Aest"), "test"),
                new DT_ListBoxData(Component.literal("Kest1"), "test"),
                new DT_ListBoxData(Component.literal("test2"), "test"),
                new DT_ListBoxData(Component.literal("test3"), "test"),
                new DT_ListBoxData(Component.literal("test4"), "test"),
                new DT_ListBoxData(Component.literal("test5"), "test")
        );
        simpleDropDownSelectBox.setBorderHoverColor(0x99000000)
                .setBorderUsualColor(0x77000000)
                .setBackgroundHoverColor(0x55000000)
                .setBackgroundUsualColor(0x55000000)
                .setRadius(r);
        addRenderableWidget(simpleDropDownSelectBox);

        addRenderableWidget(new SimpleColorBox(32,150,32,32, Component.literal("Test"))
                .setShowColor(0xff0000ff));
    }
}
