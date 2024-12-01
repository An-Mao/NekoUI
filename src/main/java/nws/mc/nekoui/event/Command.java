package nws.mc.nekoui.event;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import nws.mc.nekoui.config.Config;
import nws.mc.nekoui.config.ban$screen.BanScreenConfig;
import nws.mc.nekoui.config.health$bar.HealthBarConfig;
import nws.mc.nekoui.config.hide$hud.HideHudConfig;
import nws.mc.nekoui.config.hotbar.HotBarConfig;
import nws.mc.nekoui.config.menu.MenuConfig;
import nws.mc.nekoui.config.menu.MenuScreenConfig;
import nws.mc.nekoui.config.mob$direction.MobDirectionConfig;
import nws.mc.nekoui.config.page.PageConfig;
import nws.mc.nekoui.config.screen$element.ScreenElementConfig;

public class Command {
    public static int reloadAll(CommandContext<CommandSourceStack> context) {
        Config.INSTANCE.init();
        BanScreenConfig.I.init();
        HealthBarConfig.I.init();
        HideHudConfig.I.init();
        HotBarConfig.INSTANCE.init();
        MenuScreenConfig.INSTANCE.init();
        MenuConfig.INSTANCE.init();
        MobDirectionConfig.I.init();
        PageConfig.INSTANCE.init();
        ScreenElementConfig.I.init();
        context.getSource().sendSuccess(()-> Component.translatable("reload.nekoui.all.success"), false);
        return 1;
    }


}
