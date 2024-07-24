package anmao.mc.nekoui.screen;

import anmao.mc.amlib.mc.screen.widget.DT_XYWH;
import anmao.mc.amlib.mc.screen.widget.SquareImageButton;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class SettingScreen extends ScreenCore {
    public SettingScreen() {
        super("screen.nekoui.setting");
    }

    @Override
    protected void init() {
        super.init();
        int px = 32;
        int py = 32;
        int lbg = 0x55646464;
        int lsc = 0x83838383;
        int lt = 0xffffffff;
        int ts = Color.RED.getRGB();
        SquareImageButton openPageSetting = new SquareImageButton(new DT_XYWH(px,py,32,16),getComponent("open_page_setting"),()-> Minecraft.getInstance().setScreen(new PageSettingScreen()));
        openPageSetting.setBgUsualColor(lbg);
        openPageSetting.setBgSelectColor(lsc);
        openPageSetting.setTextSelectColor(ts);
        openPageSetting.setTextUsualColor(lt);
        addRenderableWidget(openPageSetting);
        py += 40;
        SquareImageButton openProjectsSetting = new SquareImageButton(new DT_XYWH(px,py,32,16),getComponent("open_projects_setting"),()-> Minecraft.getInstance().setScreen(new PageSettingScreen()));
        openProjectsSetting.setBgUsualColor(lbg);
        openProjectsSetting.setBgSelectColor(lsc);
        openProjectsSetting.setTextSelectColor(ts);
        openProjectsSetting.setTextUsualColor(lt);
        addRenderableWidget(openProjectsSetting);
    }
}
