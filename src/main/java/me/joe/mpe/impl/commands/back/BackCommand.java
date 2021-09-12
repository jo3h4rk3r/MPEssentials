package me.joe.mpe.impl.commands.back;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.impl.commands.spawn.SpawnCommand;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
//import static me.joe.mpe.impl.listeners.PlayerTickListener.Attack_TIMEOUT;
//import static me.joe.mpe.impl.listeners.PlayerTickListener.AttackTimer;
import static me.joe.mpe.impl.mpe.AttackTimer;
import static me.joe.mpe.impl.mpe.Attack_TIMEOUT;

import java.util.Map;
import java.util.UUID;

public class BackCommand {


   public static Map<UUID, PositionAndWorld> previousPlayerPositions = Maps.newHashMap();

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("back").executes((context) -> {

            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            PositionAndWorld posAndWorld = previousPlayerPositions.get(playerEntity.getUuid());
            if (posAndWorld != null) {

               long current = System.currentTimeMillis();
               Long lastAttack = AttackTimer.get(playerEntity.getUuid());


               if (lastAttack == null || current - lastAttack > Attack_TIMEOUT) {
                  Vec3d pos = posAndWorld.getPosition();
                  playerEntity.teleport(posAndWorld.getWorld(), pos.getX(), pos.getY() + 0.5, pos.getZ(), playerEntity.getYaw(), playerEntity.getPitch());
                  playerEntity.sendMessage((new TranslatableText("§aTeleported to your last location")).setStyle((Style.EMPTY)), false);
               } else {
                  // long timeRemaining = 1 + ((Attack_TIMEOUT - (current - lastAttack)) / 1000);
                  playerEntity.sendMessage((new TranslatableText("§cYou cannot teleport while in combat")).setStyle((Style.EMPTY)), false);
               }

            } else {
               playerEntity.sendMessage((new TranslatableText("§6No location found!")).setStyle((Style.EMPTY)), false);
               playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }
            return 1;
         }));
   }
}