package dev.anye.mc.nekoui.dat_type;

import com.mojang.logging.LogUtils;
import dev.anye.core.javascript._JavaScript;
import dev.anye.core.javascript._NashornJS;
import dev.anye.core.system._File;
import dev.anye.core.system._KeySimulate;
import dev.anye.core.system._System;
import dev.anye.mc.nekoui.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.slf4j.Logger;

import java.awt.*;

public record MenuProject(
		String name,
		MenuProjectData.Type type,
		String value) {
	private static final Logger LOGGER = LogUtils.getLogger();
	private static final _JavaScript<?,?> JS = new _NashornJS(false);

	public MenuProject(MenuProjectData menuProjectData) {
		this(menuProjectData.name(), menuProjectData.type(), menuProjectData.value());
	}

	public void onClick() {
		if (value.isEmpty()) return;
		switch (type) {
			case MESSAGE -> sendChat(value);
			case COMMAND -> sendCommand(value);
			case BUTTON -> {
				String[] keyA = value.split(" ");
				int[] keys = new int[keyA.length];
				for (int i = 0; i < keyA.length; i++) {
					keys[i] = Integer.parseInt(keyA[i]);
				}
				simulateKey(keys);
			}
				case JS -> JS.addParameter("minecraft", Minecraft.getInstance())
					.runFile(name, _File.getFilePath(Configs.CONFIG_DIR_JS, value));
		}
	}

	public void sendChat(String msg) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null) {
			player.connection.sendChat(msg);
		}
	}

	public void sendCommand(String command) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null) {
			player.connection.sendCommand(command);
		}
	}

	public void simulateKey(int[] keyCode) {
		if (_System.isWindows) {
			for (int key : keyCode) {
				_KeySimulate.simulateKeyPress(key);
			}
			for (int i = keyCode.length - 1; i >= 0; i--) {
				_KeySimulate.simulateKeyRelease(keyCode[i]);
			}
		} else {
			try {
				Robot robot = new Robot();
				for (int key : keyCode) {
					robot.keyPress(key);
				}
				for (int i = keyCode.length - 1; i >= 0; i--) {
					robot.keyRelease(keyCode[i]);
				}
			} catch (AWTException e) {
				LOGGER.error(e.getMessage());
			}
		}
	}

}
