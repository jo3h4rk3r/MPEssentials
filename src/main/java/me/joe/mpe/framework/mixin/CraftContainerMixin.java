package me.joe.mpe.framework.mixin;

import me.joe.mpe.impl.commands.misc.CraftCommand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingScreenHandler.class)
public class CraftContainerMixin {

    @Inject(
            method = "canUse",
            at = @At("HEAD"),
            cancellable = true
    )
    public void allowUsage(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if(CraftCommand.craftCommand.contains(player.getUuid()));
        cir.setReturnValue(true);
    }

}