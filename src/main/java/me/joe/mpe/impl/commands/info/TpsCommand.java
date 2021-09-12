package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.utilities.Globals;
import me.joe.mpe.impl.utilities.math.MathUtility;
import me.joe.mpe.impl.utilities.math.time.DateHelper;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.lang.management.ManagementFactory;

public class TpsCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("tps").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 0)) {
               double tps = MathUtility.round(Globals.TPS1.getAverage(), 1);
               Formatting color;
               if (tps >= 18.0D) {
                  color = Formatting.GREEN;
               } else if (tps >= 15.0D) {
                  color = Formatting.RED;
               } else {
                  color = Formatting.DARK_RED;
               }

               playerEntity.sendMessage((new TranslatableText("Uptime: %s", DateHelper.formatDateDiff(ManagementFactory.getRuntimeMXBean().getStartTime()))).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);
               playerEntity.sendMessage(new TranslatableText("TPS: %s", (new LiteralText(String.valueOf(tps))).setStyle((Style.EMPTY).withColor(color))), false);
               String memory = String.format("§eMemory: %s/%s MB", Runtime.getRuntime().freeMemory() / 1024L / 1024L, Runtime.getRuntime().totalMemory() / 1024L / 1024L);
               playerEntity.sendMessage(new LiteralText(memory), false);
            } else {
               playerEntity.sendMessage(new LiteralText("§cYou do not have required permissions!"), false);
            }

            return 1;
         }));
   }
}
