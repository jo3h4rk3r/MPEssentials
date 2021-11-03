package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.UUID;

public class CraftCommand {

    public static ArrayList<UUID> craftCommand = new ArrayList<UUID>();


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("craft").executes((context) -> CraftCommand.execute(context, context.getSource())));
        dispatcher.register(CommandManager.literal("wb").executes((context) -> CraftCommand.execute(context, context.getSource())));
        dispatcher.register(CommandManager.literal("workbench").executes((context) -> CraftCommand.execute(context, context.getSource())));
    }

    private static int execute(CommandContext<ServerCommandSource> context, ServerCommandSource source) throws CommandSyntaxException {
        ServerCommandSource pz = context.getSource();
        final PlayerEntity p = pz.getPlayer();
        Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(p.getGameProfile().getId()));
        if (Rank.hasCrafting(rank)) {
            craftCommand.add(p.getUuid());
            if (craftCommand.contains(p.getUuid())) {
                p.openHandledScreen(new SimpleNamedScreenHandlerFactory(CraftCommand::createContainer, new TranslatableText("container.crafting")));
                p.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);

            } else {
                p.sendMessage(new LiteralText("ยง4You do not have access to this command"), false);
                p.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1f,0f);
            }
        }
        return 1;
    }

    public static CraftingScreenHandler createContainer(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new CraftingScreenHandler(syncId, inventory, ScreenHandlerContext.create(player.world, player.getBlockPos()));
    }
}