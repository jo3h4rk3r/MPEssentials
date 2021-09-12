package me.joe.mpe.impl.events;

import me.joe.api.event.data.types.CancelableEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerDisconnectEvent extends CancelableEvent {
   private ServerPlayerEntity player;
   private Text text;

   public PlayerDisconnectEvent(ServerPlayerEntity playerEntity, Text text) {
      this.player = playerEntity;
      this.text = text;
   }

   public ServerPlayerEntity getPlayer() {
      return this.player;
   }

   public MinecraftServer getServer() {
      return this.player.getServer();
   }

   public Text getText() {
      return this.text;
   }
}
