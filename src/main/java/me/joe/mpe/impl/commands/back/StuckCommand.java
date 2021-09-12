package me.joe.mpe.impl.commands.back;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class StuckCommand extends Command {
    public StuckCommand() {
        super("stuck", "Unstick!!");
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("stuck").executes((context) -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                playerEntity.getServer().getCommandManager().execute(playerEntity.getCommandSource(), "/warp 1");
                playerEntity.getServer().getCommandManager().execute(playerEntity.getCommandSource(), "/warp spawn");
                return 1;
            }));
    }
}
