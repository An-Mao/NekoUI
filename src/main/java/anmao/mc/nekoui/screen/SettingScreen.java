package anmao.mc.nekoui.screen;

import anmao.mc.amlib.screen.widget.simple.SimpleButton;
import anmao.mc.nekoui.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
        SimpleButton changeAutoPage = new SimpleButton(centerX + px,centerY + py,128,16,getComponent("change_auto_page."+getAutoPageState()),()-> {
            Config.INSTANCE.getDatas().setAutoPage(!Config.INSTANCE.getDatas().isAutoPage());
            Config.INSTANCE.save();
            Minecraft.getInstance().setScreen(new SettingScreen());
        });
        changeAutoPage.setAutoWidth(false).setWidth(128);
        changeAutoPage.setCenterText(true);
        addRenderableWidget(changeAutoPage);

        py += 20;

        SimpleButton openPageSetting = new SimpleButton(centerX + px,centerY + py,128,16,getComponent("open_page_setting"),()->{
            if (Config.INSTANCE.getDatas().isAutoPage()){
                Minecraft.getInstance().setScreen(new AutoPageSettingScreen());
            }else {
                Minecraft.getInstance().setScreen(new PageSettingScreen());
            }
        });
        openPageSetting.setAutoWidth(false).setWidth(128);
        openPageSetting.setCenterText(true);
        addRenderableWidget(openPageSetting);
        py += 20;
        SimpleButton openProjectsSetting = new SimpleButton(centerX + px,centerY + py,128,16,getComponent("open_projects_setting"),()-> Minecraft.getInstance().setScreen(new ProjectsSettingScreen()));
        openProjectsSetting.setAutoWidth(false).setWidth(128);
        openProjectsSetting.setCenterText(true);
        addRenderableWidget(openProjectsSetting);

        py += 20;
        SimpleButton openTestSetting = new SimpleButton(centerX + px,centerY + py,128,16,getComponent("open_test"),()-> Minecraft.getInstance().setScreen(new TestScreen()));
        openTestSetting.setAutoWidth(false).setWidth(128);
        openTestSetting.setCenterText(true);
        addRenderableWidget(openTestSetting);
    }

    private String getAutoPageState() {
        if (Config.INSTANCE.getDatas().isAutoPage()) {
            return "on";
        } else {
            return "off";
        }
    }
}
