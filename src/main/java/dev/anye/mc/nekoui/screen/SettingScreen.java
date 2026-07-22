package dev.anye.mc.nekoui.screen;

import dev.anye.mc.cores.screen.widget.simple.SimpleButton;
import dev.anye.mc.nekoui.config.Config;
import dev.anye.mc.nekoui.config.ConfigData;
import net.minecraft.client.Minecraft;

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
		SimpleButton changeAutoPage = new SimpleButton(centerX + px, centerY + py, 128, 16, getComponent("change_auto_page." + getAutoPageState()), () -> Config.INSTANCE.ifPresent(configData -> {
			configData.setAutoPage(!configData.autoPage());
			Config.INSTANCE.save();
			Minecraft.getInstance().setScreenAndShow(new SettingScreen());
		}));
		changeAutoPage.setAutoWidth(false).setWidth(128);
		changeAutoPage.setCenterText(true);
		addRenderableWidget(changeAutoPage);

		py += 20;

		SimpleButton openPageSetting = new SimpleButton(centerX + px, centerY + py, 128, 16, getComponent("open_page_setting"), () -> Config.INSTANCE.ifPresent(configData -> {
			if (configData.autoPage()) {
				Minecraft.getInstance().setScreenAndShow(new AutoPageSettingScreen());
			} else {
				Minecraft.getInstance().setScreenAndShow(new PageSettingScreen());
			}
		}));
		openPageSetting.setAutoWidth(false).setWidth(128);
		openPageSetting.setCenterText(true);
		addRenderableWidget(openPageSetting);
		py += 20;
		SimpleButton openProjectsSetting = new SimpleButton(centerX + px, centerY + py, 128, 16, getComponent("open_projects_setting"), () -> Minecraft.getInstance().setScreenAndShow(new ProjectsSettingScreen()));
		openProjectsSetting.setAutoWidth(false).setWidth(128);
		openProjectsSetting.setCenterText(true);
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
		return Config.INSTANCE.map(ConfigData::autoPage).orElse(false) ? "on":"off";
	}
}
