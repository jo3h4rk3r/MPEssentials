package me.joe.mpe.impl.events;

import me.joe.api.event.data.EventType;
import me.joe.api.event.data.types.TypedEvent;
import net.minecraft.network.Packet;

public class PacketEvent extends TypedEvent {
   private Packet<net.minecraft.network.listener.PacketListener> packet;

   public PacketEvent(Packet<net.minecraft.network.listener.PacketListener> packet, EventType type) {
      super(type);
   }

   public Packet<net.minecraft.network.listener.PacketListener> getPacket() {
      return this.packet;
   }
}
