package me.joe.mpe.impl.commands.tpa;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.api.server.Server;
import me.joe.mpe.impl.commands.back.PositionAndWorld;
import me.joe.mpe.impl.mpe;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.*;

import static me.joe.mpe.impl.commands.tpa.TpaIgnore.tpaIgnore;
import static me.joe.mpe.impl.listeners.PlayerTickListener.TPATimes;
import static me.joe.mpe.impl.mpe.AttackTimer;
import static me.joe.mpe.impl.mpe.Attack_TIMEOUT;

public class TpaCommand {
    public static HashMap<UUID, UUID> tpa = new HashMap<UUID, UUID>();
    public static HashMap<UUID, UUID> tpah = new HashMap<UUID, UUID>();


    private String dimension;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("tpa").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            String playerName = playerEntity.getGameProfile().getName();
            String targetName = targetedPlayer.getGameProfile().getName();
            UUID player = playerEntity.getUuid();
            UUID target = targetedPlayer.getUuid();
            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
            String name = String.valueOf(playerEntity.getGameProfile().getId());
            String displayName = playerEntity.getGameProfile().getName();
            /*
            if (mpe.INSTANCE.getNickManager().has(name)) {
                displayName = "" + mpe.INSTANCE.getNickManager().get(name);
            }

             */
            Rank rank2 = mpe.INSTANCE.getRankManager().get(String.valueOf(targetedPlayer.getGameProfile().getId()));
            String name2 = String.valueOf(targetedPlayer.getGameProfile().getId());
            String displayName2 = targetedPlayer.getGameProfile().getName();
            /*
            if (mpe.INSTANCE.getNickManager().has(name2)) {
                displayName2 = "" + mpe.INSTANCE.getNickManager().get(name2);
            }

             */

            long current = System.currentTimeMillis();
            Long lastAttack = AttackTimer.get(playerEntity.getUuid());


