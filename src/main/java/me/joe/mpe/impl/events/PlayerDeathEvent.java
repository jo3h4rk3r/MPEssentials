package me.joe.mpe.impl.events;

import me.joe.api.event.Event;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerDeathEvent extends Event {
   private ServerPlayerEntity playerEntity;

   public PlayerDeathEvent(ServerPlayerEntity playerEntity) {
      this.playerEntity = playerEntity;
   }

   public ServerPlayerEntity getPlayer() {
      return this.playerEntity;
   }

   public MinecraftServer getServer() {
      return this.playerEntity.getServer();
   }
}
