package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.api.server.Server;
import me.joe.mpe.impl.listeners.PlayerTickListener;
import me.joe.mpe.impl.mpe;
import net.fabricmc.fabric.api.registry.CommandRegistry;
//import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.MessageType;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

public class FormatCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("format").executes((context)  -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                FormatCommand.executeFormatCommand(context, context.getSource(), false);
                return 0;
            }));
            dispatcher.register(CommandManager.literal("displayitem").executes((context)  -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                FormatCommand.executeDisplayItem(context, context.getSource(), false);

                return 0;
            }));
    }


    static int executeDisplayItem(CommandContext<ServerCommandSource> context, ServerCommandSource source, boolean sendFeedback) throws CommandSyntaxException {
        ServerPlayerEntity playerEntity = context.getSource().getPlayer();
        Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
        String displayName = playerEntity.getGameProfile().getName();
        String name = String.valueOf(playerEntity.getGameProfile().getId());
        if (mpe.INSTANCE.getNickManager().has(name)) {
            displayName = "" + mpe.INSTANCE.getNickManager().get(name);
        }
        ItemStack itemStack = context.getSource().getPlayer().getStackInHand(Hand.MAIN_HAND);
        if (itemStack == ItemStack.EMPTY) {
            context.getSource().sendFeedback((new LiteralText("You're currently not holding anything!")).formatted(Formatting.RED), false);
        } else {
            Text hoverText = (new LiteralText(rank.prefix + "&f" + displayName + " &ewants to show you their &e").append(itemStack.toHoverableText()));
            context.getSource().getServer().getPlayerManager().broadcastChatMessage(hoverText, MessageType.CHAT, playerEntity.getUuid());
        }
        return 1;
    }


    private static int executeFormatCommand(CommandContext<ServerCommandSource> context, ServerCommandSource source, boolean sendFeedback) throws CommandSyntaxException {
        ServerPlayerEntity playerEntity = context.getSource().getPlayer();
        playerEntity.sendMessage(
                new LiteralText("")
                        .append("Use the \"&\" character following one of the formatting codes for your message to be formatted.\n Use the \"\\\" character to escape format codes.\n")
                        .append("0 is &0black&f, ")
                        .append("1 is &1dark blue&f, ")
                        .append("2 is &2dark green&f, ")
                        .append("3 is &3dark aqua&f, \n")
                        .append("4 is &4dark red&f, ")
                        .append("5 is &5dark purple&f, ")
                        .append("8 is &8dark gray&f\n")
                        .append("----------------------------------------------------\n")
                        .append("6 is &6gold&f, ")
                        .append("7 is &7gray&f, ")
                        .append("9 is &9blue&f, ")
                        .append("a is &agreen&f, ")
                        .append("b is &baqua&f, ")
                        .append("c is &cred&f, \n")
                        .append("d is &dlight purple&f, ")
                        .append("e is &eyellow&f, ")
                        .append("f is &fwhite&f\n")
                        .append("----------------------------------------------------\n")
                        .append("k is &kobfuscated&f, ")
                        .append("l is &lbold&f, ")
                        .append("m is &mstrikethrough&f, ")
                        .append("n is &nunderline&f, \n")
                        .append("o is &oitalic&f, ")
                , false);

        return 1;

    }
}
