package me.joe.mpe.impl.commands.warps;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.managers.WarpManager;
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

public class DelWarpCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("delwarp").then(CommandManager.argument("name", StringArgumentType.string()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            String argName = StringArgumentType.getString(context, "name");
            String playerName = playerEntity.getGameProfile().getName();
            if (Rank.hasPermission(playerEntity, 3)) {
               WarpManager warpManager = mpe.INSTANCE.getWarpManager();
               Warp warp = warpManager.get(argName);
               if (warpManager.has(warp)) {
                  warpManager.remove(warp);
                  if (warpManager.getElements().isEmpty()) {
                     mpe.INSTANCE.getPlayerHomeManager().remove(playerName);
                     warp.getFile().delete();
                     warp.getFile().getDirectory().getDirectory().delete();
                  } else {
                     warp.getFile().delete();
                  }

                  playerEntity.sendMessage((new TranslatableText("Deleted warp §7'" + argName + "'")).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);
               } else {
                  playerEntity.sendMessage((new TranslatableText("Could not find warp §7'" + argName + "'")).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
                  playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
               }
            } else {
               playerEntity.sendMessage(new LiteralText("§cYou do not have required permissions!"), false);
               playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 1;
         })));
   }
}
