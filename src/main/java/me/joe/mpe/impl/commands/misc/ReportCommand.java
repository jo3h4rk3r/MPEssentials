package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.ClickEvent.Action;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;

public class ReportCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("report").executes((context) -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                playerEntity.sendMessage((new TranslatableText("§c§l[REPORT] ")).append((new LiteralText("§fSend player reports here!")).setStyle((Style.EMPTY).withClickEvent(new ClickEvent(Action.OPEN_URL, "https://docs.google.com/forms/d/e/1FAIpQLSeJg8f3xHXKPxx4zkoxLVIkH_8qoSdow_CJJDiKXlwqJO_eKg/viewform")))), false);
                return 1;
            }));
    }
}