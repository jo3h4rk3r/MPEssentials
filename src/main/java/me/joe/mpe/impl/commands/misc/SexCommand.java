package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

public class SexCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("sex").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                playerEntity.sendMessage(new TranslatableText("§asex"), false);
                playerEntity.damage(DamageSource.STARVE, 1);
                return 1;
        }));
        dispatcher.register(CommandManager.literal("sex").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            playerEntity.sendMessage(new TranslatableText("§aSex for §f" + targetedPlayer.getGameProfile().getName()), false);
            targetedPlayer.sendMessage(new TranslatableText(playerEntity.getGameProfile().getName() + " wants to sex you"), false);
            //playerEntity.damage(DamageSource.STARVE, 1);
            return 1;
        })));
    }
}
