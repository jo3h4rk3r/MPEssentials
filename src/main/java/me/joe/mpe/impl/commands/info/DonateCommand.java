package me.joe.mpe.impl.commands.info;

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

public class DonateCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("donate").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            playerEntity.sendMessage(new LiteralText(""), false);
            playerEntity.sendMessage((new TranslatableText("§6§l[Donate] §8§l| ")).append((new LiteralText("§f§l§nSurvivalMP.ga/donate")).setStyle((Style.EMPTY).withClickEvent(new ClickEvent(Action.OPEN_URL, "https://www.paypal.com/pools/c/8mz6CQT9jK?_ga=1.160056117.98233952.1576387439")))), false);
             playerEntity.sendMessage(new LiteralText(""), false);
            return 1;
         }));
   }
}
