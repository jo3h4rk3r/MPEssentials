package me.joe.mpe.framework.mixin;

import me.joe.mpe.api.Rank;
import me.joe.mpe.framework.wrapped.IServerPlayerEntity;
import me.joe.mpe.impl.commands.info.PlayerTagCommand;
import me.joe.mpe.impl.events.PlayerDisconnectEvent;
import me.joe.mpe.impl.listeners.PlayerTickListener;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.utilities.TextFormatter;
import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.option.ChatVisibility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.MessageCommand;
import net.minecraft.server.command.TeamMsgCommand;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin({ServerPlayNetworkHandler.class})
public class ServerPlayNetworkHandlerMixin {
   @Shadow private boolean waitingForKeepAlive;
   @Shadow private long keepAliveId;
   private static int count = 0;

   @Shadow
   public ServerPlayerEntity player;
   @Final
   @Shadow
   private MinecraftServer server;
   @Shadow
   private int messageCooldown;

   private static final Logger LOGGER = LogManager.getLogger();
   private int floatingTicks;


   @Inject(
      at = {@At("HEAD")},
      method = "onDisconnected(Lnet/minecraft/text/Text;)V"
   )
   private void remove(Text text, CallbackInfo ci) {
      PlayerDisconnectEvent e = new PlayerDisconnectEvent(this.player, text);
      mpe.INSTANCE.getEventBus().invoke(e);
   }

