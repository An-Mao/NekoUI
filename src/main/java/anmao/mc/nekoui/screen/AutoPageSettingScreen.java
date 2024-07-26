package anmao.mc.nekoui.screen;

import anmao.mc.amlib.screen.widget.DT_XYWH;
import anmao.mc.amlib.screen.widget.SquareImageButton;
import anmao.mc.nekoui.config.menu.MenuScreenConfig;
import anmao.mc.nekoui.screen.widget.Label;
import net.minecraft.client.gui.components.EditBox;

import java.awt.*;

public class AutoPageSettingScreen extends ScreenCore{
    EditBox sectorsEditBox,innerRadiusEditBox,outerRadiusEditBox,selectColorEditBox,usualColorEditBox;
    public AutoPageSettingScreen() {
        super("screen.nekoui.auto_page_setting");
    }


    @Override
    protected void init() {
        super.init();
        int cx = width/2;
        int py = 50;
        int lbg = 0x55646464;
        int lsc = 0x83838383;
        int lt = 0xffffffff;
        int ts = Color.RED.getRGB();

        addRenderableWidget(new Label(cx,py,12,getComponent("label.sectors"),lbg,lt));
        sectorsEditBox = new EditBox(font,cx,py,24,12,getComponent("sectors_input"));
        addRenderableWidget(sectorsEditBox);
        sectorsEditBox.setValue(String.valueOf(MenuScreenConfig.INSTANCE.getDatas().sectors));
        py += 20;
        addRenderableWidget(new Label(cx,py,12,getComponent("label.inner_radius"),lbg,lt));
        innerRadiusEditBox = new EditBox(font,cx,py,32,12,getComponent("inner_radius_input"));
        addRenderableWidget(innerRadiusEditBox);
        innerRadiusEditBox.setValue(String.valueOf(MenuScreenConfig.INSTANCE.getDatas().innerRadius));
        py += 20;
        addRenderableWidget(new Label(cx,py,12,getComponent("label.outer_radius"),lbg,lt));
        outerRadiusEditBox = new EditBox(font,cx,py,32,12,getComponent("outer_radius_input"));
        addRenderableWidget(outerRadiusEditBox);
        outerRadiusEditBox.setValue(String.valueOf(MenuScreenConfig.INSTANCE.getDatas().outerRadius));
        py += 20;
        addRenderableWidget(new Label(cx,py,12,getComponent("label.select_color"),lbg,lt));
        selectColorEditBox = new EditBox(font,cx,py,90,12,getComponent("select_color_input"));
        addRenderableWidget(selectColorEditBox);
        selectColorEditBox.setValue(MenuScreenConfig.INSTANCE.getDatas().SelectColor);
        py += 20;
        addRenderableWidget(new Label(cx,py,12,getComponent("label.usual_color"),lbg,lt));
        usualColorEditBox = new EditBox(font,cx,py,90,12,getComponent("usual_color_input"));
        addRenderableWidget(usualColorEditBox);
        usualColorEditBox.setValue(MenuScreenConfig.INSTANCE.getDatas().UsualColor);

        py += 24;
        SquareImageButton save = new SquareImageButton(new DT_XYWH(cx,py,32,16),getComponent("save"),this::saveConfig);
        save.setBgUsualColor(lbg);
        save.setBgSelectColor(lsc);
        save.setTextSelectColor(ts);
        save.setTextUsualColor(lt);
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
