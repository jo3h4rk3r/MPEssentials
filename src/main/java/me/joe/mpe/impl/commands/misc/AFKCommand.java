package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.api.event.data.interfaces.Subscribe;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.framework.wrapped.IServerPlayerEntity;
import me.joe.mpe.impl.events.PlayerTickEvent;
import me.joe.mpe.impl.mpe;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.GameMode;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AFKCommand {

    public static String isAFK;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(
                CommandManager.literal("afk")
                        .executes(AFKCommand::run));
    }
    public static void setIsAFK(String isAFK) {
        AFKCommand.isAFK = isAFK;
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity playerEntity = context.getSource().getPlayer();
        ServerPlayerEntity player = context.getSource().getPlayer();
        IServerPlayerEntity iplayer = (IServerPlayerEntity) player;

        String name = String.valueOf(playerEntity.getGameProfile().getId());
        String displayName = playerEntity.getGameProfile().getName();
        if (mpe.INSTANCE.getNickManager().has(name)) {
            displayName = "" + mpe.INSTANCE.getNickManager().get(name);
        }

        if (!mpe.INSTANCE.getAFKManager().has(String.valueOf(playerEntity.getGameProfile().getName()))) {
            mpe.INSTANCE.getAFKManager().add(String.valueOf(playerEntity.getGameProfile().getName()));
//  String name = playerEntity.getGameProfile().getName();
            playerEntity.getAbilities().invulnerable = true;
            mpe.INSTANCE.getAFKManager().save();
            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
            Text text = new LiteralText(rank.prefix + "&r" + displayName + " &7is now AFK*");
            context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, playerEntity.getUuid());
        } else {
            playerEntity.sendMessage(new LiteralText("&eYou're already AFK"), false);
        }
        return 1;
    }
}