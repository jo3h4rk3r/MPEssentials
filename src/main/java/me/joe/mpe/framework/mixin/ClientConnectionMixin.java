package me.joe.mpe.framework.mixin;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import me.joe.api.event.data.EventType;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.events.PacketEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientConnection.class})
public abstract class ClientConnectionMixin {
   @Inject(
      method = "channelRead0",
      at = {@At("HEAD")}
   )
   private void packetInCount(ChannelHandlerContext channelHandlerContext_1, Packet<net.minecraft.network.listener.PacketListener> packet_1, CallbackInfo ci) {
      PacketEvent event = new PacketEvent(packet_1, EventType.RECEIVE);
      mpe.INSTANCE.getEventBus().invoke(event);
   }

   @Inject(
      method = "sendImmediately",
      at = {@At("HEAD")}
   )
   private void packetOutCount(Packet<net.minecraft.network.listener.PacketListener> packet_1, GenericFutureListener<? extends Future<? super Void>> genericFutureListener_1, CallbackInfo ci) {
      PacketEvent event = new PacketEvent(packet_1, EventType.SEND);
      mpe.INSTANCE.getEventBus().invoke(event);
   }
}
