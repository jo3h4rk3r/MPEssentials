package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TradeCommand {
    public static Map<ServerPlayerEntity, Pair<Pair<PlayerEntity, Boolean>, Long>> tradeMap = new HashMap<>();
    public static Map<ServerPlayerEntity, Pair<Pair<PlayerEntity, Boolean>, Long>> tradeMap2 = new HashMap<>();


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("trade").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();

            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            String TargetName = targetedPlayer.getGameProfile().getName();
            String PlayerName = playerEntity.getGameProfile().getName();

            targetedPlayer.sendMessage(new LiteralText(PlayerName + " has requested to trade with you! ").formatted(Formatting.GOLD).append((new LiteralText("[ACCEPT] ")).setStyle((Style.EMPTY).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/trade accept " + playerEntity.getGameProfile().getName())).withColor(Formatting.GREEN))).append((new LiteralText("[DENY] ")).setStyle((Style.EMPTY).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/trade deny " + playerEntity.getGameProfile().getName())).withColor(Formatting.RED))), false);

            // FIX THIS
            playerEntity.sendMessage(new LiteralText("* In Development *"), false);
            targetedPlayer.sendMessage(new LiteralText("* In Development *"), false);
            // FIX THIS

            tradeMap.put(targetedPlayer, new Pair<>(new Pair<>(targetedPlayer, b), (new Date()).getTime()));
            tradeMap2.put(playerEntity, new Pair<>(new Pair<>(playerEntity, b), (new Date()).getTime()));

            if (TradeCommand.hasTradeRequest(targetedPlayer, targetedPlayer)) {
                playerEntity.sendMessage(new LiteralText("You've sent a trade request to " + TargetName).formatted(Formatting.GOLD), false);
            } else {
                playerEntity.sendMessage(new LiteralText("[!] ERROR"), false);
            }


            return 0;
        })));

        dispatcher.register(CommandManager.literal("trade").then(CommandManager.literal("accept").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            String TargetName = targetedPlayer.getGameProfile().getName();
            String PlayerName = playerEntity.getGameProfile().getName();

            if (TradeCommand.hasTradeRequest(playerEntity, playerEntity)) {
                if (TradeCommand.hasTradeRequest2(targetedPlayer, targetedPlayer)) {

                    targetedPlayer.sendMessage(new LiteralText("Trade request accepted! ").formatted(Formatting.GREEN), false);
                    playerEntity.sendMessage(new LiteralText("Trade request accepted! ").formatted(Formatting.GREEN), false);
                   // playerEntity.openHandledScreen(TradeWindow(targetedPlayer, playerEntity));
                    //targetedPlayer.openHandledScreen(TradeWindow(playerEntity));




                    tradeMap2.remove(targetedPlayer);
                    tradeMap.remove(playerEntity);
                }
                else {
                    playerEntity.sendMessage(new LiteralText("You have no trade request ").formatted(Formatting.GRAY), false);
                }
            } else {
                playerEntity.sendMessage(new LiteralText("You have no trade request ").formatted(Formatting.GRAY), false);
            }
            return 0;
        }))));
        dispatcher.register(CommandManager.literal("trade").then(CommandManager.literal("deny").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            String TargetName = targetedPlayer.getGameProfile().getName();
            String PlayerName = playerEntity.getGameProfile().getName();
            if (TradeCommand.hasTradeRequest(playerEntity, playerEntity)) {
                if (TradeCommand.hasTradeRequest2(targetedPlayer, targetedPlayer)) {
                    targetedPlayer.sendMessage(new LiteralText("Trade request denied! ").formatted(Formatting.RED), false);
                    playerEntity.sendMessage(new LiteralText("Trade request denied! ").formatted(Formatting.RED), false);
                    tradeMap2.remove(targetedPlayer);
                    tradeMap.remove(playerEntity);
                }
                else {
                    playerEntity.sendMessage(new LiteralText("You have no trade request ").formatted(Formatting.GRAY), false);
                }
            } else {
                playerEntity.sendMessage(new LiteralText("You have no trade request ").formatted(Formatting.GRAY), false);
            }
            return 0;
        }))));
    }

    private static NamedScreenHandlerFactory TradeWindow(ServerPlayerEntity playerEntity, ServerPlayerEntity targetedPlayer) {

        return null;
    }

    private static boolean hasTradeRequest(ServerPlayerEntity source, ServerPlayerEntity victim) {
        if (tradeMap.containsKey(source) && tradeMap.get(source).getLeft().getLeft().equals(victim)) {
            if ((new Date()).getTime() - (tradeMap.get(source)).getRight() <= 60000L) {
                return true;
            }

            tradeMap.remove(source);
        }

        return false;
    }
    private static boolean hasTradeRequest2(ServerPlayerEntity source, ServerPlayerEntity victim) {
        if (tradeMap2.containsKey(source) && tradeMap2.get(source).getLeft().getLeft().equals(victim)) {
            if ((new Date()).getTime() - (tradeMap2.get(source)).getRight() <= 60000L) {
                return true;
            }

            tradeMap2.remove(source);
        }

        return false;
    }
}
