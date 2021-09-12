package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class WeatherCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(
                    CommandManager.literal("sun")
                            .requires(source -> source.hasPermissionLevel(4))
                            .executes(context -> {
                                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                                playerEntity.getServer().getCommandManager().execute(playerEntity.getCommandSource(), "/weather clear");
                                return 1;
                            }));
            dispatcher.register(
                    CommandManager.literal("rain")
                            .requires(source -> source.hasPermissionLevel(4))
                            .executes(context -> {
                                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                                playerEntity.getServer().getCommandManager().execute(playerEntity.getCommandSource(), "/weather rain");
                                return 1;
                            }));
    }
}