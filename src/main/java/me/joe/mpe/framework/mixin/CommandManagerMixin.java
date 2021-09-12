package me.joe.mpe.framework.mixin;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.events.CommandExecutionEvent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({CommandManager.class})
public abstract class CommandManagerMixin {
   @Shadow
   @Final
   private CommandDispatcher<ServerCommandSource> dispatcher;

   @Inject(
           at = @At(
                   value = "RETURN",
                   target = "Lnet/minecraft/server/command/CommandManager;execute(Lnet/minecraft/server/command/ServerCommandSource;Ljava/lang/String;)I"
           ),
           method = "execute(Lnet/minecraft/server/command/ServerCommandSource;Ljava/lang/String;)I"
   )
   public void commandExecutor(ServerCommandSource source, String string_1, CallbackInfoReturnable<String> cir) {
      CommandExecutionEvent event = new CommandExecutionEvent(this.dispatcher, source, string_1);
      mpe.INSTANCE.getEventBus().invoke(event);

   }
}
