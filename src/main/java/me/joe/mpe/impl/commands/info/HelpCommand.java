package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.impl.mpe;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HelpCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("help").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();

            playerEntity.sendMessage(new TranslatableText("§a§lCommands / Usage Page"), false);
            playerEntity.sendMessage(new TranslatableText(""), false);
            playerEntity.sendMessage(new TranslatableText("§3/spawn - Teleports you to spawn"), false);
            playerEntity.sendMessage(new TranslatableText("§3/sethome - Set your homes"), false);
            playerEntity.sendMessage(new TranslatableText("§3/homes - List your homes"), false);
            playerEntity.sendMessage(new TranslatableText("§3/home - Teleport home"), false);
            playerEntity.sendMessage(new TranslatableText("§3/delhome name - Delete your home"), false);
            playerEntity.sendMessage(new TranslatableText("§3/warps - Lists warps"), false);
            playerEntity.sendMessage(new TranslatableText("§3/warp name - Teleports to warp"), false);
            playerEntity.sendMessage(new TranslatableText("§3/tpa player - Teleport to a player"), false);
            playerEntity.sendMessage(new TranslatableText("§3/tpahere player - Teleport a player to you"), false);
            playerEntity.sendMessage(new TranslatableText("§3/tpaccept player - Accepts teleport request"), false);
            playerEntity.sendMessage(new TranslatableText("§3/ignore player - Ignore players chat"), false);
            playerEntity.sendMessage(new TranslatableText("§3/back - Teleports last death location"), false);
            playerEntity.sendMessage(new TranslatableText("§3/rtp - Teleport to a random location"), false);
            playerEntity.sendMessage(new TranslatableText("§3/wild - Teleport to a FAR random location"), false);
            playerEntity.sendMessage(new TranslatableText("§3/vote - Vote for us and earn rewards"), false);
            playerEntity.sendMessage(new TranslatableText("§3/donate - Support the server"), false);

            return 1;
         }));
   }
}
