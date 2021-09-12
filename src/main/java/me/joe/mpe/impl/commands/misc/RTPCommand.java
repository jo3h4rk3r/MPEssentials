package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.api.manage.impl.ArrayListManager;
import me.joe.mpe.api.Command;
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

public class RTPCommand extends Command {
    private static final long TELEPORT_TIMEOUT = 1000 * 30;
    public static Map<UUID, Long> lastRtpTimes = new HashMap<>();

    public RTPCommand() {
        super("rtp", "Teleports you to a random location");
    }


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("rtp").executes((context) -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();

                long current = System.currentTimeMillis();
                Long lastRtp = lastRtpTimes.get(playerEntity.getUuid());
                Long lastAttack = AttackTimer.get(playerEntity.getUuid());


                if (lastAttack == null || current - lastAttack > Attack_TIMEOUT) {
                if (lastRtp == null || current - lastRtp > TELEPORT_TIMEOUT) {
                    lastRtpTimes.put(playerEntity.getUuid(), current);

                    context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "spreadplayers 0 0 0 5500 false @s");

                    playerEntity.sendMessage((new TranslatableText("§eTeleported to a random location")).setStyle((Style.EMPTY).withColor(Formatting.GREEN)), false);
                    playerEntity.playSound(SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.BLOCKS, 0.1f, 1f);
                } else {
                    long timeRemaining = 1 + ((TELEPORT_TIMEOUT - (current - lastRtp)) / 1000);
                    playerEntity.sendMessage((new TranslatableText("§eYou can't use this again for §6" + timeRemaining + "§e seconds!")).setStyle((Style.EMPTY)), false);
                    playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
                }

            } else {
            // long timeRemaining = 1 + ((Attack_TIMEOUT - (current - lastAttack)) / 1000);
            playerEntity.sendMessage((new TranslatableText("§cYou cannot teleport while in combat")).setStyle((Style.EMPTY)), false);
        }
                return 1;
            }));
    }
}
