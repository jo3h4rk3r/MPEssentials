package me.joe.mpe.framework.mixin;

import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.utilities.TextFormatter;


import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(PlayerListHeaderS2CPacket.class)
public interface PlayerListMixin extends Packet<ClientPlayPacketListener> {
    @Accessor
    void setFooter(Text footer);

    @Accessor
    void setHeader(Text header);
}


/*
public class PlayerListMixin {
    @Mutable
    @Final
    @Shadow private Text footer;

    @Mutable
    @Final
    @Shadow private Text header;


    public PlayerListMixin() {
    }

    @Inject(at= @At("RETURN"), method = "<init>")
    public void PlayerListHeaderS2CPacket(CallbackInfo ci) {
        this.footer = new LiteralText(TextFormatter.tablistChars(mpe.settings.footer));
        this.header = new LiteralText(TextFormatter.tablistChars(mpe.settings.header));

    }






}

 */







/*@Mixin(PlayerListHeaderS2CPacket.class)
public interface PlayerListMixin extends Packet<ClientPlayPacketListener> {


    @Accessor
    void setFooter(Text footer);

    @Accessor
    void setHeader(Text header);



}


 */





