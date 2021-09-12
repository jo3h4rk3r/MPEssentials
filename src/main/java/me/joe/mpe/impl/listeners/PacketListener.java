package me.joe.mpe.impl.listeners;

import com.google.common.collect.Maps;
import java.util.Map;
import me.joe.api.event.data.EventType;
import me.joe.api.event.data.interfaces.Subscribe;
import me.joe.mpe.api.Listener;
import me.joe.mpe.impl.events.PacketEvent;

public class PacketListener extends Listener {
   public static Map playerPacketCounter = Maps.newHashMap();

   public PacketListener() {
      super("Packet");
   }

   @Subscribe
   public void onPackets(PacketEvent event) {
      if (event.getType() == EventType.RECEIVE) {
      }

      if (event.getType() == EventType.SEND) {
      }

   }
}