          //  if (!(tpaIgnore.containsValue(targetedPlayer.getUuid()))) {
                if (lastAttack == null || current - lastAttack > Attack_TIMEOUT) {
                    if (!(hasAnyTPRequest(targetedPlayer))) {
                        if (targetedPlayer == null) {
                            playerEntity.sendMessage(new LiteralText("This is not a player"), false);
                        } else {
                            tpa.put(target, player);
                            playerEntity.sendMessage(new LiteralText("§3TPA request sent to: §e" + rank2.prefix + "§f" + displayName2).append((new LiteralText(" [CANCEL] ")).setStyle((Style.EMPTY).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpcancel " + targetedPlayer.getGameProfile().getName())).withColor(Formatting.RED))), false);

                            targetedPlayer.sendMessage(new LiteralText(rank.prefix + "§f" + displayName + " §3wants to teleport to you").append((new LiteralText(" [ACCEPT] ")).setStyle((Style.EMPTY).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept")).withColor(Formatting.GREEN))).append((new LiteralText("[DENY] ")).setStyle((Style.EMPTY).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny")).withColor(Formatting.RED))), false);

                            givingUsingTimer(targetedPlayer);
                            givingUsingTimer(playerEntity);


                        }
                    } else {
                        playerEntity.sendMessage(new LiteralText("§eYou already have a pending teleport request"), false);
                    }
                } else {
                    playerEntity.sendMessage((new TranslatableText("§cYou cannot teleport while in combat")).setStyle((Style.EMPTY)), false);
                }

            return 1;
        })));
        dispatcher.register(CommandManager.literal("tpahere").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");
            String playerName = playerEntity.getGameProfile().getName();
            String targetName = targetedPlayer.getGameProfile().getName();
            UUID player = playerEntity.getUuid();
            UUID target = targetedPlayer.getUuid();
            Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(playerEntity.getGameProfile().getId()));
            String name = String.valueOf(playerEntity.getGameProfile().getId());
            String displayName = playerEntity.getGameProfile().getName();
            /*
            if (mpe.INSTANCE.getNickManager().has(name)) {
                displayName = "" + mpe.INSTANCE.getNickManager().get(name);
            }

             */
            Rank rank2 = mpe.INSTANCE.getRankManager().get(String.valueOf(targetedPlayer.getGameProfile().getId()));
            String name2 = String.valueOf(targetedPlayer.getGameProfile().getId());
            String displayName2 = targetedPlayer.getGameProfile().getName();
            /*
            if (mpe.INSTANCE.getNickManager().has(name2)) {
                displayName2 = "" + mpe.INSTANCE.getNickManager().get(name2);
            }

             */
            long current = System.currentTimeMillis();
            Long lastAttack = AttackTimer.get(playerEntity.getUuid());
          //  if (!(tpaIgnore.containsValue(targetedPlayer.getUuid()))) {
                if (lastAttack == null || current - lastAttack > Attack_TIMEOUT) {
                    if (!(hasAnyTPRequest(targetedPlayer))) {
                        if (targetedPlayer == null) {
                            playerEntity.sendMessage(new LiteralText("§eThis is not a player"), false);
                        } else {
                            tpa.put(target, player);
                            tpah.put(target, player);
                            playerEntity.sendMessage(new LiteralText("§3TPA request sent to: §f" + rank2.prefix + "§f" + displayName2).append((new LiteralText(" [CANCEL] ")).setStyle((Style.EMPTY).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpcancel " + targetedPlayer.getGameProfile().getName())).withColor(Formatting.RED))), false);
                            targetedPlayer.sendMessage(new LiteralText(rank.prefix + "§f" + displayName + " §3requested you teleport to them").append((new LiteralText(" [ACCEPT] ")).setStyle((Style.EMPTY).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept")).withColor(Formatting.GREEN))).append((new LiteralText("[DENY] ")).setStyle((Style.EMPTY).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny")).withColor(Formatting.RED))), false);
                            givingUsingTimer(targetedPlayer);
                            givingUsingTimer(playerEntity);
                        }
                    } else {
                        playerEntity.sendMessage(new LiteralText("§eYou already have a pending teleport request"), false);
                    }
                } else {
                    playerEntity.sendMessage((new TranslatableText("§cYou cannot teleport while in combat")).setStyle((Style.EMPTY)), false);
                }
          //  } else {
          //      playerEntity.sendMessage((new TranslatableText( targetedPlayer.getGameProfile().getName() + " §4has blocked you from sending TPA request")).setStyle((Style.EMPTY)), false);
           // }

            return 1;
        })));
        dispatcher.register(CommandManager.literal("tpaccept").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            long current = System.currentTimeMillis();
            Long lastAttack = AttackTimer.get(playerEntity.getUuid());

            if (lastAttack == null || current - lastAttack > Attack_TIMEOUT) {
                if (hasAnyTPRequest(playerEntity)) {
                    ServerPlayerEntity teleporter = playerEntity.getServer().getPlayerManager().getPlayer(tpa.get(playerEntity.getUuid()));
                    //ServerPlayerEntity teleporter = (ServerPlayerEntity) playerEntity.getServerWorld().getPlayerByUuid(tpa.get(playerEntity.getUuid()));
                    if (tpah.containsValue(teleporter.getUuid())) {
                        playerEntity.teleport((ServerWorld) teleporter.getEntityWorld(), teleporter.getX(), teleporter.getY(), teleporter.getZ(), playerEntity.getYaw(), playerEntity.getPitch());
                        playerEntity.sendMessage(new LiteralText("§aTeleport request accepted"), false);
                        teleporter.sendMessage(new LiteralText("§aTeleport request accepted"), false);
                        tpa.remove(teleporter.getUuid(), playerEntity.getUuid());
                        tpa.remove(playerEntity.getUuid(), teleporter.getUuid());
                        tpah.remove(teleporter.getUuid(), playerEntity.getUuid());
                        tpah.remove(playerEntity.getUuid(), teleporter.getUuid());
                    }
                    if (tpa.containsValue(teleporter.getUuid())) {
                        teleporter.teleport((ServerWorld) playerEntity.getEntityWorld(), playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), playerEntity.getYaw(), playerEntity.getPitch());
                        playerEntity.sendMessage(new LiteralText("§aTeleport request accepted"), false);
                        teleporter.sendMessage(new LiteralText("§aTeleport request accepted"), false);
                        tpa.remove(teleporter.getUuid(), playerEntity.getUuid());
                        tpa.remove(playerEntity.getUuid(), teleporter.getUuid());
                        tpah.remove(teleporter.getUuid(), playerEntity.getUuid());
                        tpah.remove(playerEntity.getUuid(), teleporter.getUuid());
                    }
                } else {
                    playerEntity.sendMessage(new LiteralText("§7You have no teleport requests"), false);
                }
            } else {
                playerEntity.sendMessage((new TranslatableText("§cYou cannot teleport while in combat")).setStyle((Style.EMPTY)), false);
            }

            return 0;
        }));

        dispatcher.register(CommandManager.literal("tpdeny").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (hasAnyTPRequest(playerEntity)) {
                ServerPlayerEntity teleporter = playerEntity.getServer().getPlayerManager().getPlayer(tpa.get(playerEntity.getUuid()));
                // ServerPlayerEntity teleporter = (ServerPlayerEntity) playerEntity.getServerWorld().getPlayerByUuid(tpa.get(playerEntity.getUuid()));
                if (tpah.containsValue(teleporter.getUuid())) {
                    playerEntity.sendMessage(new LiteralText("§cTeleport request denied"), false);
                    teleporter.sendMessage(new LiteralText("§cTeleport request denied"), false);
                    tpa.remove(teleporter.getUuid(), playerEntity.getUuid());
                    tpa.remove(playerEntity.getUuid(), teleporter.getUuid());
                    tpah.remove(teleporter.getUuid(), playerEntity.getUuid());
                    tpah.remove(playerEntity.getUuid(), teleporter.getUuid());
                }
                if (tpa.containsValue(teleporter.getUuid())) {
                    playerEntity.sendMessage(new LiteralText("§cTeleport request denied"), false);
                    teleporter.sendMessage(new LiteralText("§cTeleport request denied"), false);
                    tpa.remove(teleporter.getUuid(), playerEntity.getUuid());
                    tpa.remove(playerEntity.getUuid(), teleporter.getUuid());
                    tpah.remove(teleporter.getUuid(), playerEntity.getUuid());
                    tpah.remove(playerEntity.getUuid(), teleporter.getUuid());
                } else {
                    playerEntity.sendMessage(new LiteralText("§7You have no teleport requests"), false);
                }
            }

            return 1;
        }));
        dispatcher.register(CommandManager.literal("tpcancel").then(CommandManager.argument("player", EntityArgumentType.player()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");

            if (tpa.containsValue(playerEntity.getUuid())) {
            targetedPlayer.sendMessage(new LiteralText("§4Teleport request canceled"), false);
            playerEntity.sendMessage(new LiteralText("§4Teleport request canceled"), false);
            tpa.remove(targetedPlayer.getUuid(), playerEntity.getUuid());
            tpa.remove(playerEntity.getUuid(), targetedPlayer.getUuid());
            tpah.remove(targetedPlayer.getUuid(), playerEntity.getUuid());
            tpah.remove(playerEntity.getUuid(), targetedPlayer.getUuid());
            } else {
                //playerEntity.sendMessage(new LiteralText("§7You have no teleport requests"), false);
            }


            return 1;
        })));
    }

    private static Object playerEntity(UUID uuid) {

        return null;
    }

    private static void givingUsingTimer(ServerPlayerEntity source) {
        ServerPlayerEntity teleporter = source.getServer().getPlayerManager().getPlayer(tpa.get(source.getUuid()));
        TimerTask task = new TimerTask() {
            public void run() {
                if (hasAnyTPRequest(source)) {
                    tpa.remove(teleporter.getUuid(), source.getUuid());
                    tpa.remove(source.getUuid(), teleporter.getUuid());
                    tpah.remove(teleporter.getUuid(), source.getUuid());
                    tpah.remove(source.getUuid(), teleporter.getUuid());
                    teleporter.sendMessage(new LiteralText("§7Teleport request expired"), false);
                    source.sendMessage(new LiteralText("§7Teleport request expired"), false);
                }
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 30000L;
        timer.schedule(task, delay);
    }

    private static void cancelTPA(ServerPlayerEntity source) {
        ServerPlayerEntity teleporter = source.getServer().getPlayerManager().getPlayer(tpa.get(source.getUuid()));
        System.out.println(teleporter.getGameProfile().getName());
        System.out.println(source.getGameProfile().getName());

        if (tpa.get(source.getUuid()) !=null || tpa.get(teleporter.getUuid()) !=null) {
            teleporter.sendMessage(new LiteralText("§7Teleport request canceled"), false);
            source.sendMessage(new LiteralText("§7Teleport request canceled"), false);
            tpa.remove(teleporter.getUuid(), source.getUuid());
            tpa.remove(source.getUuid(), teleporter.getUuid());
            tpah.remove(teleporter.getUuid(), source.getUuid());
            tpah.remove(source.getUuid(), teleporter.getUuid());
        } else {
            System.out.println("NULL");
        }

    }


    private static boolean hasAnyTPRequest(ServerPlayerEntity source) {
        ServerPlayerEntity teleporter = source.getServer().getPlayerManager().getPlayer(tpa.get(source.getUuid()));
        if (tpa.containsKey(source.getUuid())) {
            //   tpa.remove(source);
            return true;
        }
        return false;
    }
}





