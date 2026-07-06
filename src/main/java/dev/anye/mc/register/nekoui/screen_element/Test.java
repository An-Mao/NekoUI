package dev.anye.mc.register.nekoui.screen_element;

import dev.anye.mc.nekoui.NekoUI;
import dev.anye.mc.nekoui.dat$type.ScreenRender;
import dev.anye.mc.nekoui.register.screen_element.NekoUIScreenElement;
import dev.anye.mc.nekoui.register.screen_element.ScreenElement;
import org.joml.Vector3i;

import java.util.List;

@NekoUIScreenElement(modid = NekoUI.MOD_ID, name = "test")
public class Test extends ScreenElement {
	public Test() {
		super(new ScreenRender("0", "0", new Vector3i(0, 0, 0), List.of()));
	}

}
