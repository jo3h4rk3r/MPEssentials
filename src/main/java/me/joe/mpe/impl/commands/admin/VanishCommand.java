package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class VanishCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("vanish").executes((context) -> {
                // serverCommandSourceCommandDispatcher.register(CommandManager.literal("v").executes((context) -> this.execute(context, context.getSource())));


                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                if (Rank.hasPermission(playerEntity, 2)) {
                        if (!playerEntity.getAbilities().invulnerable) {
                            playerEntity.getAbilities().invulnerable = true;
                            playerEntity.sendAbilitiesUpdate();
                        playerEntity.setInvisible(true);
                        playerEntity.sendMessage(new LiteralText("§b§lSurvivalMP §8§l| §aVanish enabled"), false);

                    } else {
                            playerEntity.getAbilities().invulnerable = false;
                            playerEntity.sendAbilitiesUpdate();
                        playerEntity.setInvisible(false);
                        playerEntity.sendMessage(new TranslatableText("§b§lSurvivalMP §8§l| §cVanish disabled"), false);

                    }
                }


                                else {
                    playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
                }
                return 1;


            }));
    }
}
