package dev.anye.mc.nekoui.register;

import com.mojang.logging.LogUtils;
import dev.anye.core.system._File;
import dev.anye.core.system._KeySimulate;
import dev.anye.core.system._System;
import dev.anye.mc.nekoui.config.Configs;
import dev.anye.mc.nekoui.dat$type.MenuProjectData;
import dev.anye.mc.nekoui.other.CJS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.slf4j.Logger;

import java.awt.*;

public class MenuProject {
	protected static final Logger LOGGER = LogUtils.getLogger();
	private String name;
	private MenuProjectData.Type type;
	private String value;

	public MenuProject(MenuProjectData menuProjectData) {
		this(menuProjectData.name(), menuProjectData.type(), menuProjectData.value());
	}

	public MenuProject(String name, MenuProjectData.Type type, String value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public String name() {
		return name;
	}

	public MenuProjectData.Type type() {
		return type;
	}

	public String value() {
		return value;
	}

	public void onClick() {
		if (value.isEmpty()) return;
		switch (type) {
			case message -> sendChat(value);
			case command -> sendCommand(value);
			case button -> {
				String[] keyA = value.split(" ");
				int[] keys = new int[keyA.length];
				for (int i = 0; i < keyA.length; i++) {
					keys[i] = Integer.parseInt(keyA[i]);
				}
				simulateKey(keys);
			}
			case js -> CJS.E.addParameter("minecraft", Minecraft.getInstance())
					.runFile(name, _File.getFilePath(Configs.ConfigDir_JS, value));
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
