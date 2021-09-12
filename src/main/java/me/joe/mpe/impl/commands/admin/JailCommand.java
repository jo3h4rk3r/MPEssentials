package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class JailCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("jail").then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("reason", StringArgumentType.string()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            String reason = StringArgumentType.getString(context, "reason");
            String displayName = playerEntity.getGameProfile().getName();
            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
            if (Rank.hasPermission(playerEntity, 1)) {
                String name2 = targetedPlayer.getGameProfile().getName();
                String name = String.valueOf(playerEntity.getGameProfile().getId());

                targetedPlayer.teleport(0,5,0);
                //targetedPlayer.networkHandler.disconnect(new LiteralText(reason));


                if (mpe.INSTANCE.getNickManager().has(name)) {
                    displayName = "" + mpe.INSTANCE.getNickManager().get(name);
                }

                mpe.INSTANCE.getJailManager().add(targetedPlayer.getGameProfile().getName());
                mpe.INSTANCE.getJailManager().save();
                Text text = new LiteralText(rank.prefix + "§f" + displayName + " §7Jailed player §e" + name2 + "§7 for reason §e" + reason);
                context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, playerEntity.getUuid());
            } else {
                playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
            }

            return 0;
        }))));
        dispatcher.register(CommandManager.literal("unjail").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            if (Rank.hasPermission(playerEntity, 2)) {
                String name2 = targetedPlayer.getGameProfile().getName();
                targetedPlayer.teleport(-246.50, 65.00, -51.50);
                playerEntity.sendMessage(new LiteralText("§fUnjailed player §e" + name2), false);
                mpe.INSTANCE.getJailManager().remove(targetedPlayer.getGameProfile().getName());
                mpe.INSTANCE.getJailManager().save();
            } else {
                playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);

            }

            return 0;
        })));
    }
}
