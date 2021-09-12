package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerTagCommand {


    public static ArrayList<UUID> noPlayerTag = new ArrayList<UUID>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("playertag").executes((context) -> {
            ServerCommandSource pz = context.getSource();


            final PlayerEntity p = pz.getPlayer();
            if (noPlayerTag.contains(p.getUuid())) {
                p.sendMessage(new LiteralText(Formatting.YELLOW + "PlayerTag notifications are" + Formatting.RED + " off"), false);

            } else {
                p.sendMessage(new LiteralText(Formatting.YELLOW + "PlayerTag notifications are" + Formatting.GREEN + " on"), false);

            }
            System.out.println("check");
            return 1;
        }).then(CommandManager.literal("off").executes(context -> {
            ServerCommandSource pz = context.getSource();
            final PlayerEntity p = pz.getPlayer();

            if (noPlayerTag.contains(p.getUuid())) {
                p.sendMessage(new LiteralText(Formatting.YELLOW + "You are currently not getting notifications from PlayerTags anyways dumbass"), false);


            } else {

                noPlayerTag.add(p.getUuid());
                p.sendMessage(new LiteralText(Formatting.YELLOW + "You have been removed from getting sound notifications PlayerTags in chat"), false);


            }
            System.out.println("off");
            return 0;
        })).then(CommandManager.literal("on").executes(context -> {
            ServerCommandSource pz = context.getSource();
            final PlayerEntity p = pz.getPlayer();

            if (noPlayerTag.contains(p.getUuid())) {
                noPlayerTag.remove(p.getUuid());
                p.sendMessage(new LiteralText(Formatting.YELLOW + "You have been added to getting sound notifications PlayerTags in chat"), false);


            } else {

                p.sendMessage(new LiteralText(Formatting.YELLOW + "You already getting the PlayerTag notifications dumbass"), false);

            }
            System.out.println("on");
            return 0;
        })));

    }
}

