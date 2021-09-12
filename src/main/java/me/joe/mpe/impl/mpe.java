package me.joe.mpe.impl;

import me.joe.mpe.api.Rank;
import me.joe.mpe.api.server.Server;
import me.joe.mpe.impl.commands.misc.PvPSwitchCommand;
import me.joe.mpe.impl.managers.*;
import me.joe.mpe.impl.managers.PlayerHomeManager;
import me.joe.mpe.impl.tablist.TabHUD;
import me.joe.mpe.impl.utilities.files.PlayerCounter;
import me.joe.mpe.impl.utilities.TextFormatter;
import me.joe.mpe.impl.utilities.TickCounter;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum mpe {
   INSTANCE;

   public static String NAME = "MPE-Core";
   private CommandManager commandManager;
   private EventManager eventBus;
   private ListenerManager listenerManager;
   private PlayerHomeManager homeManager;
   private PlayerManager playerManager;
   private WarpManager warpManager;
   private AFKManager afkManager;
   private BansManager bansManager;
   private JailManager jailManager;
   private RankManager rankManager;
   private NickManager nickManager;
   private EventsManager eventsManager;
   private MuteManager muteManager;
   private ProtectedZonesManager zonesManager;
   public static File DIRECTORY = new File("config");
   public static TabHUD settings = new TabHUD();
   public static TextFormatter TF = new TextFormatter();
   public static final long Attack_TIMEOUT = 1000 * 10;
   public static Map<UUID, Long> AttackTimer = new HashMap<>();

   public void main() {
      if (!DIRECTORY.exists()) {
         DIRECTORY.mkdir();
      }

      this.eventBus = new EventManager();
      this.commandManager = new CommandManager();
      this.listenerManager = new ListenerManager();
      this.playerManager = new PlayerManager();
      this.warpManager = new WarpManager();
      this.eventsManager = new EventsManager();
      this.jailManager = new JailManager();
      this.rankManager = new RankManager();
      this.nickManager = new NickManager();
      this.muteManager = new MuteManager();
      this.afkManager = new AFKManager();
      this.bansManager = new BansManager();
      this.zonesManager = new ProtectedZonesManager();
      this.listenerManager.setAllToListen();
      this.homeManager = new PlayerHomeManager();
      ServerTickCallback.EVENT.register(TickCounter::onTick);
      ServerTickCallback.EVENT.register(PlayerCounter::onTickPlayer);


      AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
         ActionResult result = mpe.INSTANCE.getZonesManager().getActionResult(player);
         if (result == ActionResult.SUCCESS) {
            player.sendMessage(new LiteralText("&c&lYou cannot build in this area!"), true);
         }
         return result;
      });
      UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
         Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
         if (!Rank.canBuild(player)) {
            if (block == Blocks.CRAFTING_TABLE || block == Blocks.CHEST || block == Blocks.FURNACE || block == Blocks.OAK_DOOR || block == Blocks.ENCHANTING_TABLE || block == Blocks.NOTE_BLOCK || block == Blocks.OAK_TRAPDOOR || block == Blocks.WHITE_BED || block == Blocks.RED_BED || block == Blocks.BLACK_BED || block == Blocks.BLUE_BED || block == Blocks.GRINDSTONE || block == Blocks.STONECUTTER || block == Blocks.AIR || block == Blocks.ENDER_CHEST || block == Blocks.BLAST_FURNACE || block == Blocks.SMITHING_TABLE || block == Blocks.BREWING_STAND || block == Blocks.CARTOGRAPHY_TABLE || block == Blocks.LECTERN || block == Blocks.JUKEBOX || block == Blocks.BELL || block == Blocks.DISPENSER || block == Blocks.HOPPER || block == Blocks.BARREL || block == Blocks.COMPOSTER || block == Blocks.SHULKER_BOX || block == Blocks.ANVIL || block == Blocks.CHIPPED_ANVIL || block == Blocks.DAMAGED_ANVIL || block == Blocks.LOOM || block == Blocks.SMOKER || block == Blocks.FLETCHING_TABLE || block == Blocks.TRAPPED_CHEST || block == Blocks.STONE_BUTTON || block == Blocks.OAK_BUTTON || block == Blocks.LEVER) {
               return ActionResult.PASS;
            }
         }
         ActionResult result = mpe.INSTANCE.getZonesManager().getActionResult(player);
         if (result == ActionResult.SUCCESS) {
            player.sendMessage(new LiteralText("&c&lYou cannot build in this area!"), true);
         }

         return result;

      });
      AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
       /*  if (!(PvPSwitchCommand.PvPSwitch.containsKey(player.getUuid())))  {
            if (entity instanceof PlayerEntity) {
               player.sendMessage(new LiteralText("&c&lYou have PvP turned off"), true);
               return ActionResult.SUCCESS;
            }
         }

        */
         if (entity instanceof PlayerEntity && ((PlayerEntity) entity).getAbilities().invulnerable) {
            player.sendMessage(new LiteralText("&c&lYou cannot attack players in this area!"), true);
            return ActionResult.SUCCESS;
         }
         if (mpe.INSTANCE.getAFKManager().has(String.valueOf(player.getGameProfile().getName()))) {
            if (entity instanceof PlayerEntity) {
               player.sendMessage(new LiteralText("&c&lYou cannot attack players while AFK!"), true);
               return ActionResult.SUCCESS;
            }
         }
         return ActionResult.PASS;
      });
      AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
         if (entity instanceof PlayerEntity) {
            PlayerEntity pe = ((PlayerEntity) entity);
            long current = System.currentTimeMillis();
            Long lastAttack = AttackTimer.get(player.getUuid());
            AttackTimer.put(player.getUuid(), current);
            AttackTimer.put(pe.getUuid(), current);
            if (lastAttack == null || current - lastAttack > Attack_TIMEOUT) {
               player.sendMessage((new TranslatableText("§a§lYou are now in combat")).setStyle((Style.EMPTY)), true);
               pe.sendMessage((new TranslatableText("§a§lYou are now in combat")).setStyle((Style.EMPTY)), true);
            }
         }
         return ActionResult.PASS;
      });
   }


   public void close() {
      System.out.println("Closing MPEssentials");
      this.eventBus.detach(this.listenerManager);
   }

   public CommandManager getCommandManager() {
      return this.commandManager;
   }

   public ListenerManager getListenerManager() {
      return this.listenerManager;
   }

   public PlayerManager getPlayerManager() {
      return this.playerManager;
   }

   public PlayerHomeManager getPlayerHomeManager() {
      return this.homeManager;
   }

   public WarpManager getWarpManager() {
      return this.warpManager;
   }


   public EventManager getEventBus() {
      return this.eventBus;
   }

   public RankManager getRankManager() {
      return this.rankManager;
   }

   public NickManager getNickManager() {
      return this.nickManager;
   }

   public MuteManager getMuteManager() {
      return this.muteManager;
   }

   public EventsManager getEventsManager() {
      return this.eventsManager;
   }

   public AFKManager getAFKManager() {
      return this.afkManager;
   }
   public JailManager getJailManager() {
      return this.jailManager;
   }
   public BansManager getBansManager() {
      return this.bansManager;
   }


   public ProtectedZonesManager getZonesManager() {
      return this.zonesManager;
   }
}
