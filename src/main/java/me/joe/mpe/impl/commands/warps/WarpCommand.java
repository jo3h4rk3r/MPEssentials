package me.joe.mpe.impl.commands.warps;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.mpe.api.Command;
import me.joe.mpe.impl.commands.spawn.SpawnCommand;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.managers.WarpManager;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import static me.joe.mpe.impl.mpe.AttackTimer;
import static me.joe.mpe.impl.mpe.Attack_TIMEOUT;
//import static me.joe.mpe.impl.listeners.PlayerTickListener.Attack_TIMEOUT;
//import static me.joe.mpe.impl.listeners.PlayerTickListener.AttackTimer;

public class WarpCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("warp").then(CommandManager.argument("name", StringArgumentType.string()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            String argName = StringArgumentType.getString(context, "name");
            if (mpe.INSTANCE.getWarpManager().getElements().size() <= 0) {
               playerEntity.sendMessage((new TranslatableText("No server warps created")).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
               playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
               return 1;
            } else {
               WarpManager warpManager = mpe.INSTANCE.getWarpManager();
               Warp warp = warpManager.get(argName);
               if (warp == null) {
                  playerEntity.sendMessage((new TranslatableText("Could not find warp §7'" + argName + "'")).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
                  playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
               } else {

                  long current = System.currentTimeMillis();
                  Long lastAttack = AttackTimer.get(playerEntity.getUuid());


                  if (lastAttack == null || current - lastAttack > Attack_TIMEOUT) {
                     playerEntity.teleport(playerEntity.server.getWorld(warp.getDimension()), warp.getX(), warp.getY() + 0.5, warp.getZ(), playerEntity.getYaw(), playerEntity.getPitch());
                     playerEntity.sendMessage((new TranslatableText("Warped to §7'" + warp.getName() + "'")).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);

                  } else {
                     // long timeRemaining = 1 + ((Attack_TIMEOUT - (current - lastAttack)) / 1000);
                     playerEntity.sendMessage((new TranslatableText("§cYou cannot teleport while in combat")).setStyle((Style.EMPTY)), false);
                  }

               }

               return 1;
            }
         })));
   }
}
