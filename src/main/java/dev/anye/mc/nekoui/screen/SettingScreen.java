package dev.anye.mc.nekoui.screen;

import dev.anye.core.dt._BoundingBox;
import dev.anye.mc.cores.screen.bs.BorderStyles;
import dev.anye.mc.cores.screen.widget.c.CWidgetButton;
import dev.anye.mc.cores.screen.widget.simple.SimpleButton;
import dev.anye.mc.nekoui.config.Config;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

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
        CWidgetButton changeAutoPage = new CWidgetButton(new _BoundingBox(centerX + px,centerY + py,128,16), BorderStyles.DEFAULT,getComponent("change_auto_page."+getAutoPageState()),()-> {
            Config.INSTANCE.getDatas().setAutoPage(!Config.INSTANCE.getDatas().isAutoPage());
            Config.INSTANCE.save();
            Minecraft.getInstance().setScreen(new SettingScreen());
        });
        addRenderableWidget(changeAutoPage);

        py += 20;

        CWidgetButton openPageSetting = new CWidgetButton(new _BoundingBox(centerX + px,centerY + py,128,16),BorderStyles.DEFAULT,getComponent("open_page_setting"),()->{
            if (Config.INSTANCE.getDatas().isAutoPage()){
                Minecraft.getInstance().setScreen(new AutoPageSettingScreen());
            }else {
                Minecraft.getInstance().setScreen(new PageSettingScreen());
            }
        });
        addRenderableWidget(openPageSetting);
        py += 20;
        CWidgetButton openProjectsSetting = new CWidgetButton(new _BoundingBox(centerX + px,centerY + py,128,16),BorderStyles.DEFAULT,getComponent("open_projects_setting"),()-> Minecraft.getInstance().setScreen(new ProjectsSettingScreen()));
        addRenderableWidget(openProjectsSetting);
        /*
        py += 20;
        SimpleButton openTestSetting = new SimpleButton(centerX + px,centerY + py,128,16,getComponent("open_test"),()-> Minecraft.getInstance().setScreen(new TestScreen()));
        openTestSetting.setAutoWidth(false).setWidth(128);
        openTestSetting.setCenterText(true);
        addRenderableWidget(openTestSetting);

         */
    }

    private String getAutoPageState() {
        if (Config.INSTANCE.getDatas().isAutoPage()) {
            return "on";
        } else {
            return "off";
        }
    }
}
