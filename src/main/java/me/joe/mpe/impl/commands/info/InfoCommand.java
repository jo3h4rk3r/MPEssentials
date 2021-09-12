package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class InfoCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("info").executes((context) -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                playerEntity.sendMessage(new TranslatableText("§aUse '/rules' to read the rules"), false);
                playerEntity.sendMessage(new TranslatableText("§aUse '/help' for a list of commands"), false);
                playerEntity.sendMessage(new TranslatableText("§aUse '/discord' to join our community"), false);
                return 1;
            }));
    }
}