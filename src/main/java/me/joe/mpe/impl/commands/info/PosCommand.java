package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.impl.utilities.math.MathUtility;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class PosCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("pos").executes((context) -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();

                {
                    double roundedX = MathUtility.round(playerEntity.getX(), 1);
                    double roundedY = MathUtility.round(playerEntity.getY(), 1);
                    double roundedZ = MathUtility.round(playerEntity.getZ(), 1);

                    String pos = String.format("{%s %s %s}", roundedX, roundedY, roundedZ);

                    playerEntity.sendMessage(new LiteralText("§ePOS: §7" + pos), false);
                    return 0;

                }
            }));
    }
}
