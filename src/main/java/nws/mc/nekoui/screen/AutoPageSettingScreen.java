package nws.mc.nekoui.screen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nws.mc.cores.screen.widget.simple.SimpleButton;
import nws.mc.cores.screen.widget.simple.SimpleEditBox;
import nws.mc.cores.screen.widget.simple.SimpleLabel;
import nws.mc.nekoui.config.menu.MenuScreenConfig;

@OnlyIn(Dist.CLIENT)
public class AutoPageSettingScreen extends ScreenCore{
    SimpleEditBox sectorsEditBox,innerRadiusEditBox,outerRadiusEditBox,selectColorEditBox,usualColorEditBox;
    public AutoPageSettingScreen() {
        super("screen.nekoui.auto_page_setting");
    }


    @Override
    protected void init() {
        super.init();
        int cx = width/4;
        int py = 50;

        SimpleLabel simpleLabel = createNewLabel(cx,py,12,16,getComponent("label.sectors"));
        simpleLabel.setAutoWidth(true);
        addRenderableWidget(simpleLabel);

        sectorsEditBox = createNewEditBox(simpleLabel.getX()+simpleLabel.getWidth(),py,24,16,sectorsEditBox,getComponent("sectors_input"));
        addRenderableWidget(sectorsEditBox);
        sectorsEditBox.setValue(String.valueOf(MenuScreenConfig.INSTANCE.getDatas().sectors));


        py += 20;
        simpleLabel = createNewLabel(cx,py,12,16,getComponent("label.inner_radius"));
        simpleLabel.setAutoWidth(true);
        addRenderableWidget(simpleLabel);
        innerRadiusEditBox = createNewEditBox(simpleLabel.getX()+simpleLabel.getWidth(),py,32,16,innerRadiusEditBox,getComponent("inner_radius_input"));
        addRenderableWidget(innerRadiusEditBox);
        innerRadiusEditBox.setValue(String.valueOf(MenuScreenConfig.INSTANCE.getDatas().innerRadius));


        py += 20;
        simpleLabel = createNewLabel(cx,py,12,16,getComponent("label.outer_radius"));
        simpleLabel.setAutoWidth(true);
        addRenderableWidget(simpleLabel);
        outerRadiusEditBox = createNewEditBox(simpleLabel.getX()+simpleLabel.getWidth(),py,32,16,outerRadiusEditBox,getComponent("outer_radius_input"));
        addRenderableWidget(outerRadiusEditBox);
        outerRadiusEditBox.setValue(String.valueOf(MenuScreenConfig.INSTANCE.getDatas().outerRadius));


        py += 20;
        simpleLabel = createNewLabel(cx,py,12,16,getComponent("label.select_color"));
        simpleLabel.setAutoWidth(true);
        addRenderableWidget(simpleLabel);
        selectColorEditBox = createNewEditBox(simpleLabel.getX()+simpleLabel.getWidth(),py,90,16,selectColorEditBox,getComponent("select_color_input"));
        addRenderableWidget(selectColorEditBox);
        selectColorEditBox.setValue(MenuScreenConfig.INSTANCE.getDatas().SelectColor);

        py += 20;
        simpleLabel = createNewLabel(cx,py,12,16,getComponent("label.usual_color"));
        simpleLabel.setAutoWidth(true);
        addRenderableWidget(simpleLabel);
        usualColorEditBox = createNewEditBox(simpleLabel.getX()+simpleLabel.getWidth(),py,90,16,usualColorEditBox,getComponent("usual_color_input"));
        addRenderableWidget(usualColorEditBox);
        usualColorEditBox.setValue(MenuScreenConfig.INSTANCE.getDatas().UsualColor);

        py += 24;
        SimpleButton save = createNewButton(cx,py,32,16,getComponent("save"),this::saveConfig);
        addRenderableWidget(save);
    }

    private void saveConfig() {
        MenuScreenConfig.INSTANCE.getDatas().sectors = Integer.parseInt(sectorsEditBox.getValue());
        MenuScreenConfig.INSTANCE.getDatas().innerRadius = Integer.parseInt(innerRadiusEditBox.getValue());
        MenuScreenConfig.INSTANCE.getDatas().outerRadius = Integer.parseInt(outerRadiusEditBox.getValue());
        MenuScreenConfig.INSTANCE.getDatas().SelectColor = selectColorEditBox.getValue();
        MenuScreenConfig.INSTANCE.getDatas().UsualColor = usualColorEditBox.getValue();
        MenuScreenConfig.INSTANCE.save();
        if (this.minecraft != null) {
            this.minecraft.setScreen(new AutoPageSettingScreen());
        }
    }
}
