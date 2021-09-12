package me.joe.mpe.impl.events;

import me.joe.api.event.Event;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerAttackEvent extends Event {
   private ServerPlayerEntity attacker;
   private ServerPlayerEntity target;

   public PlayerAttackEvent(ServerPlayerEntity attacker, ServerPlayerEntity target) {
      this.attacker = attacker;
      this.target = target;
   }

   public ServerPlayerEntity getAttacker() {
      return this.attacker;
   }

   public ServerPlayerEntity getTarget() {
      return this.target;
   }
}
