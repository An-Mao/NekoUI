package dev.anye.mc.nekoui.event;

import com.mojang.brigadier.context.CommandContext;
import dev.anye.mc.nekoui.config.Configs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class Command {
	public static int reloadAll(CommandContext<CommandSourceStack> context) {
		Configs.init();
		//ScreenElementConfig.I.init();
		context.getSource().sendSuccess(() -> Component.translatable("reload.nekoui.all.success"), false);
		return 1;
	}

	public static int reloadScreenElement(CommandContext<CommandSourceStack> context) {
		Configs.LoadScreenRender();
		context.getSource().sendSuccess(() -> Component.translatable("reload.nekoui.screen_element.success"), false);
		return 1;
	}

}
