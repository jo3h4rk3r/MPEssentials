package me.joe.mpe.framework.mixin;


import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.command.MessageCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MessageCommand.class})
public class MessageCommandMixin {
    @Inject(
            at = {@At("HEAD")},
            method = "register",
            cancellable = true
    )
    private static void MessageCommand(CommandDispatcher<ServerCommandSource> dispatcher, CallbackInfo ci) {

        ci.cancel();
    }


}
