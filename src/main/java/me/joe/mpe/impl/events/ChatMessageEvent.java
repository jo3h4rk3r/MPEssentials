package me.joe.mpe.impl.events;

import me.joe.api.event.data.types.CancelableEvent;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ChatMessageEvent extends CancelableEvent {
   private ChatMessageC2SPacket packet;
   private ServerPlayerEntity player;

   public ChatMessageEvent(ChatMessageC2SPacket pack, ServerPlayerEntity player) {
      this.packet = pack;
      this.player = player;
   }

   public ChatMessageC2SPacket getPacket() {
      return this.packet;
   }

   public ServerPlayerEntity getPlayer() {
      return this.player;
   }

   public MinecraftServer getServer() {
      return this.player.getServer();
   }
}
