package anmao.mc.nekoui.screen;

import anmao.mc.amlib.screen.widget.simple.SimpleEditBox;
import anmao.mc.amlib.screen.widget.square.SquareImageButton;
import anmao.mc.nekoui.config.menu.MenuScreenConfig;
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
        int cx = width/2;
        int py = 50;

        addRenderableWidget(createNewLabel(cx,py,12,16,getComponent("label.sectors")));
        sectorsEditBox = createNewEditBox(cx,py,24,12,sectorsEditBox,getComponent("sectors_input"));
        addRenderableWidget(sectorsEditBox);
        sectorsEditBox.setValue(String.valueOf(MenuScreenConfig.INSTANCE.getDatas().sectors));
        py += 20;
        addRenderableWidget(createNewLabel(cx,py,12,16,getComponent("label.inner_radius")));
        innerRadiusEditBox = createNewEditBox(cx,py,32,12,innerRadiusEditBox,getComponent("inner_radius_input"));
        addRenderableWidget(innerRadiusEditBox);
        innerRadiusEditBox.setValue(String.valueOf(MenuScreenConfig.INSTANCE.getDatas().innerRadius));
        py += 20;
        addRenderableWidget(createNewLabel(cx,py,12,16,getComponent("label.outer_radius")));
        outerRadiusEditBox = createNewEditBox(cx,py,32,12,outerRadiusEditBox,getComponent("outer_radius_input"));
        addRenderableWidget(outerRadiusEditBox);
        outerRadiusEditBox.setValue(String.valueOf(MenuScreenConfig.INSTANCE.getDatas().outerRadius));
        py += 20;
        addRenderableWidget(createNewLabel(cx,py,12,16,getComponent("label.select_color")));
        selectColorEditBox = createNewEditBox(cx,py,90,12,selectColorEditBox,getComponent("select_color_input"));
        addRenderableWidget(selectColorEditBox);
        selectColorEditBox.setValue(MenuScreenConfig.INSTANCE.getDatas().SelectColor);
        py += 20;
        addRenderableWidget(createNewLabel(cx,py,12,16,getComponent("label.usual_color")));
        usualColorEditBox = createNewEditBox(cx,py,90,12,usualColorEditBox,getComponent("usual_color_input"));
        addRenderableWidget(usualColorEditBox);
        usualColorEditBox.setValue(MenuScreenConfig.INSTANCE.getDatas().UsualColor);

        py += 24;
        SquareImageButton save = new SquareImageButton(cx,py,32,16,getComponent("save"),this::saveConfig);
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
