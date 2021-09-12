package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class RulesCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("rules").executes((context) -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
               // playerEntity.getServer().getCommandManager().execute(playerEntity.getCommandSource(), "/warp wilderness");
              //  playerEntity.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 100.3F, 0.1F);
                playerEntity.sendMessage(new TranslatableText("§8§l(§b§lSurvivalMP Rules§8§l)"), false);
                playerEntity.sendMessage(new LiteralText(""), false);
                playerEntity.sendMessage(new TranslatableText("§eNo hacks/cheats of anykind."), false);
                playerEntity.sendMessage(new TranslatableText("§eNo duping, exploiting, or anything that breaks the game."), false);
                playerEntity.sendMessage(new TranslatableText("§eNo spamming, advertising, or disruptive behavior in chat."), false);
                playerEntity.sendMessage(new TranslatableText("§eNo impersonating staff or other players."), false);
                playerEntity.sendMessage(new TranslatableText("§ePvP and Raiding is allowed! Make sure to build far!"), false);
                playerEntity.sendMessage(new TranslatableText("§6Join our '/discord' for a full list of rules."), false);
                playerEntity.sendMessage(new LiteralText(""), false);
                return 1;
            }));
    }
}






