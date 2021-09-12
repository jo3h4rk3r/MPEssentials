package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;

public class HatCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("hat").executes((context) -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
                if (Rank.hasHat(rank)) {
                    ItemStack hand = playerEntity.getMainHandStack();
                    ItemStack head = playerEntity.getEquippedStack(EquipmentSlot.HEAD);
                    playerEntity.getInventory().armor.set(EquipmentSlot.HEAD.getEntitySlotId(), hand);
                    playerEntity.getInventory().main.set(playerEntity.getInventory().selectedSlot, head);
                    playerEntity.sendMessage(new LiteralText("§eHat set to &f" + hand), false);
                }
                else {
                    playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
                    playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
                }
                return 1;
            }));
    }
}
