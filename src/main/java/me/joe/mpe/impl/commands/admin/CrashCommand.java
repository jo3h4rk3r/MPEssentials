package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Rank;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class CrashCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("crash").requires(
                (source) -> source.hasPermissionLevel(2)).then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 2)) {
                ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
                playerEntity.sendMessage(new TranslatableText("§aCrashing player §f" + targetedPlayer.getGameProfile().getName()), false);

                context.getSource().getServer().getCommandManager().execute(targetedPlayer.getCommandSource().withMaxLevel(4).withSilent(), "particle minecraft:elder_guardian ~ ~ ~ 0 0 0 0 50000");
            } else {
                playerEntity.sendMessage(new LiteralText("§cYou do not have access to this command"), false);
            }
            return 1;
        })));
    }
}
