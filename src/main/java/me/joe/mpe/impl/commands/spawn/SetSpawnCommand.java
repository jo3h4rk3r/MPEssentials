package me.joe.mpe.impl.commands.spawn;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.utilities.math.MathUtility;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

public class SetSpawnCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("setspawn").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 4)) {
               double roundedX = MathUtility.round(playerEntity.getX(), 1);
               double roundedY = MathUtility.round(playerEntity.getY(), 1);
               double roundedZ = MathUtility.round(playerEntity.getZ(), 1);
               playerEntity.sendMessage(new TranslatableText(String.format("§eSet server spawn to §7{%s %s %s}", roundedX, roundedY, roundedZ)), false);
               saveSpawn(roundedX, roundedY, roundedZ);
            } else {
               playerEntity.sendMessage(new LiteralText("§4You do not have access to this command."), false);
               playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 1;
         }));
   }

   public static void saveSpawn(double x, double y, double z) {
      List<String> lines = new ArrayList<>();
      lines.add(String.format("coords:%s,%s,%s", x, y, z));
      SpawnCommand.spawnFile.write(lines);
   }
}
