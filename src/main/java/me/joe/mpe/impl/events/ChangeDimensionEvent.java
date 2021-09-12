package me.joe.mpe.impl.events;

import me.joe.api.event.Event;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.dimension.DimensionType;

public class ChangeDimensionEvent extends Event {
   private DimensionType toDimension;
   private ServerPlayNetworkHandler networkHandler;

   public ChangeDimensionEvent(DimensionType dim, ServerPlayNetworkHandler networkHandler) {
      this.toDimension = dim;
      this.networkHandler = networkHandler;
   }

   public DimensionType getDimension() {
      return this.toDimension;
   }

   public ServerPlayNetworkHandler networkHandler() { return this.networkHandler; }

   public ServerPlayerEntity getPlayer() {
      return this.networkHandler.player;
   }

   public MinecraftServer getServer() { return this.getPlayer().getServer(); }
}
