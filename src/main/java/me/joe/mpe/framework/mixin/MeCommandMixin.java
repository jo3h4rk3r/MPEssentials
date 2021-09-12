package me.joe.mpe.framework.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.MeCommand;
import net.minecraft.server.command.MessageCommand;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MeCommand.class})
public class MeCommandMixin {
    @Inject(
            at = {@At("HEAD")},
            method = "register",
            cancellable = true
    )
    private static void MeCommand(CommandDispatcher<ServerCommandSource> dispatcher, CallbackInfo ci) {

        ci.cancel();
    }


}
