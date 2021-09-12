package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class ClearChatCommands {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("cc").executes((context) -> {
            ServerPlayerEntity player = context.getSource().getPlayer();
            if (Rank.hasPermission(player, 2)) {
               for(int i = 0; i < 100; ++i) {
                  Text text = new LiteralText(" ");
                  context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, player.getUuid());
               }
            } else {
               player.sendMessage(new LiteralText("§4You do not have access to that command."), false);
               player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 0;
         }));
         dispatcher.register(CommandManager.literal("cca").executes((context) -> {
            ServerPlayerEntity player = context.getSource().getPlayer();
            if (Rank.hasPermission(player, 2)) {
               for(int i = 0; i < 100; ++i) {
                  Text text = new LiteralText(" ");
                  context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, player.getUuid());
               }
            } else {
               player.sendMessage(new LiteralText("§4You do not have access to that command."), false);
               player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 0;
         }));
         dispatcher.register(CommandManager.literal("clearallchat").executes((context) -> {
            ServerPlayerEntity player = context.getSource().getPlayer();
            if (Rank.hasPermission(player, 2)) {
               for(int i = 0; i < 100; ++i) {
                  Text text = new LiteralText(" ");
                  context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, player.getUuid());
               }
            } else {
               player.sendMessage(new LiteralText("§4You do not have access to that command."), false);
               player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 0;
         }));
         dispatcher.register(CommandManager.literal("clearserverchat").executes((context) -> {
            ServerPlayerEntity player = context.getSource().getPlayer();
            if (Rank.hasPermission(player, 2)) {
               for(int i = 0; i < 100; ++i) {
                  Text text = new LiteralText(" ");
                  context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, player.getUuid());
               }
            } else {
               player.sendMessage(new LiteralText("§4You do not have access to that command."), false);
               player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 0;
         }));
   }
}
