package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class VersionCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("version").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();

            playerEntity.sendMessage(new TranslatableText("§7Core: Fabric-1.16.3"), false);
            playerEntity.sendMessage(new TranslatableText("§7API: fabric-api-0.22.2+build.396-1.16.3"), false);

            return 1;
        }));
    }
}
