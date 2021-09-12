package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.api.manage.impl.ArrayListManager;
import me.joe.mpe.api.Command;
import me.joe.mpe.impl.commands.spawn.SpawnCommand;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.joe.mpe.impl.mpe.AttackTimer;
import static me.joe.mpe.impl.mpe.Attack_TIMEOUT;

//import static me.joe.mpe.impl.listeners.PlayerTickListener.AttackTimer;
//import static me.joe.mpe.impl.listeners.PlayerTickListener.Attack_TIMEOUT;

public class WildCommand {
    private static final long TELEPORT_TIMEOUT_WILD = 1000 * 600;
    public static Map<UUID, Long> lastRtpTimes_wild = new HashMap<>();



    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,  boolean b) {
            //dispatcher.register(CommandManager.literal("wild").executes((context) -> {
                   //     ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                    //    playerEntity.sendMessage((new TranslatableText("§6⚠ §eYou can only run this command once! Type '/wild confirm' to confirm teleport.")).setStyle((Style.EMPTY).withColor(Formatting.GREEN)), false);
                        //  if (lastRtp_wild == null || current - lastRtp_wild > TELEPORT_TIMEOUT_WILD) {
                        //   lastRtpTimes_wild.put(playerEntity.getUuid(), current);
                    //    return 1;
                   // }));
        dispatcher.register(CommandManager.literal("wild").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();

            long current = System.currentTimeMillis();
            Long lastRtp_wild = lastRtpTimes_wild.get(playerEntity.getUuid());

            if (lastRtp_wild == null || current - lastRtp_wild > TELEPORT_TIMEOUT_WILD) {

                long current1 = System.currentTimeMillis();
                Long lastAttack = AttackTimer.get(playerEntity.getUuid());


                if (lastAttack == null || current - lastAttack > Attack_TIMEOUT) {
                    lastRtpTimes_wild.put(playerEntity.getUuid(), current);
                    context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "spreadplayers 40000 0 40000 40000 false @s");
                    playerEntity.sendMessage((new TranslatableText("§eTeleported to a random location")).setStyle((Style.EMPTY).withColor(Formatting.GREEN)), false);
                    playerEntity.playSound(SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.BLOCKS, 0.5f, 1f);
                } else {
                    // long timeRemaining = 1 + ((Attack_TIMEOUT - (current - lastAttack)) / 1000);
                    playerEntity.sendMessage((new TranslatableText("§cYou cannot teleport while in combat")).setStyle((Style.EMPTY)), false);
                }

            } else {
                long timeRemaining = 1 + ((TELEPORT_TIMEOUT_WILD - (current - lastRtp_wild)) / 1000);
                playerEntity.sendMessage((new TranslatableText("§cYou can't use this again for §f" + timeRemaining + "§c seconds")).setStyle((Style.EMPTY)), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
        }));
    }
}




