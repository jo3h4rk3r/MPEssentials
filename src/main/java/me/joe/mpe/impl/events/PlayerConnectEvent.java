package me.joe.mpe.impl.events;

import me.joe.api.event.data.types.CancelableEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class PlayerConnectEvent extends CancelableEvent {
   private ClientConnection connection;
   private ServerPlayerEntity player;

   public PlayerConnectEvent(ClientConnection connection, ServerPlayerEntity playerEntity) {
      this.connection = connection;
      this.player = playerEntity;
   }

   public ServerPlayerEntity getPlayer() {
      return this.player;
   }

   public InetAddress getAddress() {
      return ((InetSocketAddress)this.connection.getAddress()).getAddress();
   }

   public ClientConnection getClientConnection() {
      return this.connection;
   }

   public MinecraftServer getServer() {
      return this.player.getServer();
   }
}
