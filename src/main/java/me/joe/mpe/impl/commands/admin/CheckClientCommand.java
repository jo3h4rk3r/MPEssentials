package me.joe.mpe.impl.commands.admin;

import com.mojang.authlib.properties.PropertyMap;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.loader.game.MinecraftGameProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.MinecraftClientGame;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

public class CheckClientCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("check").requires((source) -> source.hasPermissionLevel(4)).then(CommandManager.literal("client").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");

            String playerName = playerEntity.getGameProfile().getName();
            String TargetName = targetedPlayer.getGameProfile().getName();

            if (context.getSource().hasPermissionLevel(4)) {




            } else {
                System.out.println("A NON-OP Player tried to set a rank");
                playerEntity.sendMessage(new LiteralText("§cYou do not have access to this command"), false);
            }


            return 1;
        }))));
        dispatcher.register(CommandManager.literal("check").requires((source) -> source.hasPermissionLevel(4)).then(CommandManager.literal("ip").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");

            String playerName = playerEntity.getGameProfile().getName();
            String TargetName = targetedPlayer.getGameProfile().getName();
            if (context.getSource().hasPermissionLevel(4)) {



                String TargetIP = targetedPlayer.getIp();

                playerEntity.sendMessage(new LiteralText(TargetName + "'s IP is: " + TargetIP), false);

            } else {
                System.out.println("A NON-OP Player tried to set a rank");
                playerEntity.sendMessage(new LiteralText("§cYou do not have access to this command"), false);
            }


            return 1;
        }))));
    }
}
