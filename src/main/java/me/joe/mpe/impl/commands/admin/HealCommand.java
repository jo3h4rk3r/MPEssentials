package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class HealCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("heal").executes((context) -> {
                    ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                if (Rank.hasPermission(playerEntity, 2)) {
                    playerEntity.setHealth(20);
                    playerEntity.getHungerManager().add(20, 1);
                    playerEntity.sendMessage(new TranslatableText("§aYou have been healed"), false);
                }
                else {
                    playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
                    playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
                }
                    return 1;

            }));


        dispatcher.register(CommandManager.literal("heal").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");

            if (Rank.hasPermission(playerEntity, 2)) {
                targetedPlayer.setHealth(20);
                targetedPlayer.getHungerManager().add(20, 1);
                targetedPlayer.sendMessage(new TranslatableText("§aYou have been healed"), false);
                playerEntity.sendMessage(new TranslatableText("§aHealed §f" + targetedPlayer.getGameProfile().getName()), false);
            }
            else {
                playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;

        })));
    }
}
