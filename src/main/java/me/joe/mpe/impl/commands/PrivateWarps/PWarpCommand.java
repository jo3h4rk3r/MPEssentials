package me.joe.mpe.impl.commands.PrivateWarps;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class PWarpCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("pw").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            playerEntity.sendMessage(new LiteralText("Private Warps: 1, 2, 3, 4"), false);
            return 0;
        }));



        dispatcher.register(CommandManager.literal("pw").then(CommandManager.literal("xp").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            return 0;
        })));
        dispatcher.register(CommandManager.literal("pw").then(CommandManager.literal("lounge").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            return 0;
        })));


    }
}
