package dev.anye.mc.nekoui.screen;

import dev.anye.mc.cores.screen.widget.simple.SimpleButton;
import dev.anye.mc.cores.screen.widget.simple.SimpleEditBox;
import dev.anye.mc.cores.screen.widget.simple.SimpleLabel;
import dev.anye.mc.nekoui.config.menu.MenuScreenConfig;
import dev.anye.mc.nekoui.config.menu.MenuScreenData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AutoPageSettingScreen extends ScreenCore{
    SimpleEditBox sectorsEditBox,innerRadiusEditBox,outerRadiusEditBox,selectColorEditBox,usualColorEditBox;
    public AutoPageSettingScreen() {
        super("screen.nekoui.auto_page_setting");
    }


    @Override
    protected void init() {
        super.init();
		MenuScreenConfig.INSTANCE.ifPresent(menuScreenData -> {
			int cx = width / 4;
			int py = 50;

			SimpleLabel simpleLabel = createNewLabel(cx, py, 12, 16, getComponent("label.sectors"));
			simpleLabel.setAutoWidth(true);
			addRenderableWidget(simpleLabel);

			sectorsEditBox = createNewEditBox(simpleLabel.getX() + simpleLabel.getWidth(), py, 24, 16, sectorsEditBox, getComponent("sectors_input"));
			addRenderableWidget(sectorsEditBox);
			sectorsEditBox.setValue(String.valueOf(menuScreenData.sectors()));


			py += 20;
			simpleLabel = createNewLabel(cx, py, 12, 16, getComponent("label.inner_radius"));
			simpleLabel.setAutoWidth(true);
			addRenderableWidget(simpleLabel);
			innerRadiusEditBox = createNewEditBox(simpleLabel.getX() + simpleLabel.getWidth(), py, 32, 16, innerRadiusEditBox, getComponent("inner_radius_input"));
			addRenderableWidget(innerRadiusEditBox);
			innerRadiusEditBox.setValue(String.valueOf(menuScreenData.innerRadius()));


			py += 20;
			simpleLabel = createNewLabel(cx, py, 12, 16, getComponent("label.outer_radius"));
			simpleLabel.setAutoWidth(true);
			addRenderableWidget(simpleLabel);
			outerRadiusEditBox = createNewEditBox(simpleLabel.getX() + simpleLabel.getWidth(), py, 32, 16, outerRadiusEditBox, getComponent("outer_radius_input"));
			addRenderableWidget(outerRadiusEditBox);
			outerRadiusEditBox.setValue(String.valueOf(menuScreenData.outerRadius()));


			py += 20;
			simpleLabel = createNewLabel(cx, py, 12, 16, getComponent("label.select_color"));
			simpleLabel.setAutoWidth(true);
			addRenderableWidget(simpleLabel);
			selectColorEditBox = createNewEditBox(simpleLabel.getX() + simpleLabel.getWidth(), py, 90, 16, selectColorEditBox, getComponent("select_color_input"));
			addRenderableWidget(selectColorEditBox);
			selectColorEditBox.setValue(menuScreenData.selectColor());

			py += 20;
			simpleLabel = createNewLabel(cx, py, 12, 16, getComponent("label.usual_color"));
			simpleLabel.setAutoWidth(true);
			addRenderableWidget(simpleLabel);
			usualColorEditBox = createNewEditBox(simpleLabel.getX() + simpleLabel.getWidth(), py, 90, 16, usualColorEditBox, getComponent("usual_color_input"));
			addRenderableWidget(usualColorEditBox);
			usualColorEditBox.setValue(menuScreenData.usualColor());

			py += 24;
			SimpleButton save = createNewButton(cx, py, 32, 16, getComponent("save"), this::saveConfig);
			addRenderableWidget(save);
		});
    }


	private void saveConfig() {
		MenuScreenConfig.INSTANCE.save(new MenuScreenData(
				Integer.parseInt(sectorsEditBox.getValue()),
				Integer.parseInt(innerRadiusEditBox.getValue()),
				Integer.parseInt(outerRadiusEditBox.getValue()),
				selectColorEditBox.getValue(),
				usualColorEditBox.getValue()
		));
		this.minecraft.setScreen(new AutoPageSettingScreen());
	}
}
