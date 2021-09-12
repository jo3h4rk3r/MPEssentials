package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class RanksCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("ranks").executes((context) -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                playerEntity.getServer().getCommandManager().execute(playerEntity.getCommandSource(), "/warp ranks");
                return 1;
            }));
    }
}