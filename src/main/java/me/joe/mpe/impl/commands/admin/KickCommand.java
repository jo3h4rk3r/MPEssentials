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

public class KickCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("k").then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("reason", StringArgumentType.string()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            String reason = StringArgumentType.getString(context, "reason");
            String displayName = playerEntity.getGameProfile().getName();
            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
            if (Rank.hasPermission(playerEntity, 2)) {
               String name2 = targetedPlayer.getGameProfile().getName();
               String name = String.valueOf(playerEntity.getGameProfile().getId());
               targetedPlayer.networkHandler.disconnect(new LiteralText(reason));
               if (mpe.INSTANCE.getNickManager().has(name)) {
                  displayName = "" + mpe.INSTANCE.getNickManager().get(name);
               }
               Text text = new LiteralText(rank.prefix + "§f" + displayName + " §7kicked player §e" + name2 + "§7 for reason §e" + reason);
               context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, playerEntity.getUuid());
            } else {
               playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
            }

            return 0;
         }))));
         dispatcher.register(CommandManager.literal("k").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            if (Rank.hasPermission(playerEntity, 2)) {
               String name2 = targetedPlayer.getGameProfile().getName();
               targetedPlayer.networkHandler.disconnect(new LiteralText("You have been kicked"));
               playerEntity.sendMessage(new LiteralText("§fKicked player §e" + name2), false);
            } else {
               playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);

            }

            return 0;
         })));
   }
}
