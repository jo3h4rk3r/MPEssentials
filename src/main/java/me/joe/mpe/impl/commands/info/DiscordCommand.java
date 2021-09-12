package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;

public class DiscordCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("discord").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            playerEntity.sendMessage(new LiteralText(""), false);
            playerEntity.sendMessage((new TranslatableText("§a§l[Discord] ")).append((new LiteralText("§bhttps://discord.gg/uHmyAeJu3M")).setStyle((Style.EMPTY).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/uHmyAeJu3M")))), false);
            playerEntity.sendMessage(new LiteralText(""), false);
            return 1;
         }));
   }
}
