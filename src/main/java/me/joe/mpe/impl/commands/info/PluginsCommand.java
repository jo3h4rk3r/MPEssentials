package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class PluginsCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("plugins").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();

            playerEntity.sendMessage(new TranslatableText("§aPlugins§8: §7MPEssentials, §fLithium, §7Worldedit, §fFabricProxy, §7fabric-api"), false);


            return 1;
        }));
    }
}
