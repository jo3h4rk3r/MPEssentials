package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

public class GodCommand {
    private static int count = 0;
   // int count = 0;
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(
                    CommandManager.literal("god")
                            .executes(context -> {
                                ServerPlayerEntity playerEntity = context.getSource().getPlayer();

                                if (Rank.hasPermission(playerEntity, 1)) {
                                        if (count == 0) {
                                            playerEntity.getAbilities().invulnerable = true;
                                            playerEntity.sendAbilitiesUpdate();
                                            playerEntity.sendMessage(new TranslatableText("§fGod mode §aenabled"), false);
                                            count++;
                                        } else {
                                            count = 0;
                                            playerEntity.getAbilities().invulnerable = false;
                                            playerEntity.sendAbilitiesUpdate();
                                            playerEntity.sendMessage(new TranslatableText("§fGod mode §cdisabled"), false);
                                        }
                                }
                                else {
                                    playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
                                    playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
                                }


                                return 1;
                            }));

    }
}