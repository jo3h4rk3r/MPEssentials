package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class MutePlayerCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("mute").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 2)) {
               MutePlayerCommand.executeMute(context, context.getSource(), false);
            } else {
               playerEntity.sendMessage(new LiteralText("§cYou do not have access to this command!"), false);
            }

            return 0;
         })));
         dispatcher.register(CommandManager.literal("smute").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 2)) {
               MutePlayerCommand.executeMute(context, context.getSource(), false);
            } else {
               playerEntity.sendMessage(new LiteralText("§cYou do not have access to this command!"), false);
            }

            return 0;
         })));
         dispatcher.register(CommandManager.literal("unmute").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 2)) {
               MutePlayerCommand.executeUnmute(context, context.getSource(), false);
            } else {
               playerEntity.sendMessage(new LiteralText("§cYou do not have access to this command!"), false);
            }

            return 0;
         })));
   }

   private static int executeUnmute(CommandContext<ServerCommandSource> context, ServerCommandSource source, boolean sendFeedback) throws CommandSyntaxException {
      ServerPlayerEntity sender = context.getSource().getPlayer();
      ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "player");
      if (mpe.INSTANCE.getMuteManager().has(String.valueOf(target.getGameProfile().getId()))) {
         mpe.INSTANCE.getMuteManager().remove(String.valueOf(target.getGameProfile().getId()));
         mpe.INSTANCE.getMuteManager().save();
         sender.sendMessage(new LiteralText(String.format("§eUnmuted §7%s", target.getGameProfile().getName())), false);
      } else {
         sender.sendMessage(new LiteralText(String.format("§7%s§e is not muted", target.getGameProfile().getName())), false);
      }

      return 1;
   }

   private static int executeMute(CommandContext<ServerCommandSource> context, ServerCommandSource source, boolean sendFeedback) throws CommandSyntaxException {
      ServerPlayerEntity sender = context.getSource().getPlayer();
      ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "player");
      if (!mpe.INSTANCE.getMuteManager().has(String.valueOf(target.getGameProfile().getId()))) {
         mpe.INSTANCE.getMuteManager().add(String.valueOf(target.getGameProfile().getId()));
         mpe.INSTANCE.getMuteManager().save();
         sender.sendMessage(new LiteralText(String.format("§eMuted §7%s", target.getGameProfile().getName())), false);
      } else {
         sender.sendMessage(new LiteralText(String.format("§7%s§e is already muted", target.getGameProfile().getName())), false);
      }

      return 1;
   }
}
