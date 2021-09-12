package me.joe.mpe.impl.listeners;

import me.joe.api.event.data.interfaces.Subscribe;
import me.joe.mpe.api.Listener;
import me.joe.mpe.api.Rank;
import me.joe.mpe.framework.wrapped.IServerPlayerEntity;
import me.joe.mpe.impl.events.PlayerTickEvent;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.utilities.Globals;
import me.joe.mpe.impl.utilities.ProtectedZone;
import me.joe.mpe.impl.utilities.math.MathUtility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.*;

import static me.joe.mpe.impl.mpe.AttackTimer;
import static me.joe.mpe.impl.mpe.Attack_TIMEOUT;

public class PlayerTickListener extends Listener {
    long current = System.currentTimeMillis();
    private int currentTick = 0;
    public static Map<java.util.UUID, Long> PlayerHud = new HashMap<>();
    public static Map previousPlayerPositions = new HashMap();
    public static Map<java.util.UUID, Long> teleportCooldowns = new HashMap<>();
    public static Map<java.util.UUID, Long> AFKTimes = new HashMap<>();
    public static Map<java.util.UUID, Long> TPATimes = new HashMap<>();
    public static final Map<UUID, ProtectedZone> previousZones = new HashMap<>();
    private final Set<ServerPlayerEntity> registeredEntities = new HashSet<>();

    public static int cooldownSeconds = 30;
    public static int AFKTimer = 600;
    public static int TPATimer = 5;
    int time = 0;
    public PlayerTickListener() {
        super("PlayerTick");
    }

    @Subscribe
    public void tick(PlayerTickEvent event) {
        ServerPlayerEntity player = event.getPlayer();
        IServerPlayerEntity iplayer = (IServerPlayerEntity) player;
        Long shit = PlayerHud.get(player.getUuid());

        /*
        if (shit == null) {
        } else {
            double tps = MathUtility.round(Globals.TPS1.getAverage(), 1);
            Formatting color;
            if (tps >= 18.0D) {
                color = Formatting.GREEN;
            } else if (tps >= 15.0D) {
                color = Formatting.RED;
            } else {
                color = Formatting.DARK_RED;
            }
            double roundedX = MathUtility.round(player.getX(), 1);
            double roundedY = MathUtility.round(player.getY(), 1);
            double roundedZ = MathUtility.round(player.getZ(), 1);
            String pos = String.format("%s %s %s", roundedX, roundedY, roundedZ);
            int pings = player.pingMilliseconds;
            String ping = pings + "ms";
            String attacker = String.format("%s", player.getAttacker());
            player.sendMessage(new TranslatableText("§f§lPOS§7: §e" + pos + " " + "§f§lPING§7: §e" + ping + " " + "§f§lTPS§7: %s", (new LiteralText(String.valueOf(tps))).setStyle((Style.EMPTY).withColor(color))), true);
        }
         */

        if (player.age % 2 == 0 && player.isAlive()) {
            iplayer.setLastHorizontalSpeed((double) player.horizontalSpeed);
            iplayer.setLastUpwardSpeed(player.getVelocity().getY());
        }





        if (mpe.INSTANCE.getJailManager().has(String.valueOf(player.getGameProfile().getName()))) {
            time++;

            if (time == 80) {
                player.teleport(-190.54, 80.06, -292.47);

                time = 0;
            }
        }





        if (AttackTimer.containsKey(player.getUuid()))  {
            long current = System.currentTimeMillis();
            Long lastAttack = AttackTimer.get(player.getUuid());
            long timeRemaining = 1 + ((Attack_TIMEOUT - (current - lastAttack)) / 1000);
            if (player.isDead()) {
                player.sendMessage(new LiteralText("§a§lYou are no longer in combat"), true);
                AttackTimer.remove(player.getUuid());
            }
            if (timeRemaining == 1) {
                player.sendMessage(new LiteralText("§a§lYou are no longer in combat"), true);
                AttackTimer.remove(player.getUuid());
            }
        }

        //CHECKS FOR AFK
        if (mpe.INSTANCE.getAFKManager().has(String.valueOf(player.getGameProfile().getName()))) {
            if (iplayer.isMoving()) {

                if (!player.isCreative() && !player.isSpectator()) {
                    player.getAbilities().invulnerable = false;
                    player.sendAbilitiesUpdate();
                }

                String name = String.valueOf(player.getGameProfile().getId());
                String displayName = player.getGameProfile().getName();
                if (mpe.INSTANCE.getNickManager().has(name)) {
                    displayName = "" + mpe.INSTANCE.getNickManager().get(name);
                }
                Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(player.getGameProfile().getId()));
                Text text = new LiteralText(rank.prefix + "&r" + displayName + " &7is no longer AFK*");
                event.getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, player.getUuid());
                mpe.INSTANCE.getAFKManager().remove(String.valueOf(player.getGameProfile().getName()));
                mpe.INSTANCE.getAFKManager().save();
            }
        }




        if (iplayer.isMoving()) {
            AFKTimes.put(player.getGameProfile().getId(), (new Date()).getTime());
        } else if (AFKTime(player)) {
            if (!mpe.INSTANCE.getAFKManager().has(String.valueOf(player.getGameProfile().getName()))) {
                mpe.INSTANCE.getAFKManager().add(String.valueOf(player.getGameProfile().getName()));
                String name = String.valueOf(player.getGameProfile().getId());
                String displayName = player.getGameProfile().getName();
                if (mpe.INSTANCE.getNickManager().has(name)) {
                    displayName = "" + mpe.INSTANCE.getNickManager().get(name);
                }
                player.getAbilities().invulnerable = true;
                mpe.INSTANCE.getAFKManager().save();
                Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(player.getGameProfile().getId()));
                Text text = new LiteralText(rank.prefix + "&r" + displayName + " &7is now AFK*");
                event.getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, player.getUuid());
                AFKTimes.remove(player.getGameProfile().getId());
            }
        }




        //SPAWN
        if (player.isAlive() && mpe.INSTANCE.getZonesManager().getElements().size() > 0) {
            ProtectedZone lastZone = previousZones.get(player.getUuid());
            ProtectedZone zone = mpe.INSTANCE.getZonesManager().getZonePlayerIsIn(player);
            if (zone != null) {
                if (lastZone != zone) {
                    player.sendMessage(new LiteralText("§bEntering §7" + zone.getName()), true);
                    if (!Rank.canBuild(player)) {
                        player.getAbilities().allowModifyWorld = false;
                        player.sendAbilitiesUpdate();
                    }
                }

                


                if (!Rank.canBuild(player)) {
                    player.getAbilities().invulnerable = !zone.isPvp();
                }
                previousZones.put(player.getUuid(), zone);
            } else {
                if (lastZone != null) {
                    player.sendMessage(new LiteralText("§bLeaving §7" + lastZone.getName()), true);
                    if (!Rank.canBuild(player)) {
                        player.getAbilities().invulnerable = false;
                        player.getAbilities().allowModifyWorld = true;
                        player.sendAbilitiesUpdate();
                    }
                }
                previousZones.remove(player.getUuid());
            }
        }

        //PLAYER EVENT CODE
