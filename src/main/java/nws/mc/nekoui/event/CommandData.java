package nws.mc.nekoui.event;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;

import java.util.List;

public class CommandData {
    public static final int Permission_OP = 2;
    public static final int Permission_Player = 0;
    public final List<String> command;
    public final int permission;
    public final Code code;

    public CommandData(int permission, Code code, String... command) {
        this.code = code;
        this.command = List.of(command);
        this.permission = permission;
    }
    public CommandData(Code code, String... command) {
        this.code = code;
        this.command = List.of(command);
        this.permission = Permission_Player;
    }

    public int code(CommandContext<CommandSourceStack> context) {
        return code.code(context);
    }

    public interface Code {
        int code(CommandContext<CommandSourceStack> context);
    }
    public static CommandData create(int permission, Code code, String... command) {
        return new CommandData(permission, code, command);
    }
    public static CommandData create(Code code, String... command) {
        return new CommandData(Permission_OP, code, command);
    }
}
