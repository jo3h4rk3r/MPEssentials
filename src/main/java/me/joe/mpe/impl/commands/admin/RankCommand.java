package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class RankCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register((CommandManager.literal("rank").requires(
                 (source) -> source.hasPermissionLevel(4))
         ).then(CommandManager.argument("cmd", StringArgumentType.string()).then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("rank", StringArgumentType.string()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            String arg = StringArgumentType.getString(context, "cmd");
            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
            String rank = StringArgumentType.getString(context, "rank");
            String var5 = arg.toLowerCase();
            byte var6 = -1;
            switch(var5.hashCode()) {
            case -1335458389:
               if (var5.equals("delete")) {
                  var6 = 7;
               }
               break;
            case -934610812:
               if (var5.equals("remove")) {
                  var6 = 6;
               }
               break;
            case 97:
               if (var5.equals("a")) {
                  var6 = 0;
               }
               break;
            case 96417:
               if (var5.equals("add")) {
                  var6 = 1;
               }
               break;
            case 99339:
               if (var5.equals("del")) {
                  var6 = 8;
               }
               break;
            case 112794:
               if (var5.equals("rem")) {
                  var6 = 5;
               }
               break;
            case 113762:
               if (var5.equals("set")) {
                  var6 = 3;
               }
               break;
            case 3173137:
               if (var5.equals("give")) {
                  var6 = 2;
               }
               break;
            case 94746189:
               if (var5.equals("clear")) {
                  var6 = 4;
               }
            }

            switch(var6) {
            case 0:
            case 1:
            case 2:
            case 3:
               mpe.INSTANCE.getRankManager().add(String.valueOf(player.getGameProfile().getId()), Rank.getRank(rank));
               playerEntity.sendMessage(new TranslatableText(String.format("%s has been given %s rank", player.getGameProfile().getName(), rank.toUpperCase())), false);
               break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
               mpe.INSTANCE.getRankManager().remove(String.valueOf(player.getGameProfile().getId()));
               mpe.INSTANCE.getRankManager().add(String.valueOf(player.getGameProfile().getId()), Rank.GUEST);
               playerEntity.sendMessage(new TranslatableText(String.format("%s has had rank reset to %s", player.getGameProfile().getName(), "GUEST")), false);
            }

            mpe.INSTANCE.getRankManager().save();
            return 1;
         })))));
   }
}