   @Redirect(method = "onDisconnected(Lnet/minecraft/text/Text;)V",
           at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcastChatMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"))
   public void onPlayerDisconnected(PlayerManager playerManager, Text message, MessageType type, UUID senderUuid) {
   }






   @Inject(
      at = {@At("HEAD")},
      method = "onGameMessage(Lnet/minecraft/network/packet/c2s/play/ChatMessageC2SPacket;)V",
      cancellable = true
   )
   public void onGameMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {

      if(formatChat(packet.getChatMessage()).toString() == "toomanylinks")
      {
         ci.cancel();
         this.player.sendMessage(new LiteralText("too many links, only one per message"), false);
      }

      this.customOnChatMessage(packet);
      ci.cancel();
   }

   public void customOnChatMessage(ChatMessageC2SPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, (ServerPlayNetworkHandler) (Object) this, this.player.getWorld());
      if (this.player.getClientChatVisibility() == ChatVisibility.HIDDEN) {
         this.sendPacket(new GameMessageS2CPacket((new TranslatableText("chat.cannotSend")).formatted(Formatting.RED),MessageType.CHAT /* idk what you want here */, this.player.getUuid()));
      } else {
         this.player.updateLastActionTime();
         String string = packet.getChatMessage();
         string = StringUtils.normalizeSpace(string);

         for(int i = 0; i < string.length(); ++i) {
            if (!SharedConstants.isValidChar(string.charAt(i))) {
               this.disconnect(new TranslatableText("multiplayer.disconnect.illegal_characters"));
               return;
            }
         }

         if (string.startsWith("/")) {
            this.executeCommand(string);
         } else if (mpe.INSTANCE.getMuteManager().has(String.valueOf(this.player.getGameProfile().getId()))) {
            this.player.sendMessage(new LiteralText("§cYou are currently muted"), false);
         } else {
            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(this.player.getGameProfile().getId()));
            String name = String.valueOf(this.player.getGameProfile().getId());
            String displayName = this.player.getGameProfile().getName();
            MutableText displayNameHoverText = new TranslatableText(String.valueOf(player.getGameProfile().getName()));
            TranslatableText NameHover = (new TranslatableText("" + rank.prefix + "§f" + player.getGameProfile().getName()));
            if (mpe.INSTANCE.getNickManager().has(name)) {
               displayName = "" + mpe.INSTANCE.getNickManager().get(name);
               displayNameHoverText = new TranslatableText("%s", displayName).setStyle((Style.EMPTY).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, NameHover)).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + player.getGameProfile().getName() + " ")));
            }

            this.server.sendSystemMessage(new LiteralText("!" + player.getGameProfile().getName() + " > " + string), this.player.getUuid());
            Text text = new TranslatableText("%s§f%s §f§l:§r %s", rank.prefix, displayNameHoverText, formatChat(string));
            this.server.getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, this.player.getUuid());




         }

         this.messageCooldown += 20;
         if (this.messageCooldown > 50 && !this.server.getPlayerManager().isOperator(this.player.getGameProfile())) {
            String displayName = this.player.getGameProfile().getName();
            this.disconnect(new TranslatableText("disconnect.spam"));
            //Text text = new TranslatableText("§4" + displayName + "§4 has been kicked for spamming");
            //this.server.getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, this.player.getUuid());
            System.out.println(displayName + " has been kicked for spamming.");
         }



      }

   }

   private LiteralText formatChat(String s) {
      boolean link = false;
      boolean item = false;
      String between = "";
      String chatb4Msg = "";
      String chataftMsg = "";
      int linkcount = 0;
      int urlcount = 0;
      int itemcount = 0;
      int itemincount = 0;

      String url = "none";
      String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
      StringBuilder colorBuilder = new StringBuilder(s);
      LiteralText urltext = new LiteralText("");
      LiteralText colortext = new LiteralText("");

      //Simple Color Change

      for (int d = 0; d < colorBuilder.length(); d++) {
         if (colorBuilder.charAt(d) == '&' && matches(colorBuilder.charAt(d + 1))) {
            colorBuilder.setCharAt(d, '§');
         }

      }
      String colorDone = colorBuilder.toString();
      LiteralText finalProduct = new LiteralText("");


      String[] check = colorBuilder.toString().split(" ");


      String playerz = "";

      for (int i = 0; i < check.length; i++) {
         final PlayerEntity p = this.server.getPlayerManager().getPlayer(check[i]);
         if (p != null) {

            if (i == 0) {
               playerz = check[i];

            } else {
               playerz = playerz + check[i];

            }

         }


      }


      String[] playerseperate = playerz.split(" ");
      StringBuilder st = new StringBuilder(colorDone);


      for (int i = 0; i < playerseperate.length; i++) {
         final PlayerEntity p = this.server.getPlayerManager().getPlayer(playerseperate[i]);
         if (p != null) {



            //if (PlayerTagCommand.noPlayerTag.contains(p.getUuid())) {
            //} else {
            //   World world = p.getEntityWorld();
            //   world.playSound(null, p.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.1f, 0.1f);
            //}


            Pattern word = Pattern.compile(playerseperate[i]);
            Matcher match = word.matcher(st.toString());
            while (match.find()) {
               // System.out.println("Found love at index " + match.start() + " - " + (match.end() - 1));


               st.replace(match.start(), match.end(), "§3" + playerseperate[i] + "§r");

            }

         }


      }

      colorDone = st.toString();


      for (int i = 0; i < check.length; i++) {
         if (isUrl(check[i], regex)) {
            //How many links are in msg

            if(linkcount== 0)
            {
               linkcount++;
            }
            //How many words in the url is
            urlcount= i+1;

            link = true;

            url = check[i];


         }

      }




      for (int i = 0; i < check.length; i++) {
         if (check[i].equalsIgnoreCase("#item")) {
            //How many links are in msg

            item = true;

            itemcount = i + 1;


         }

      }


      if (link && !item) {

         chatb4Msg = TextFormatter.before(colorDone, url);
         chataftMsg = TextFormatter.after(colorDone, url);


         if (chatb4Msg == "") {

            return (LiteralText) new LiteralText(chatb4Msg + " ").append(new LiteralText("" + url).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)).withColor(Formatting.BLUE).withFormatting(Formatting.UNDERLINE)));


         }
         return (LiteralText) new LiteralText(chatb4Msg).append(new LiteralText(url).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)).withColor(Formatting.BLUE).withFormatting(Formatting.UNDERLINE))).append(new LiteralText(chataftMsg));

      } else if (item && !link) {
         //System.out.println("No url, but item yes");

         chatb4Msg = TextFormatter.before(colorDone, "#item");
         chataftMsg = TextFormatter.after(colorDone, "#item");


         if (chatb4Msg == "") {
            return (LiteralText) new LiteralText(chatb4Msg + " ").append(player.getInventory().getMainHandStack().toHoverableText()).setStyle(player.getInventory().getMainHandStack().toHoverableText().getStyle()).append(new LiteralText(chataftMsg));


         }


         return (LiteralText) new LiteralText(chatb4Msg).append(new LiteralText("").append(player.getInventory().getMainHandStack().toHoverableText())).append(new LiteralText(" " + chataftMsg));
         //return (LiteralText) new LiteralText(chatb4Msg).append(new LiteralText(player.inventory.getMainHandStack().getName().getString()).setStyle(player.inventory.getMainHandStack().toHoverableText().getStyle())).append(new LiteralText(chataftMsg));


      } else if (item && link) {

         boolean linkfirst;
         if (urlcount < itemcount) {
            linkfirst = true;
           // System.out.println("Link is first");
           // System.out.println("Url is at " + urlcount + " item = " + itemcount);

         } else {
           // System.out.println(" Item is at = " + itemcount + "Url is at " + urlcount);
           // System.out.println("Item is first");
            linkfirst = false;
         }
         if (linkfirst) {
            if (itemcount == urlcount + 1) {

               //Check if next to each other

              // System.out.println("One after another");
               //Checks text before the url and item
               chatb4Msg = TextFormatter.before(colorDone, url + " #item");
               chataftMsg = TextFormatter.after(colorDone, url + " #item");

              // System.out.println(chatb4Msg);
               return (LiteralText) new LiteralText(chatb4Msg)
                       .append(new LiteralText(url).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)).withColor(Formatting.BLUE).withFormatting(Formatting.UNDERLINE)))
                       .append(new LiteralText(" ").append(player.getInventory().getMainHandStack().toHoverableText()))
                       .append(chataftMsg);

            } else {
               chatb4Msg = TextFormatter.before(colorDone, url);
               chataftMsg = TextFormatter.after(colorDone, "#item");
               between = TextFormatter.between(colorDone, url,"#item");
              // System.out.println(between);

               return (LiteralText) new LiteralText(chatb4Msg)
                       .append(new LiteralText(url).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)).withColor(Formatting.BLUE).withFormatting(Formatting.UNDERLINE)))
                       .append(between)
                       .append(new LiteralText("").append(player.getInventory().getMainHandStack().toHoverableText()))
                       .append(chataftMsg);


            }


         } else {
            if (urlcount == itemcount + 1) {

               //Check if next to each other

              // System.out.println("One after another");
               //Checks text before the url and item
               chatb4Msg = TextFormatter.before(colorDone, "#item " + url);
               chataftMsg = TextFormatter.after(colorDone, "#item " + url);
               // chatb4Msg=before(colorDone, url+"#item");
               // chataftMsg=after(colorDone, url+" #item");


              // System.out.println(chatb4Msg);
               return (LiteralText) new LiteralText(chatb4Msg)
                       .append(new LiteralText("")
                               .append(player.getInventory().getMainHandStack().toHoverableText()))
                       .append(new LiteralText(" ")
                               .append(new LiteralText(url).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)).withColor(Formatting.BLUE).withFormatting(Formatting.UNDERLINE))))
                       .append(chataftMsg);

            }else{
               chatb4Msg = TextFormatter.before(colorDone, "#item");
               chataftMsg = TextFormatter.after(colorDone, url);
               between = TextFormatter.between(colorDone, "#item",url);
             //  System.out.println(between);
               return (LiteralText) new LiteralText(chatb4Msg)
                       .append(new LiteralText("").append(player.getInventory().getMainHandStack().toHoverableText()))
                       .append(new LiteralText(between))
                       .append(new LiteralText(url).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)).withColor(Formatting.BLUE).withFormatting(Formatting.UNDERLINE)))
                       .append(new LiteralText(chataftMsg));



            }

         }


      }



      return new LiteralText(colorDone);
   }
   private static boolean isUrl(String s, String pattern) {
      try {
         Pattern patt = Pattern.compile(pattern);
         Matcher matcher = patt.matcher(s);


         return matcher.matches();
      } catch (RuntimeException e) {
         return false;
      }
   }
   private static boolean matches(Character c){
      return "b0931825467adcfeklmnor".contains(c.toString());
   }

   @Shadow
   private void executeCommand(String string) {
   }

   @Shadow
   public void disconnect(Text text) {

   }

   @Shadow
   public void sendPacket(Packet<net.minecraft.network.listener.ClientPlayPacketListener> packet) {
   }
}
