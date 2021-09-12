package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
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
import net.minecraft.world.GameMode;

public class FlyCommand {
    private static int count = 0;


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("fly").executes(context -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 2)) {
                if (count == 0) {
                    playerEntity.getAbilities().allowFlying = true;
                    playerEntity.sendAbilitiesUpdate();
                    playerEntity.sendMessage(new TranslatableText("§aFlight enabled"), false);
                    count++;
                } else {
                    playerEntity.getAbilities().allowFlying = false;
                    playerEntity.getAbilities().flying = false;
                    playerEntity.sendAbilitiesUpdate();
                    playerEntity.sendMessage(new TranslatableText("§cFlight disabled"), false);
                    count = 0;
                }
            }

            else {
                playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        }));
        dispatcher.register(CommandManager.literal("fly").then(CommandManager.argument("player", EntityArgumentType.player()).executes(context -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            if (Rank.hasPermission(playerEntity, 2)) {
                if (count == 0) {
                        targetedPlayer.getAbilities().allowFlying = true;
                        targetedPlayer.sendAbilitiesUpdate();
                        targetedPlayer.sendMessage(new TranslatableText("§aFlight enabled"), false);
                        playerEntity.sendMessage(new TranslatableText("§aFlight enabled for §f" + targetedPlayer.getGameProfile().getName()), false);
                    count++;
                } else {
                        targetedPlayer.getAbilities().allowFlying = false;
                        targetedPlayer.getAbilities().flying = false;
                        targetedPlayer.sendAbilitiesUpdate();
                        targetedPlayer.sendMessage(new TranslatableText("§cFlight disabled"), false);
                        playerEntity.sendMessage(new TranslatableText("§aFlight §cdisabled §afor §f" + targetedPlayer.getGameProfile().getName()), false);
                    count = 0;
                    }
            }
            else {
                playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        })));
        dispatcher.register(CommandManager.literal("flyspeed").then(CommandManager.argument("flySpeed", StringArgumentType.greedyString()).executes(context -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            float flySpeeds = Float.parseFloat(StringArgumentType.getString(context, "flySpeed"));


            return 1;
        })));

    }
}