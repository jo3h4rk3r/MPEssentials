package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.GameMode;

public class GamemodeCommand {


   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("gm").then(CommandManager.literal("0").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "gamemode survival");
            //playerEntity.setGameMode(GameMode.SURVIVAL);
               playerEntity.sendMessage(new LiteralText("§fGamemode set to §eSurvival Mode"), false);
               System.out.println(playerEntity.getGameProfile().getName() + " has switched into survival mode!" );
            return 1;
         })));
      dispatcher.register(CommandManager.literal("gm").then(CommandManager.literal("1").executes((context) -> {
         ServerPlayerEntity playerEntity = context.getSource().getPlayer();
         if (Rank.hasPermission(playerEntity, 3)) {
            //playerEntity.setGameMode(GameMode.CREATIVE);
            context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "gamemode creative");
            playerEntity.sendMessage(new LiteralText("§fGamemode set to §eCreative Mode"), false);
            System.out.println(playerEntity.getGameProfile().getName() + " has switched into creative mode!" );
         } else {
            playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
         }
         return 1;
      })));
      dispatcher.register(CommandManager.literal("gm").then(CommandManager.literal("2").executes((context) -> {
         ServerPlayerEntity playerEntity = context.getSource().getPlayer();
         //playerEntity.setGameMode(GameMode.ADVENTURE);
         context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "gamemode adventure");
            playerEntity.sendMessage(new LiteralText("§fGamemode set to §eAdventure Mode"), false);
         System.out.println(playerEntity.getGameProfile().getName() + " has switched into adventure mode!" );
         return 1;
      })));
      dispatcher.register(CommandManager.literal("gm").then(CommandManager.literal("3").executes((context) -> {
         ServerPlayerEntity playerEntity = context.getSource().getPlayer();
         if (Rank.hasPermission(playerEntity, 2)) {
            //playerEntity.setGameMode(GameMode.SPECTATOR);
            context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "gamemode spectator");
            playerEntity.sendMessage(new LiteralText("§fGamemode set to §eSpectator Mode"), false);
            System.out.println(playerEntity.getGameProfile().getName() + " has switched into spectator mode!" );
         } else {
            playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
         }
         return 1;
      })));
   }




}
