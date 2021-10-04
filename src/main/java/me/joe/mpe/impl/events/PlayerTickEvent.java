package me.joe.mpe.impl.events;

import me.joe.api.event.Event;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PlayerTickEvent extends Event {
   private ServerPlayerEntity player;

   public PlayerTickEvent(ServerPlayerEntity player) {
      this.player = player;
   }

   public ServerPlayerEntity getPlayer() {
      return this.player;
   }

   public ServerWorld getWorld() {
      return this.player.getWorld();
   }

   public MinecraftServer getServer() {
      return this.player.getServer();
   }

}
