package me.joe.mpe.impl.commands.spawn;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.impl.utilities.CfgFile;
import me.joe.mpe.impl.utilities.FileDirectory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//import static me.joe.mpe.impl.listeners.PlayerTickListener.Attack_TIMEOUT;
//import static me.joe.mpe.impl.listeners.PlayerTickListener.AttackTimer;
import static me.joe.mpe.impl.listeners.PlayerTickListener.teleportCooldowns;
import static me.joe.mpe.impl.mpe.AttackTimer;
import static me.joe.mpe.impl.mpe.Attack_TIMEOUT;


public class SpawnCommand {
   public static CfgFile spawnFile;
   public static Vec3d pos;

    static {
      spawnFile = new CfgFile("spawn", new FileDirectory("core"));
      SpawnCommand.loadSpawn();
   }

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("spawn").executes((context) -> {
             ServerPlayerEntity playerEntity = context.getSource().getPlayer();

             long current = System.currentTimeMillis();
             Long lastAttack = AttackTimer.get(playerEntity.getUuid());


             if (lastAttack == null || current - lastAttack > Attack_TIMEOUT) {
                 SpawnCommand.loadSpawn();
                 SpawnCommand.teleportToSpawn(playerEntity);
             } else {
                // long timeRemaining = 1 + ((Attack_TIMEOUT - (current - lastAttack)) / 1000);
                 playerEntity.sendMessage((new TranslatableText("§cYou cannot teleport while in combat")).setStyle((Style.EMPTY)), false);
             }


             return 1;
         }));
   }

    public static Map<UUID, Long> getTeleportCooldowns() {
        return teleportCooldowns;
    }

    public void setPendingSpawnTeleports(HashMap pendingSpawnTeleports) {
    }

    public static void loadSpawn() {
      if (spawnFile.getFile().exists() && spawnFile.read().size() > 0) {
         String[] coords = spawnFile.read().get(0).split("coords:")[1].split(",");
         double x = Double.parseDouble(coords[0]);
         double y = Double.parseDouble(coords[1]);
         double z = Double.parseDouble(coords[2]);
         SpawnCommand.pos = new Vec3d(x, y, z);
      } else {
         SetSpawnCommand.saveSpawn(0.0D, 0.0D, 0.0D);
      }

   }

   public static void teleportToSpawn(ServerPlayerEntity playerEntity) {
         playerEntity.teleport(
                 playerEntity.server.getWorld(World.OVERWORLD),
                 SpawnCommand.pos.getX(),
                 SpawnCommand.pos.getY(),
                 SpawnCommand.pos.getZ(),
                 playerEntity.getYaw(),
                 playerEntity.getPitch()
         );
         playerEntity.sendMessage((new TranslatableText("§eTeleported to spawn")).setStyle((Style.EMPTY).withColor(Formatting.GREEN)), false);
         playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_HAT, SoundCategory.BLOCKS, 1f, 2f);
      }
   }



