package me.joe.mpe.impl.commands.misc;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static java.util.function.Function.identity;

public class KitCommand {
   // private static final HoverEvent.Action SHOW_TEXT = context.getSource().getPlayer();
   private static final long KIT_TIMEOUT = 1000 * 12600;
    public static Map<UUID, Long> KIT_TIMEOUT_MAP = new HashMap<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("kit").executes((context) -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                ServerPlayerEntity sender = context.getSource().getPlayer();
                Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));

                if (Rank.hasHat(rank)) {

                    playerEntity.sendMessage(new LiteralText("§eKits§7:§f start, donor, vip, vip+, mvp"), false);




                } else {
                    playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
                    playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
                }

                return 1;
            }));
        dispatcher.register(CommandManager.literal("kit").then(CommandManager.literal("start").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity sender = context.getSource().getPlayer();

            long current = System.currentTimeMillis();
            Long lastRtp_wild = KIT_TIMEOUT_MAP.get(playerEntity.getUuid());

            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));

            if (lastRtp_wild == null || current - lastRtp_wild > KIT_TIMEOUT) {
                KIT_TIMEOUT_MAP.put(playerEntity.getUuid(), current);


                    playerEntity.getInventory().insertStack(new ItemStack(Items.BREAD));
                    playerEntity.sendMessage(new LiteralText("§eReceived kit §6start!"), false);



            } else {
                long timeRemaining = 1 + ((KIT_TIMEOUT - (current - lastRtp_wild)) / 1000);
                playerEntity.sendMessage((new TranslatableText("§cYou can't use this kit again")).setStyle((Style.EMPTY)), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }


            return 1;
        })));
        dispatcher.register(CommandManager.literal("kit").then(CommandManager.literal("donor").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity sender = context.getSource().getPlayer();

            long current = System.currentTimeMillis();
            Long lastRtp_wild = KIT_TIMEOUT_MAP.get(playerEntity.getUuid());

            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));

            if (Rank.hasHat(rank)) {
            if (lastRtp_wild == null || current - lastRtp_wild > KIT_TIMEOUT) {
                KIT_TIMEOUT_MAP.put(playerEntity.getUuid(), current);


            playerEntity.getInventory().insertStack(new ItemStack(Items.DIAMOND_HELMET));
                   playerEntity.getInventory().insertStack(new ItemStack(Items.DIAMOND_CHESTPLATE));
                   playerEntity.getInventory().insertStack(new ItemStack(Items.DIAMOND_LEGGINGS));
                   playerEntity.getInventory().insertStack(new ItemStack(Items.DIAMOND_BOOTS));
                   playerEntity.getInventory().insertStack(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                   playerEntity.getInventory().insertStack(new ItemStack(Items.DIAMOND_SWORD));
                   playerEntity.getInventory().insertStack(new ItemStack(Items.DIAMOND_PICKAXE));
                   playerEntity.getInventory().insertStack(new ItemStack(Items.DIAMOND_AXE));
                   playerEntity.getInventory().insertStack(new ItemStack(Items.DIAMOND_SHOVEL));



                   playerEntity.getInventory().insertStack(new ItemStack(Items.SUNFLOWER));
            playerEntity.sendMessage(new LiteralText("§eReceived kit §6Donor"), false);



        } else {
            long timeRemaining = 1 + ((KIT_TIMEOUT - (current - lastRtp_wild)) / 1000);
            playerEntity.sendMessage((new TranslatableText("§cYou can't use this kit again")).setStyle((Style.EMPTY)), false);
        }
            } else {
                playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
            }

            return 1;
        })));
        dispatcher.register(CommandManager.literal("kit").then(CommandManager.literal("vip").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity sender = context.getSource().getPlayer();


            long current = System.currentTimeMillis();
            Long lastRtp_wild = KIT_TIMEOUT_MAP.get(playerEntity.getUuid());

            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));

            if (Rank.hasHat(rank)) {

            if (lastRtp_wild == null || current - lastRtp_wild > KIT_TIMEOUT) {
                KIT_TIMEOUT_MAP.put(playerEntity.getUuid(), current);




                playerEntity.getInventory().insertStack(new ItemStack(Items.ROSE_BUSH));
                playerEntity.sendMessage(new LiteralText("§eReceived kit §6VIP"), false);


            } else {
                long timeRemaining = 1 + ((KIT_TIMEOUT - (current - lastRtp_wild)) / 1000);
                playerEntity.sendMessage((new TranslatableText("§cYou can't use this kit again")).setStyle((Style.EMPTY)), false);
            }
            } else {
                playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
            }

            return 1;
        })));
        dispatcher.register(CommandManager.literal("kit").then(CommandManager.literal("vip+").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity sender = context.getSource().getPlayer();
            long current = System.currentTimeMillis();
            Long lastRtp_wild = KIT_TIMEOUT_MAP.get(playerEntity.getUuid());

            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));

            if (Rank.hasHat(rank)) {
            if (lastRtp_wild == null || current - lastRtp_wild > KIT_TIMEOUT) {
                KIT_TIMEOUT_MAP.put(playerEntity.getUuid(), current);


                playerEntity.getInventory().insertStack(new ItemStack(Items.POPPY));
                playerEntity.sendMessage(new LiteralText("§eReceived kit §6VIP+"), false);


            } else {
                long timeRemaining = 1 + ((KIT_TIMEOUT - (current - lastRtp_wild)) / 1000);
                playerEntity.sendMessage((new TranslatableText("§cYou can't use this kit again")).setStyle((Style.EMPTY)), false);
            }
            } else {
                playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
            }

            return 1;
        })));
        dispatcher.register(CommandManager.literal("kit").then(CommandManager.literal("mvp").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity sender = context.getSource().getPlayer();
            long current = System.currentTimeMillis();
            Long lastRtp_wild = KIT_TIMEOUT_MAP.get(playerEntity.getUuid());

            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));

            if (Rank.hasHat(rank)) {
            if (lastRtp_wild == null || current - lastRtp_wild > KIT_TIMEOUT) {
                KIT_TIMEOUT_MAP.put(playerEntity.getUuid(), current);


                playerEntity.getInventory().insertStack(new ItemStack(Items.CORNFLOWER));
                playerEntity.sendMessage(new LiteralText("§eReceived kit §6MVP"), false);


            } else {
                long timeRemaining = 1 + ((KIT_TIMEOUT - (current - lastRtp_wild)) / 1000);
                playerEntity.sendMessage((new TranslatableText("§cYou can't use this kit again")).setStyle((Style.EMPTY)), false);
            }
            } else {
                playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
            }
            return 1;
        })));
    }
}
