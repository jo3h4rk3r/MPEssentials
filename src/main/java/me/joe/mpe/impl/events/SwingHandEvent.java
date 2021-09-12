package me.joe.mpe.impl.events;

import me.joe.api.event.Event;
import net.minecraft.server.network.ServerPlayerEntity;

public class SwingHandEvent extends Event {
   private ServerPlayerEntity player;

   public SwingHandEvent(ServerPlayerEntity player) {
      this.player = player;
   }

   public ServerPlayerEntity getPlayer() {
      return this.player;
   }
}
