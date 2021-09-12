package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
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
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

public class GlowCommand {
    private static int count = 0;
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("glow").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
            if (Rank.hasHat(rank)) {
                if (count == 0) {
                    playerEntity.sendMessage(new LiteralText("§aGlow enabled"), false);





                    count++;
                } else {
                    playerEntity.sendMessage(new LiteralText("§cGlow disabled"), false);
                    playerEntity.setGlowing(false);
                    count = 0;
                }
            } else {
                playerEntity.sendMessage(new LiteralText("§cYou do not have access to that command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        }));
        dispatcher.register(CommandManager.literal("glow").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
            if (Rank.hasPermission(playerEntity, 2)) {
                if (count == 0) {
                    playerEntity.sendMessage(new LiteralText("§aGlow enabled"), false);

                    playerEntity.setGlowing(true);
                    count++;
                } else {
                    playerEntity.sendMessage(new LiteralText("§cGlow disabled"), false);
                    playerEntity.setGlowing(false);
                    count = 0;
                }
            } else {
                playerEntity.sendMessage(new LiteralText("§cYou do not have access to that command."), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        })));
    }
}
