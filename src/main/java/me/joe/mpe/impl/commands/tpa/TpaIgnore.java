package me.joe.mpe.impl.commands.tpa;

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

public class TpaIgnore {
    private static int count = 0;
    public static HashMap<UUID, UUID> tpaIgnore = new HashMap<UUID, UUID>();
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("tpaignore").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");

           /* if (count == 0) {
                playerEntity.sendMessage(new LiteralText("§cYou will no longer receive TPA requests from " + targetedPlayer.getGameProfile().getName()), false);
                tpaIgnore.put(targetedPlayer.getUuid(), playerEntity.getUuid());
                count++;
            } else {
                tpaIgnore.remove(targetedPlayer.getUuid(), playerEntity.getUuid());
                playerEntity.sendMessage(new LiteralText("§aYou will now receive TPA requests from " + targetedPlayer.getGameProfile().getName()), false);
                count = 0;
            }

            */


            //playerEntity.damage(DamageSource.STARVE, 1);
            return 1;
        })));
    }
}
