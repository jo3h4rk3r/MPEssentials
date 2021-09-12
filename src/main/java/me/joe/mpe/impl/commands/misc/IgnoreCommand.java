package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.framework.ModCore;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import static me.joe.mpe.impl.commands.misc.MsgIgnoreCommand.MsgIgnoreC;
import static me.joe.mpe.impl.commands.tpa.TpaIgnore.tpaIgnore;
import static net.minecraft.command.argument.EntityArgumentType.getPlayer;
import static net.minecraft.server.command.CommandManager.argument;


public class IgnoreCommand {
    public static HashMap<UUID, UUID> msgIgnore = new HashMap<UUID, UUID>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,  boolean b) {

      //  CommandRegistry.INSTANCE.register(true, (CommandDispatcher<ServerCommandSource> dispatcher) -> {

            dispatcher.register(CommandManager.literal("ignore")


                    .then(argument("target", EntityArgumentType.player())
                            .executes(IgnoreCommand::ignore)));




        }


    private static int ignore(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        UUID senderUUID = ctx.getSource().getPlayer().getUuid();
        ServerPlayerEntity playerEntity = ctx.getSource().getPlayer();
        ServerPlayerEntity target = getPlayer(ctx, "target");
        UUID ignoredUUID = target.getUuid();

        if (senderUUID.equals(ignoredUUID)) {
            ctx.getSource().sendFeedback(new LiteralText("You cannot ignore yourself.").formatted(Formatting.RED), false);
            playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            return 1;
        }

        if (Rank.hasPermission(target, 1)) {
            ctx.getSource().sendFeedback(new LiteralText("You cannot ignore that player.").formatted(Formatting.RED), false);
            playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            return 1;
        }



        if(!ModCore.tpData.ignoreMap.containsKey(senderUUID)){
            ModCore.tpData.ignoreMap.put(senderUUID, new HashSet<>());
        }
        if(!ModCore.tpData.ignoreMap.get(senderUUID).contains(ignoredUUID)){
            ModCore.tpData.ignoreMap.get(senderUUID).add(ignoredUUID);
            ctx.getSource().sendFeedback(
                    new LiteralText("You are now ignoring ").formatted(Formatting.YELLOW).append(getPlayer(ctx, "target").getName().copy().formatted(Formatting.GOLD)), false);
            msgIgnore.put(ignoredUUID, senderUUID);
            tpaIgnore.put(ignoredUUID, senderUUID);
            MsgIgnoreC.put(ignoredUUID, senderUUID);
        } else {
            ModCore.tpData.ignoreMap.get(senderUUID).remove(ignoredUUID);
            ctx.getSource().sendFeedback(
                    new TranslatableText("You are no longer ignoring ")
                            .formatted(Formatting.YELLOW)
                            .append(
                                    getPlayer(ctx, "target").getName().copy()
                                            .formatted(Formatting.GOLD)), false);
            msgIgnore.remove(ignoredUUID, senderUUID);
            tpaIgnore.remove(ignoredUUID, senderUUID);
            MsgIgnoreC.remove(ignoredUUID, senderUUID);
        }
        try {
            ModCore.tpData.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

}