package me.joe.mpe.framework.mixin;


import me.joe.mpe.impl.commands.misc.AnvilCommand;
import me.joe.mpe.impl.commands.misc.CraftCommand;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CraftingScreenHandler.class)
abstract class CraftContainerMixin extends AbstractRecipeScreenHandler<CraftingInventory> {


    public CraftContainerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        if(CraftCommand.craftCommand.contains(player.getUuid()))
        {

            return true;


        }
        return canUse(ScreenHandlerContext.create(player.getEntityWorld(), player.getBlockPos()), player, player.getBlockStateAtPos().getBlock());
    }

    @Override
    public void close(PlayerEntity player) {
        if(CraftCommand.craftCommand.contains(player.getUuid()))
        {
            player.sendMessage(new LiteralText("Closed"),false);
            CraftCommand.craftCommand.remove(player.getUuid());
        }
        super.close(player);
    }
}







