package anmao.mc.nekoui.screen;

import anmao.mc.nekoui.screen.widget.SimpleLabel;
import net.minecraft.network.chat.Component;

public class TestScreen extends ScreenCore {

    protected TestScreen() {
        super("screen.nekoui.test");
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(new SimpleLabel(100,100,100,50, Component.empty(),0xffff0000,0xff00ff00));
    }
}
