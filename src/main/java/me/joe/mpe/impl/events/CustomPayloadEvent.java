package me.joe.mpe.impl.events;

import me.joe.api.event.data.EventType;
import me.joe.api.event.data.types.CancelableTypedEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;

public class CustomPayloadEvent extends CancelableTypedEvent {
   private CustomPayloadC2SPacket packet;

   public CustomPayloadEvent(CustomPayloadC2SPacket packet, EventType type) {
      super(type);
      this.packet = packet;
   }

   public Packet<net.minecraft.network.listener.ServerPlayPacketListener> getPacket() {
      return this.packet;
   }

   public void setPacket(CustomPayloadC2SPacket packet) {
      this.packet = packet;
   }
}
