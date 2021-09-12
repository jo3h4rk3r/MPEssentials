package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

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
        craftCommand.add(p.getUuid());
        if (craftCommand.contains(p.getUuid())) {
           // p.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, inv, pEntity) -> {

           //     return new CraftingScreenHandler(i, inv, ScreenHandlerContext.create(p.getEntityWorld(), p.getBlockPos()));


          //  }, new TranslatableText("container.craft")));

            p.sendMessage(new LiteralText("BROKEN"),false);

        }
        return 0;
    }
}

