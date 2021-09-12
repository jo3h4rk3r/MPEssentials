package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

public class testCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("setrank").requires(
                (source) -> source.hasPermissionLevel(4)).then(CommandManager.argument("player", StringArgumentType.string()).then(CommandManager.argument("rank", StringArgumentType.string()).executes((context) -> {




            if (context.getSource().hasPermissionLevel(4)) {
                String player = StringArgumentType.getString(context, "player");
                String rank = StringArgumentType.getString(context, "rank");
                context.getSource().getServer().getCommandManager().execute(context.getSource().getServer().getCommandSource().withMaxLevel(4).withSilent(), "execute as @r run rank set " + player + " " + rank);
                System.out.println("rank set " + player + " " + rank);


            } else {
                System.out.println("A NON-OP Player tried to set a rank");
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                playerEntity.sendMessage(new LiteralText("§cYou do not have access to this command"), false);

            }


            return 1;
        }))));
    }
}
