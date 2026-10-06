package dev.anye.mc.nekoui.screen;

import com.mojang.logging.LogUtils;
import dev.anye.mc.cores.screen.widget.RenderWidgetCore;
import dev.anye.mc.cores.screen.widget.SimpleListBoxData;
import dev.anye.mc.cores.screen.widget.simple.SimpleButton;
import dev.anye.mc.cores.screen.widget.simple.SimpleDropDownSelectBox;
import dev.anye.mc.cores.screen.widget.simple.SimpleEditBox;
import dev.anye.mc.cores.screen.widget.simple.SimpleLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

import java.util.List;

public class ScreenCore extends Screen {
	protected static final Logger LOGGER = LogUtils.getLogger();
	public final String translateId;

	protected ScreenCore(String translateId) {
		super(Component.translatable(translateId + ".title"));
		this.translateId = translateId + ".";
	}

	public Component getComponent(String s) {
		return Component.translatable(this.translateId + s);
	}

	public SimpleLabel createNewLabel(int x, int y, int w, int h, Component pMessage) {
		return new SimpleLabel(x, y, w, h, pMessage).setAutoWidth(false).setAutoHeight(false).setCenterText(true).setRadius(2);
	}

	public SimpleDropDownSelectBox createNewSelectBox(int x, int y, int w, int h, Component pMessage, List<SimpleListBoxData> data) {
		return new SimpleDropDownSelectBox(x, y, w, h, pMessage, data)
				.setRadius(2);
	}

	public SimpleButton createNewButton(int x, int y, int w, int h, Component pMessage, RenderWidgetCore.OnPress c) {
		SimpleButton b = new SimpleButton(x, y, w, h, pMessage, true, true, true, c);
		b.setRadius(2);
		return b;
	}

	public SimpleEditBox createNewEditBox(int x, int y, int w, int h, SimpleEditBox editBox, Component pMessage) {
		return new SimpleEditBox(this.font,x, y, w, h, editBox, pMessage)
				.setRadius(2);
	}
}
