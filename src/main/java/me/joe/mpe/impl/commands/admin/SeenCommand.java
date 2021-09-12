package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.utilities.math.MathUtility;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class SeenCommand{

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("seen").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();

                ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
                if (Rank.hasPermission(playerEntity, 2)) {
                    double roundedX = MathUtility.round(targetedPlayer.getX(), 1);
                    double roundedY = MathUtility.round(targetedPlayer.getY(), 1);
                    double roundedZ = MathUtility.round(targetedPlayer.getZ(), 1);
                    String name = targetedPlayer.getGameProfile().getName();
                    String ip = targetedPlayer.getIp();
                    String pos = String.format("%s %s %s", roundedX, roundedY, roundedZ);
                    int ping = targetedPlayer.pingMilliseconds;
                    playerEntity.sendMessage(new LiteralText( "§ePlayer: §6" + name), false);
                    playerEntity.sendMessage(new LiteralText( "§ePOS: §6" + pos), false);
                    playerEntity.sendMessage(new LiteralText( "§eIP: §7" + ip), false);
                    playerEntity.sendMessage(new TranslatableText("§aPing§7: §b" + ping + "ms"), false);



                } else {
                    playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
                    playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
                }

                return 0;
            })));
    }
}
