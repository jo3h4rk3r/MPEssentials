package me.joe.mpe.framework.mixin;

import com.google.gson.stream.JsonReader;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.joe.mpe.api.Rank;
import me.joe.mpe.framework.ModCore;
import me.joe.mpe.impl.config.Config;
import me.joe.mpe.impl.events.PlayerConnectEvent;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.tablist.TabHUD;
import me.joe.mpe.impl.utilities.TextFormatter;
import me.joe.mpe.impl.utilities.TickCounter;
import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import net.minecraft.client.realms.dto.PlayerInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.MessageType;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.OperatorList;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.UserCache;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.sql.PooledConnection;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Mixin({PlayerManager.class})
public abstract class PlayerManagerMixin {

    private int counter = 0;
    private Object PacketByteBuf;

    @Shadow public abstract void sendToAll(Packet<?> packet);

    @Shadow @Final private List<ServerPlayerEntity> players;

    @Shadow @Final private MinecraftServer server;


    @Shadow @Final protected int maxPlayers;


    @Shadow public abstract int getCurrentPlayerCount();

    @Shadow @Final private Map<UUID, ServerPlayerEntity> playerMap;

    @Shadow @Final public static File BANNED_IPS_FILE;

    @Shadow @Final private OperatorList ops;

    @Shadow public abstract MinecraftServer getServer();

/*

    @Inject(at= @At("HEAD"), method = "updatePlayerLatency")
    public void updatePlayerLatency(CallbackInfo ci) {
            @SuppressWarnings("ConstantConditions")
            PlayerListMixin packet = (PlayerListMixin) new PlayerListHeaderS2CPacket();
            packet.setFooter(new LiteralText(TextFormatter.tablistChars(Config.INSTANCE.footer)));
            packet.setHeader(new LiteralText(TextFormatter.tablistChars(Config.INSTANCE.header)));
            this.sendToAll((Packet<?>) packet);

    }

 */





    @Inject(
            at= @At("HEAD"),
            method = "updatePlayerLatency"
    )

    public void updatePlayerLatency(CallbackInfo ci) {
        if (this.counter++ >= 80) {
            PacketByteBuf buf = new PacketByteBuf((ByteBuf) Unpooled.buffer());
            GameProfile gameProfile = new GameProfile(buf.readUuid(), (String)null);
            this.sendToAll((Packet<?>) buf);
            this.sendToAll(new PlayerListHeaderS2CPacket(buf));
            this.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, this.players));
          //  packet.setFooter(new LiteralText(TextFormatter.tablistChars(Config.INSTANCE.footer)));
          //  packet.setHeader(new LiteralText(TextFormatter.tablistChars(Config.INSTANCE.header)));
            this.counter = 0;



            /*
            PacketByteBuf packet = new PacketByteBuf();
            packet.writeString("1234");
            this.sendToAll((Packet<?>) packet);
            this.sendToAll(new PlayerListHeaderS2CPacket(packet));
            this.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, this.players));
            this.counter = 0;
*/



        }
    }



    @Inject(
            at = {@At("HEAD")},
            method = "onPlayerConnect"
    )
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        PlayerConnectEvent e = new PlayerConnectEvent(connection, player);
        mpe.INSTANCE.getEventBus().invoke(e);
    }

    @Inject(
            at = {@At("TAIL")},
            method = "onPlayerConnect"
    )
    private void onPlayerConnected(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        player.sendMessage(new LiteralText("&5&lSurvivalMP &f&l| &aConnected!"), false);
        player.sendMessage(new LiteralText(""), false);
        player.sendMessage(new LiteralText("&a" + getCurrentPlayerCount() + " &fonline"), false);
        player.sendMessage(new LiteralText(""), false);
        player.sendMessage(new LiteralText("&fTPS: &a" + TickCounter.getTps()), false);
        player.sendMessage(new LiteralText(""), false);
        player.sendMessage(new LiteralText("&7Join our discord for news and updates &3/discord"), false);
        player.sendMessage(new LiteralText(""), false);
    }

    @Redirect(method = "onPlayerConnect",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcastChatMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"))
    public void onPlayerConnect(PlayerManager playerManager, Text message, MessageType type, UUID senderUuid) {

    }

    @Inject(method = "broadcastChatMessage", at = @At("HEAD"), cancellable = true)

    public void broadcastChatMessage(Text message, MessageType messageType, UUID uuid, CallbackInfo ci) {
        ci.cancel();

        this.server.sendSystemMessage(message, uuid);

        GameMessageS2CPacket packet = new GameMessageS2CPacket(message, messageType, uuid);

        for (ServerPlayerEntity recipient : this.players) {
            Set<UUID> ignoreData = ModCore.tpData.ignoreMap.get(recipient.getUuid());

            if (ignoreData == null || !ignoreData.contains(uuid)) {
                recipient.networkHandler.sendPacket(packet);
            }
        }
    }
}


