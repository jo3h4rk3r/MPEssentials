package me.joe.mpe.framework.mixin;


import me.joe.mpe.impl.commands.misc.AnvilCommand;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.text.LiteralText;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AnvilScreenHandler.class)
abstract class AnvilContainerMixin extends ForgingScreenHandler {


    public AnvilContainerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }



    @Override
    public boolean canUse(PlayerEntity player) {
        if(AnvilCommand.anvilCommand.contains(player.getUuid()))
        {

            return true;

        }
        return super.canUse(player);
    }

    @Override
    public void close(PlayerEntity player) {
        if(AnvilCommand.anvilCommand.contains(player.getUuid()))
        {
            //player.sendMessage(new LiteralText("Closed"),false);
            AnvilCommand.anvilCommand.remove(player.getUuid());
        }
        super.close(player);
    }
}