/*
        if (newEventFile.getFile().exists() && newEventFile.read().size() > 0) {
            if (mpe.INSTANCE.getEventsManager().has(String.valueOf(player.getGameProfile().getName()))) {
                if (eventStartFile.getFile().exists() && eventStartFile.read().size() > 0) {
                    List<String> lines = new ArrayList<>();
                    lines.add(String.format("%s", player));
                    eventStartFile.write(lines);
                    event.getServer().getCommandManager().execute(player.getCommandSource().withMaxLevel(4).withSilent(), "scoreboard players reset @a event_score");
                  //  player.sendMessage(new LiteralText("§8§l[§5§lEVENT§8§l]§a Starting in 30 seconds!"),false);
                    player.teleport(-14999797, 62, -14999679);
                    teleportCooldowns.put(player.getGameProfile().getId(), (new Date()).getTime());
                    lines.remove(String.format("%s", player));
                    eventStartFile.write(lines);
                }
                if (hasWaited(player)) {
                    //player.sendMessage((new TranslatableText("§8§l[§5§lEVENT§8§l]§a The event has started!")).setStyle((Style.EMPTY)), false);
                    event.getServer().getCommandManager().execute(player.getCommandSource().withMaxLevel(4).withSilent(), "scoreboard players add @a event_score 1");
                    player.teleport(-14999777.00, 70.62, -14999679.00);
                    teleportCooldowns.remove(player.getGameProfile().getId());
                }
            }
        }

 */

    }
        public static boolean canTeleport (ServerPlayerEntity player){
            return !teleportCooldowns.containsKey(player.getGameProfile().getId());
        }
        public static long getCooldownSeconds (ServerPlayerEntity player){
            return ((long) (cooldownSeconds * 1000 + 1000) - ((new Date()).getTime() - teleportCooldowns.get(player.getGameProfile().getId()))) / 1000L;
        }
        public static boolean hasWaited (ServerPlayerEntity playerEntity){
            return (new Date()).getTime() - teleportCooldowns.get(playerEntity.getGameProfile().getId()) >= (long) (cooldownSeconds * 1000);
        }
        public static boolean AFKTime (ServerPlayerEntity playerEntity){
            return (new Date()).getTime() - AFKTimes.get(playerEntity.getGameProfile().getId()) >= (long) (AFKTimer * 1000);
        }
        public static boolean TPATime (ServerPlayerEntity playerEntity){
        return (new Date()).getTime() - TPATimes.get(playerEntity.getGameProfile().getId()) >= (long) (TPATimer * 1000);
    }

    }

