package anmao.mc.nekoui.screen;

import anmao.mc.amlib.screen.widget.DT_XYWH;
import anmao.mc.amlib.screen.widget.SquareImageButton;
import anmao.mc.nekoui.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
@OnlyIn(Dist.CLIENT)
public class SettingScreen extends ScreenCore {
    public SettingScreen() {
        super("screen.nekoui.setting");
    }

    @Override
    protected void init() {
        super.init();
        int centerX = width / 2;
        int centerY = height / 2;
        int px = -64;
        int py = -32;
        int lbg = 0x55646464;
        int lsc = 0x83838383;
        int lt = 0xffffffff;
        int ts = Color.RED.getRGB();
        SquareImageButton changeAutoPage = new SquareImageButton(new DT_XYWH(centerX + px,centerY + py,128,16),getComponent("change_auto_page."+getAutoPageState()),()-> {
            Config.INSTANCE.getDatas().setAutoPage(!Config.INSTANCE.getDatas().isAutoPage());
            Config.INSTANCE.save();
            Minecraft.getInstance().setScreen(new SettingScreen());
        });
        changeAutoPage.setBgUsualColor(lbg);
        changeAutoPage.setBgSelectColor(lsc);
        changeAutoPage.setTextSelectColor(ts);
        changeAutoPage.setTextUsualColor(lt);
        addRenderableWidget(changeAutoPage);

        py += 20;

        SquareImageButton openPageSetting = new SquareImageButton(new DT_XYWH(centerX + px,centerY + py,128,16),getComponent("open_page_setting"),()->{
            if (Config.INSTANCE.getDatas().isAutoPage()){
                Minecraft.getInstance().setScreen(new AutoPageSettingScreen());
            }else {
                Minecraft.getInstance().setScreen(new PageSettingScreen());
            }
        });
        openPageSetting.setBgUsualColor(lbg);
        openPageSetting.setBgSelectColor(lsc);
        openPageSetting.setTextSelectColor(ts);
        openPageSetting.setTextUsualColor(lt);
        addRenderableWidget(openPageSetting);
        py += 20;
        SquareImageButton openProjectsSetting = new SquareImageButton(new DT_XYWH(centerX + px,centerY + py,128,16),getComponent("open_projects_setting"),()-> Minecraft.getInstance().setScreen(new ProjectsSettingScreen()));
        openProjectsSetting.setBgUsualColor(lbg);
        openProjectsSetting.setBgSelectColor(lsc);
        openProjectsSetting.setTextSelectColor(ts);
        openProjectsSetting.setTextUsualColor(lt);
        addRenderableWidget(openProjectsSetting);
    }

    private String getAutoPageState() {
        if (Config.INSTANCE.getDatas().isAutoPage()) {
            return "on";
        } else {
            return "off";
        }
    }
}
