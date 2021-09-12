package me.joe.mpe.impl.commands.misc;

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
import net.minecraft.text.LiteralText;

public class PlayerNickCommand {


   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("punnick").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            String name = String.valueOf(targetedPlayer.getGameProfile().getId());
            if (Rank.hasPermission(playerEntity, 2)) {
               mpe.INSTANCE.getNickManager().remove(name);
               playerEntity.sendMessage(new LiteralText(String.format("§eRemoved §7%s's §enickname", targetedPlayer.getGameProfile().getName())), false);
               mpe.INSTANCE.getNickManager().save();
            } else {
               playerEntity.sendMessage(new LiteralText("§cYou do not have required permissions!"), false);
            }

            return 1;
         })));
         dispatcher.register(CommandManager.literal("pnick").then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("name", StringArgumentType.string()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            String argName = StringArgumentType.getString(context, "name");
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            String name = String.valueOf(targetedPlayer.getGameProfile().getId());
            if (Rank.hasPermission(playerEntity, 2)) {
               mpe.INSTANCE.getNickManager().add(name, argName);
               playerEntity.sendMessage(new LiteralText(String.format("§eChanged §7%s's §enickname to §7%s", targetedPlayer.getGameProfile().getName(), argName)), false);
               mpe.INSTANCE.getNickManager().save();
            } else {
               playerEntity.sendMessage(new LiteralText("§cYou do not have required permissions!"), false);
            }

            return 1;
         }))));
   }
}
