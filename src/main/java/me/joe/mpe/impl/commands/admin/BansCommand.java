package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;

import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BansCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("b").then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("reason", StringArgumentType.string()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            String reason = StringArgumentType.getString(context, "reason");
            String displayName = playerEntity.getGameProfile().getName();
            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
            if (Rank.hasPermission(playerEntity, 2)) {
                String name2 = targetedPlayer.getGameProfile().getName();
                String name = String.valueOf(playerEntity.getGameProfile().getId());

                context.getSource().getServer().getCommandManager().execute(targetedPlayer.getCommandSource().withMaxLevel(4).withSilent(), "execute at " + name2 + " run summon minecraft:lightning_bolt ~ ~ ~");
                targetedPlayer.networkHandler.disconnect(new LiteralText(reason));
                context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "ban " + name2 + " " + reason);


                if (mpe.INSTANCE.getNickManager().has(name)) {
                    displayName = "" + mpe.INSTANCE.getNickManager().get(name);
                }
                Text text = new LiteralText(rank.prefix + "§f" + displayName + " §7banned player §e" + name2 + "§7 for reason §e" + reason);
                context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, playerEntity.getUuid());
            } else {
                playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 0;
        }))));



        /* dispatcher.register(CommandManager.literal("b").then(CommandManager.argument("player", GameProfileArgumentType.gameProfile()).then(CommandManager.argument("reason", StringArgumentType.greedyString()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            String reason = StringArgumentType.getString(context, "reason");
            String displayName = playerEntity.getGameProfile().getName();
            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
            if (Rank.hasPermission(playerEntity, 1)) {
                String target = targetedPlayer.getGameProfile().getName();
                //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                //LocalDateTime now = LocalDateTime.now();
                String name = String.valueOf(playerEntity.getGameProfile().getId());
                if (mpe.INSTANCE.getNickManager().has(name)) {
                    displayName = "" + mpe.INSTANCE.getNickManager().get(name);
                }

                //mpe.INSTANCE.getBansManager().add(playerEntity.getGameProfile().getName() + " banned player " + target + " reason: " + reason + " date: " + dtf.format(now));
                //mpe.INSTANCE.getBansManager().save();
                Text text = new LiteralText(rank.prefix + "§f" + displayName + " §7banned player §e" + target + "§7 for reason §e" + reason);
                context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, playerEntity.getUuid());
                //context.getSource().getMinecraftServer().getCommandManager().execute(targetedPlayer.getCommandSource().withMaxLevel(4).withSilent(), "execute at " + name2 + " run summon minecraft:lightning_bolt ~ ~ ~");
                targetedPlayer.networkHandler.disconnect(new LiteralText(reason));
                context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "ban " + target + " " + reason);
            } else {
                playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 0;
        }))));

        */

        dispatcher.register(CommandManager.literal("unban").then(CommandManager.argument("player", StringArgumentType.greedyString()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            String target = StringArgumentType.getString(context, "player");
            //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            //LocalDateTime now = LocalDateTime.now();
            // ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            if (Rank.hasPermission(playerEntity, 2)) {
                //    String name2 = targetedPlayer.getGameProfile().getName();

                playerEntity.sendMessage(new LiteralText("§7Unbanned player &e" + target), false);
                context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4), "pardon " + target);


                //mpe.INSTANCE.getBansManager().add(playerEntity.getGameProfile().getName() + " unbanned player "  + target + " DATE: " + dtf.format(now));
                //mpe.INSTANCE.getBansManager().save();
            } else {
                playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 0;
        })));

    }
}
