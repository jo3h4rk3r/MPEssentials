package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

public class ParticlesCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("particles").executes((context) -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();

                if (Rank.hasPermission(playerEntity, 0)) {


                    playerEntity.sendMessage(new TranslatableText("§aParticals!"), false);
                    context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "trigger particals");
                        } else {
                            playerEntity.sendMessage(new TranslatableText("§4You do not have access to this command."), false);
                            playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
                        }

                return 1;
            }));
        dispatcher.register(CommandManager.literal("particles").then(CommandManager.literal("flame").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 0)) {
                playerEntity.sendMessage(new TranslatableText("§aFlame particals enabled"), false);
                context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "trigger particals set 1");
            } else {
                playerEntity.sendMessage(new TranslatableText("§4You do not have access to this command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        })));
        dispatcher.register(CommandManager.literal("particles").then(CommandManager.literal("soulflame").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 0)) {
                playerEntity.sendMessage(new TranslatableText("§aSoulFlame particals enabled"), false);
                context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "trigger particals set 2");
            } else {
                playerEntity.sendMessage(new TranslatableText("§4You do not have access to this command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        })));
        dispatcher.register(CommandManager.literal("particles").then(CommandManager.literal("enderdust").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 0)) {
                playerEntity.sendMessage(new TranslatableText("§aEnderdust particals enabled"), false);
                context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "trigger particals set 3");
            } else {
                playerEntity.sendMessage(new TranslatableText("§4You do not have access to this command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        })));
        dispatcher.register(CommandManager.literal("particles").then(CommandManager.literal("obtears").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 0)) {
                playerEntity.sendMessage(new TranslatableText("§aTears particals enabled"), false);
                context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "trigger particals set 4");
            } else {
                playerEntity.sendMessage(new TranslatableText("§4You do not have access to this command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        })));
        dispatcher.register(CommandManager.literal("particles").then(CommandManager.literal("lava").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 0)) {
                playerEntity.sendMessage(new TranslatableText("§aLava particals enabled"), false);
                context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "trigger particals set 5");
            } else {
                playerEntity.sendMessage(new TranslatableText("§4You do not have access to this command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        })));
        dispatcher.register(CommandManager.literal("particles").then(CommandManager.literal("endercloud").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 0)) {
                playerEntity.sendMessage(new TranslatableText("§aEndercloud particals enabled"), false);
                context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "trigger particals set 6");
            } else {
                playerEntity.sendMessage(new TranslatableText("§4You do not have access to this command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        })));
        dispatcher.register(CommandManager.literal("particles").then(CommandManager.literal("?").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 0)) {
                playerEntity.sendMessage(new TranslatableText("§a? particals enabled"), false);
                context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "trigger particals set 7");
            } else {
                playerEntity.sendMessage(new TranslatableText("§4You do not have access to this command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        })));
        dispatcher.register(CommandManager.literal("particles").then(CommandManager.literal("spores").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 0)) {
                playerEntity.sendMessage(new TranslatableText("§aSpore particals enabled"), false);
                context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "trigger particals set 8");
            } else {
                playerEntity.sendMessage(new TranslatableText("§4You do not have access to this command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        })));
        dispatcher.register(CommandManager.literal("particles").then(CommandManager.literal("off").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 0)) {
                playerEntity.sendMessage(new TranslatableText("§aParticals disabled"), false);
                context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "trigger particals set 9");
            } else {
                playerEntity.sendMessage(new TranslatableText("§4You do not have access to this command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        })));
    }
}
