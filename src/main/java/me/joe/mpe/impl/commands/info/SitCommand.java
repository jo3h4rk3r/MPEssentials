package me.joe.mpe.impl.commands.info;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class SitCommand<SitEntity extends Entity> {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("sit").executes(context -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
         //   playerEntity.lay

            //playerEntity.startRiding((Entity) PigEntity);
            playerEntity.sendMessage(new LiteralText("You are now sitting"),false);

            return 0;
        }));



        dispatcher.register(CommandManager.literal("ride").then(CommandManager.argument("player", EntityArgumentType.player()).executes(context -> {
                    ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
                    ServerPlayerEntity playerEntity = context.getSource().getPlayer();

                    if (playerEntity.isInRange(targetedPlayer, 4)){
                       // if (playerEntity.hasPassengers()){
                            //playerEntity.sendMessage(new LiteralText("ashd87natsd6ats76d5"),false);
                          //  } else {
                            playerEntity.startRiding(targetedPlayer);
                            targetedPlayer.teleport(playerEntity.server.getWorld(World.NETHER), playerEntity.getX(), playerEntity.getY()+0.5, playerEntity.getZ(), playerEntity.getYaw(), playerEntity.getPitch());
                            targetedPlayer.teleport(playerEntity.server.getWorld(World.OVERWORLD), playerEntity.getX(), playerEntity.getY()+0.5, playerEntity.getZ(), playerEntity.getYaw(), playerEntity.getPitch());
                            playerEntity.sendMessage(new LiteralText("You are now riding " + targetedPlayer.getGameProfile().getName()),false);
                            targetedPlayer.teleport(playerEntity.server.getWorld(World.OVERWORLD), playerEntity.getX(), playerEntity.getY()+0.5, playerEntity.getZ(), playerEntity.getYaw(), playerEntity.getPitch());
                  //      }
                } else {
                        playerEntity.sendMessage(new LiteralText("You must be under 5 blocks away from the player"),false);
                      }
            return 0;
        })));

            dispatcher.register(CommandManager.literal("unmount").executes(context -> {
                ServerPlayerEntity playerEntity = context.getSource().getPlayer();
                //   playerEntity.lay

                //playerEntity.startRiding((Entity) PigEntity);
                playerEntity.sendMessage(new LiteralText("test"),false);
                playerEntity.stopRiding();

                return 0;
            }));



        }
}
