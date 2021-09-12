package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;

public class BMeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("me")
                .then(CommandManager.argument("message", greedyString())
                        .executes(ctx -> broadcast(ctx.getSource(), getString(ctx, "message")))));
    }


    public static int broadcast(ServerCommandSource source, String message) throws CommandSyntaxException {


        ServerPlayerEntity player = source.getPlayer();
        Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(player.getGameProfile().getId()));
        String name = String.valueOf(player.getGameProfile().getId());
        String displayName = player.getGameProfile().getName();

        if (player.hasPermissionLevel(4)) {
            if (mpe.INSTANCE.getNickManager().has(name)) {
                displayName = "" + mpe.INSTANCE.getNickManager().get(name);
            }
            Text text = new LiteralText("§a(*) " + rank.prefix + "§f" + displayName + " §f: §f" + message);
            source.getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, player.getUuid());
        } else {
            player.sendMessage(new LiteralText("&cYou do not have access to this command"), false);
        }
            return 1;

    }


}

