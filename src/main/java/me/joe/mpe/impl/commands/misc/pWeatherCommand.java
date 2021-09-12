package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.packet.c2s.play.ClientSettingsC2SPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

public class pWeatherCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("pweather").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            playerEntity.sendMessage(new TranslatableText("§abroke"), false);

            return 1;
        }));
        dispatcher.register(CommandManager.literal("pweather").then(CommandManager.literal("clear").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            playerEntity.sendMessage(new TranslatableText("§aWeather changed to clear"), false);

            playerEntity.getServer().getCommandManager().execute(playerEntity.getCommandSource(), "/weather clear");


      //      playerEntity.setSwimming(true);
         //   playerEntity.setMovementSpeed(5);


            //targetedPlayer.sendMessage(new TranslatableText(playerEntity.getGameProfile().getName() + " wants to sex you"), false);
            return 1;
        })));
    }
}
