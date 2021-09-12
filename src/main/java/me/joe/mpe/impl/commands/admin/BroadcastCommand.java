package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.mpe.api.Command;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;

public class BroadcastCommand {



    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,  boolean b) {
        dispatcher.register(CommandManager.literal("broadcast")
                .requires(source -> source.hasPermissionLevel(4)) // Must be a game master to use the command. Command will not show up in tab completion or execute to non op's or any op that is permission level 1.
                        .then(CommandManager.argument("message", greedyString())
                                .executes(ctx -> broadcast(ctx.getSource(), getString(ctx, "message"))))); // You can deal with the arguments out here and pipe them into the command.
    }


    public static int broadcast(ServerCommandSource source, String message) throws CommandSyntaxException {
        Text text = new LiteralText("&6&lBroadcast &8&l| &e&l" + message);
        ServerPlayerEntity playerEntity = source.getPlayer();
    //    source.getMinecraftServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, );
        source.getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, playerEntity.getUuid());
        return 1; // Success
    }


}
