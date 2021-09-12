package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.joe.mpe.impl.mpe;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

import static net.minecraft.server.command.CommandManager.literal;

public class PingCommand
{
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b)
    {
        LiteralArgumentBuilder<ServerCommandSource> ping = literal("ping").
                executes( c ->
                {
                    ServerPlayerEntity playerEntity = c.getSource().getPlayer();
                    int ping1 = playerEntity.pingMilliseconds;
                    playerEntity.sendMessage(new TranslatableText("§aYour current ping§7: §b" + ping1 + "ms"), false);
                    return 1;
                });
        LiteralArgumentBuilder<ServerCommandSource> ping2 = literal("ping").then(CommandManager.argument("player", EntityArgumentType.player()).executes( d ->
                {
                    ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(d, "player");
                    ServerPlayerEntity playerEntity = d.getSource().getPlayer();
                    String name = String.valueOf(targetedPlayer.getGameProfile().getName());
                    if (mpe.INSTANCE.getNickManager().has(name)) {
                        name = "" + mpe.INSTANCE.getNickManager().get(name);
                    }
                    int ping2u = targetedPlayer.pingMilliseconds;
                    playerEntity.sendMessage(new TranslatableText("§a" + name + "'s§a current ping§7:§b " + ping2u + "ms"), false);
                    return 1;
                }));

        dispatcher.register(ping);
        dispatcher.register(ping2);


    }


}