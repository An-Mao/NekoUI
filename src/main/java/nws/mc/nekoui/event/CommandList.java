package nws.mc.nekoui.event;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandList {
    private static final String root = "nekoui";
    public List<CommandData> commands;
    private final CommandDispatcher<CommandSourceStack> dispatcher;
    public CommandList(CommandDispatcher<CommandSourceStack> dispatcher) {
        this.dispatcher = dispatcher;
        commands = new ArrayList<>();
        commands.add(CommandData.create(CommandData.Permission_Player,Command::reloadAll,root,"reload"));


    }
    public void register(){
        for (CommandData commandData : commands) {
            LiteralArgumentBuilder<CommandSourceStack> c = null;
            List<String> cs = commandData.command;
            int size = cs.size() - 1;
            for (int i = size; i >= 0; i--) {
                if (i == size) {
                    c = Commands.literal(cs.get(i))
                            .requires(commandSourceStack -> commandSourceStack.hasPermission(commandData.permission))
                            .executes(commandData::code);
                }else {
                    c = Commands.literal(cs.get(i)).then(c);
                }
            }
            dispatcher.register(c);
        }
    }
}
