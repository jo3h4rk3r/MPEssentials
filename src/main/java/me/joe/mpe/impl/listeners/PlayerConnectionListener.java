package me.joe.mpe.impl.listeners;

import me.joe.api.event.data.interfaces.Subscribe;
import me.joe.mpe.api.Listener;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.commands.back.BackCommand;
import me.joe.mpe.impl.events.CustomPayloadEvent;
import me.joe.mpe.impl.events.PlayerConnectEvent;
import me.joe.mpe.impl.events.PlayerDisconnectEvent;
import me.joe.mpe.impl.managers.HomeManager;
import me.joe.mpe.impl.mpe;
//import net.fabricmc.fabric.impl.networking.CustomPayloadC2SPacketAccessor;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.world.GameMode;

import java.util.Random;


public class PlayerConnectionListener extends Listener {
   public PlayerConnectionListener() {
      super("PlayerConnection");
   }
   @Subscribe
   public void listenForConnect(PlayerConnectEvent event) {
      String uuid = String.valueOf(event.getPlayer().getGameProfile().getId());
      String playerName = event.getPlayer().getGameProfile().getName();
      mpe.INSTANCE.getPlayerHomeManager().loadPlayersHomes(uuid, playerName);
      Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(event.getPlayer().getGameProfile().getId()));
      String name = String.valueOf(event.getPlayer().getGameProfile().getId());
      String displayName = event.getPlayer().getGameProfile().getName();
      if (mpe.INSTANCE.getPlayerHomeManager().has(playerName)) {
         ((HomeManager)mpe.INSTANCE.getPlayerHomeManager().get(playerName)).getHomeDirectory().rename(uuid);
         mpe.INSTANCE.getPlayerHomeManager().remove(playerName);
         mpe.INSTANCE.getPlayerHomeManager().add(uuid, new HomeManager(uuid));
      }
      if (!mpe.INSTANCE.getRankManager().has(String.valueOf(event.getPlayer().getGameProfile().getId()))) {
         mpe.INSTANCE.getRankManager().add(String.valueOf(event.getPlayer().getGameProfile().getId()), Rank.GUEST);
         mpe.INSTANCE.getRankManager().save();
      }
      BackCommand.previousPlayerPositions.remove(event.getPlayer().getUuid());
      if (mpe.INSTANCE.getPlayerManager().has(uuid)) {
         if (mpe.INSTANCE.getNickManager().has(name)) {
            displayName = "" + mpe.INSTANCE.getNickManager().get(name);
         }
         event.getServer().sendSystemMessage(new LiteralText(event.getPlayer().getGameProfile().getName() + " joined the game"), event.getPlayer().getUuid());
         event.getServer().getPlayerManager().sendToAll(new GameMessageS2CPacket(new LiteralText(String.format(rank.prefix + "§b" + event.getPlayer().getGameProfile().getName() + " §ejoined the game")).formatted(Formatting.ITALIC), MessageType.CHAT, Util.NIL_UUID));
      }
      else {

         mpe.INSTANCE.getPlayerManager().addNewPlayer(uuid, playerName);



         event.getServer().sendSystemMessage(new LiteralText(event.getPlayer().getGameProfile().getName() + " joined the game"), event.getPlayer().getUuid());
         event.getServer().getPlayerManager().sendToAll(new GameMessageS2CPacket(new LiteralText(String.format("§l§dWelcome §r%s §l§dto the server!", event.getPlayer().getName().asString())), MessageType.CHAT, Util.NIL_UUID));
         event.getServer().getPlayerManager().sendToAll(new GameMessageS2CPacket(new LiteralText(String.format("§b%s §ejoined the game", event.getPlayer().getGameProfile().getName())).formatted(Formatting.ITALIC), MessageType.CHAT, Util.NIL_UUID));
         //event.getServer().getPlayerManager().sendToAll(new GameMessageS2CPacket(new LiteralText(rank.prefix + "§r" + event.getPlayer().getGameProfile().getName() + " §6joined the game").formatted(Formatting.ITALIC), MessageType.CHAT, Util.NIL_UUID));

         
      }

   }



   @Subscribe
   public void listenForDisconnect(PlayerDisconnectEvent event) {
      String uuid = String.valueOf(event.getPlayer().getGameProfile().getId());
      Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(event.getPlayer().getGameProfile().getId()));
      String name = String.valueOf(event.getPlayer().getGameProfile().getId());
      String displayName = event.getPlayer().getGameProfile().getName();

      if (mpe.INSTANCE.getPlayerManager().has(uuid)) {
         if (mpe.INSTANCE.getNickManager().has(name)) {
            displayName = "" + mpe.INSTANCE.getNickManager().get(name);
         }



         event.getServer().sendSystemMessage(new LiteralText(event.getPlayer().getGameProfile().getName() + " left the game"), event.getPlayer().getUuid());
         event.getServer().getPlayerManager().sendToAll(new GameMessageS2CPacket(new LiteralText(String.format(rank.prefix + "§b" + event.getPlayer().getGameProfile().getName() + " §eleft the game")).formatted(Formatting.ITALIC), MessageType.CHAT, Util.NIL_UUID));
         PlayerTickListener.previousZones.remove(event.getPlayer().getUuid());
      }
   }
/*
   public void onPackets(CustomPayloadEvent event) {
      if (event.getPacket() instanceof CustomPayloadC2SPacket) {
         CustomPayloadC2SPacket pack = (CustomPayloadC2SPacket)event.getPacket();
         CustomPayloadC2SPacketAccessor ipacket = (CustomPayloadC2SPacketAccessor)pack;
         System.out.println("Namespace - " + ipacket.getChannel().getNamespace());
         System.out.println("Path - " + ipacket.getChannel().getPath());
         System.out.println("--------------------------------------------------");
      }



   }
 */
}


