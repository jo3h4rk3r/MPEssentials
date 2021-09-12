package me.joe.mpe.impl.commands.info;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.text.ClickEvent.Action;
import net.minecraft.util.Hand;

import java.util.UUID;

public class VoteCommand {


   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("vote").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            Text SurvivalMP_Vote2 = (new LiteralText("&eVote for the server and earn rewards!")).setStyle((Style.EMPTY).withClickEvent(new ClickEvent(Action.OPEN_URL, "http://survivalmp.ga/vote.php")));
            Text SurvivalMP_Vote = (new LiteralText("&6&l[Vote Link] &8&l| §f§l§nSurvivalMP.ga/vote")).setStyle((Style.EMPTY).withClickEvent(new ClickEvent(Action.OPEN_URL, "http://survivalmp.ga/vote.php")));
            playerEntity.sendMessage(new LiteralText(""), false);
            playerEntity.sendMessage(SurvivalMP_Vote2, false);
            playerEntity.sendMessage(SurvivalMP_Vote, false);
            playerEntity.sendMessage(new LiteralText(""), false);

            return 1;
         }));
   }
}
