package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.mpe.api.Rank;
import me.joe.mpe.framework.ModCore;
import me.joe.mpe.impl.mpe;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.*;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import static me.joe.mpe.impl.commands.misc.IgnoreCommand.msgIgnore;
import static me.joe.mpe.impl.commands.tpa.TpaIgnore.tpaIgnore;

public class MsgCommand {

    private static HashMap<UUID, UUID> msg = new HashMap<UUID, UUID>();
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {

            dispatcher.register(CommandManager.literal("message").then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("message", StringArgumentType.greedyString()).executes((context)  -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                MsgCommand.executeMsg(context, context.getSource(), false);
                return 0;
            }))));
            dispatcher.register(CommandManager.literal("m").then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("message", StringArgumentType.greedyString()).executes((context)  -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                MsgCommand.executeMsg(context, context.getSource(), false);
                return 0;
            }))));
            dispatcher.register(CommandManager.literal("r").then(CommandManager.argument("message", StringArgumentType.greedyString()).executes((context)  -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                MsgCommand.executeReply(context, context.getSource(), false);
                return 0;
            })));
            dispatcher.register(CommandManager.literal("reply").then(CommandManager.argument("message", StringArgumentType.greedyString()).executes((context)  -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                MsgCommand.executeReply(context, context.getSource(), false);
                return 0;
            })));
            dispatcher.register(CommandManager.literal("w").then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("message", StringArgumentType.greedyString()).executes((context)  -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                MsgCommand.executeMsg(context, context.getSource(), false);
                return 0;
            }))));
            dispatcher.register(CommandManager.literal("tell").then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("message", StringArgumentType.greedyString()).executes((context)  -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                MsgCommand.executeMsg(context, context.getSource(), false);
                return 0;
            }))));
            dispatcher.register(CommandManager.literal("whisper").then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("message", StringArgumentType.greedyString()).executes((context)  -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                MsgCommand.executeMsg(context, context.getSource(), false);
                return 0;
            }))));
            dispatcher.register(CommandManager.literal("msg").then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("message", StringArgumentType.greedyString()).executes((context)  -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                MsgCommand.executeMsg(context, context.getSource(), false);
                return 0;
            }))));




    }
    private static int executeMsg(CommandContext<ServerCommandSource> context, ServerCommandSource source, boolean sendFeedback) throws CommandSyntaxException {
        ServerPlayerEntity playerEntity = context.getSource().getPlayer();
        ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
        Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
        Rank rank2 = mpe.INSTANCE.getRankManager().get(String.valueOf(targetedPlayer.getGameProfile().getId()));
        String name = String.valueOf(playerEntity.getGameProfile().getId());
        String name2 = String.valueOf(targetedPlayer.getGameProfile().getId());
        String message = StringArgumentType.getString(context, "message");

        ServerPlayerEntity playerTarget = playerEntity.getServer().getPlayerManager().getPlayer(msg.get(playerEntity.getUuid()));
        String displayName = playerEntity.getGameProfile().getName();
       // MutableText displayNameHoverText = new TranslatableText(String.valueOf(playerEntity.getGameProfile().getName()));
        //TranslatableText NameHover = (new TranslatableText("" + rank.prefix + "§f" + playerEntity.getGameProfile().getName()));
        if (mpe.INSTANCE.getNickManager().has(name)) {
            displayName = "" + mpe.INSTANCE.getNickManager().get(name);
           // displayNameHoverText = new TranslatableText("%s", displayName).setStyle((Style.EMPTY).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, NameHover)).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + playerEntity.getGameProfile().getName() + " ")));
        }



        String displayName2 = targetedPlayer.getGameProfile().getName();
        if (mpe.INSTANCE.getNickManager().has(name2)) {
            displayName2 = "" + mpe.INSTANCE.getNickManager().get(name2);
        }
        UUID player = playerEntity.getUuid();
        UUID target = targetedPlayer.getUuid();
        //if(!ModCore.tpData.ignoreMap.containsKey(target)){





      //  if (!(msgIgnore.containsValue(playerTarget.getUuid()))) {
            msg.put(target, player);

            playerEntity.sendMessage(new LiteralText("&e&l&oMessage sent to&6&l&o " + displayName2), true);
            playerEntity.sendMessage(new LiteralText("§6§o[§f" + rank2.prefix + "§f" + displayName2 + "§6§o]§7§o <- " + rank.prefix + "§f" + displayName + "§8: §e§o" + message), false);
            if (mpe.INSTANCE.getAFKManager().has(String.valueOf(targetedPlayer.getGameProfile().getName()))) {
                playerEntity.sendMessage(new LiteralText("&cThis player is currently AFK and may not respond!"), false);
            }
            TranslatableText NameHover = (new TranslatableText("Reply to " + rank.prefix + "§f" + playerEntity.getGameProfile().getName()));
            targetedPlayer.sendMessage(new LiteralText("&e&l&oMessage from&6&l&o " + displayName), true);
            targetedPlayer.sendMessage(new LiteralText(rank.prefix + "§f" + displayName + "§7§o -> " + "§6§o[Me]" + "§8: §e§o" + message).setStyle((Style.EMPTY).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, NameHover)).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + playerEntity.getGameProfile().getName() + " "))), false);
            //targetedPlayer.sendMessage(new LiteralText("§a[REPLY]").setStyle((Style.EMPTY).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, NameHover)).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + playerEntity.getGameProfile().getName() + " "))), false);
            targetedPlayer.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.BLOCKS, 1f, 1f);

            source.getServer().sendSystemMessage(new LiteralText("[PRIVATE MESSAGE] " + playerEntity.getGameProfile().getName() + " -> " + targetedPlayer.getGameProfile().getName() + " : " + message), playerEntity.getUuid());
     //   } else {
      //      playerEntity.sendMessage(new LiteralText("&cThis player has blocked your messages"), false);
      //  }


        return 1;
    }
    private static void executeReply(CommandContext<ServerCommandSource> context, ServerCommandSource source, boolean sendFeedback) throws CommandSyntaxException {
        ServerPlayerEntity playerEntity = context.getSource().getPlayer();
        ServerPlayerEntity playerTarget = playerEntity.getServer().getPlayerManager().getPlayer(msg.get(playerEntity.getUuid()));
        Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
        Rank rank2 = mpe.INSTANCE.getRankManager().get(String.valueOf(playerTarget.getGameProfile().getId()));
        String name = String.valueOf(playerEntity.getGameProfile().getId());
        String name2 = String.valueOf(playerTarget.getGameProfile().getId());
        String message = StringArgumentType.getString(context, "message");
        UUID player = playerEntity.getUuid();
        UUID target = playerTarget.getUuid();
        //UUID senderUUID = context.getSource().getPlayer().getUuid();


        String displayName = playerEntity.getGameProfile().getName();
        if (mpe.INSTANCE.getNickManager().has(name)) {
            displayName = "" + mpe.INSTANCE.getNickManager().get(name);
            // displayNameHoverText = new TranslatableText("%s", displayName).setStyle((Style.EMPTY).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, NameHover)).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + playerEntity.getGameProfile().getName() + " ")));
        }



        String displayName2 = playerTarget.getGameProfile().getName();
        if (mpe.INSTANCE.getNickManager().has(name2)) {
            displayName2 = "" + mpe.INSTANCE.getNickManager().get(name2);
        }
       // UUID player = playerEntity.getUuid();
       // UUID target = playerTarget.getUuid();




       // if (!(msgIgnore.containsValue(playerTarget.getUuid()))) {
        //if(!ModCore.tpData.ignoreMap.containsKey(target)){
            msg.put(target, player);
            if (msg.containsValue(target)) {
                playerEntity.sendMessage(new LiteralText("&e&l&oMessage sent to&6&l&o " + displayName2), true);
                playerEntity.sendMessage(new LiteralText("§6§o[§f" + rank2.prefix + "§f" + displayName2 + "§6§o]§7§o <- " + rank.prefix + "§f" + displayName + "§8: §e§o" + message), false);
                if (mpe.INSTANCE.getAFKManager().has(String.valueOf(playerTarget.getGameProfile().getName()))) {
                    playerEntity.sendMessage(new LiteralText("&cThis player is currently AFK and may not respond!"), false);
                }
                TranslatableText NameHover = (new TranslatableText("Reply to " + rank.prefix + "§f" + playerEntity.getGameProfile().getName()));
                playerTarget.sendMessage(new LiteralText("&e&l&oMessage from&6&l&o " + displayName), true);
                playerTarget.sendMessage(new LiteralText(rank.prefix + "§f" + displayName + "§7§o -> " + "§6§o[Me]" + "§8: §e§o" + message).setStyle((Style.EMPTY).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, NameHover)).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + playerEntity.getGameProfile().getName() + " "))), false);
                //targetedPlayer.sendMessage(new LiteralText("§a[REPLY]").setStyle((Style.EMPTY).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, NameHover)).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + playerEntity.getGameProfile().getName() + " "))), false);
                playerTarget.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.BLOCKS, 1f, 1f);
                source.getServer().sendSystemMessage(new LiteralText("[PRIVATE MESSAGE] " + playerEntity.getGameProfile().getName() + " -> " + playerTarget.getGameProfile().getName() + " : " + message), playerEntity.getUuid());
                // msg.remove(playerTarget.getUuid(), playerEntity.getUuid());

            } else {
                playerEntity.sendMessage(new LiteralText("&eYou have no one to reply to"), true);
            }
      //  } else {
     //   playerEntity.sendMessage(new LiteralText("&cThis player has blocked your messages"), false);
    //}
       // return 1;

    }

}
