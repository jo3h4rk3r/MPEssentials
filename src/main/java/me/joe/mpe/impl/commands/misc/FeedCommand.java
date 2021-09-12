package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.api.manage.impl.ArrayListManager;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FeedCommand extends Command {
    private static final long FEED_TIMEOUT = 1000 * 1200;
    public static Map<UUID, Long> lastFeedTimes = new HashMap<>();

    public FeedCommand() {
        super("feed", "feed");
    }


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("feed").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();

            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
            if (Rank.hasHat(rank)) {


                long current = System.currentTimeMillis();
                Long lastFeed = lastFeedTimes.get(playerEntity.getUuid());

                if (lastFeed == null || current - lastFeed > FEED_TIMEOUT) {
                    lastFeedTimes.put(playerEntity.getUuid(), current);
                    playerEntity.getHungerManager().add(20, 1);
                    playerEntity.sendMessage(new LiteralText("§aReplenished hunger"), false);
                } else {
                    long timeRemaining = 1 + ((FEED_TIMEOUT - (current - lastFeed)) / 1000);
                    playerEntity.sendMessage((new TranslatableText("§eYou can't use this again for §6" + timeRemaining + "§e seconds!")).setStyle((Style.EMPTY)), false);
                    playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
                }

            } else {
                playerEntity.sendMessage(new LiteralText("§4You do not have access to this command"), false);
                playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 1;
        }));
    }
}
