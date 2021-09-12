package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AnvilCommand {


    public static ArrayList<UUID> anvilCommand = new ArrayList<UUID>();
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("anvil").executes((context) ->
                AnvilCommand.execute(context, context.getSource())));
    }
    private static int execute(CommandContext<ServerCommandSource> context, ServerCommandSource source) throws CommandSyntaxException {
        ServerCommandSource pz = context.getSource();
        final PlayerEntity p = pz.getPlayer();



        Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(p.getGameProfile().getId()));
        if (Rank.hasAnvil(rank)) {
            anvilCommand.add(p.getUuid());
            if (anvilCommand.contains(p.getUuid())) {
                p.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, inv, pEntity) -> {

                    return new AnvilScreenHandler(i, inv, ScreenHandlerContext.create(p.getEntityWorld(), p.getBlockPos()));


                }, new TranslatableText("container.repair")));

            }
        } else {
            p.sendMessage(new LiteralText("&cYou do not have access to this command"), false);
        }
        return 0;
    }
}
