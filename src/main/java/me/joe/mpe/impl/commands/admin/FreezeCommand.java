package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
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

import java.util.HashMap;
import java.util.UUID;

public class FreezeCommand {
    private static int count = 0;
    public static HashMap<Integer, Integer> Freeze = new HashMap<Integer, Integer>();
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("freeze").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();


            if (count == 0) {
                playerEntity.sendMessage(new LiteralText("§cFEEZE"), false);
                count++;
                Freeze.put(count, count);
            } else {
                Freeze.remove((count));
                playerEntity.sendMessage(new LiteralText("§aUNFROZE"), false);
                count = 0;
            }


            //playerEntity.damage(DamageSource.STARVE, 1);
            return 1;
        }));
    }
}
