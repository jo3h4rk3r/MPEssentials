package me.joe.mpe.impl.commands.homes;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.joe.mpe.impl.commands.spawn.SpawnCommand;
import me.joe.mpe.impl.managers.HomeManager;
import me.joe.mpe.impl.mpe;
import net.fabricmc.fabric.api.entity.EntityTrackingRegistry;
import net.minecraft.network.Packet;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
//import static me.joe.mpe.impl.listeners.PlayerTickListener.Attack_TIMEOUT;
//import static me.joe.mpe.impl.listeners.PlayerTickListener.AttackTimer;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

//import static me.joe.mpe.impl.listeners.PlayerTickListener.HomeTimerReset;
import static me.joe.mpe.impl.mpe.AttackTimer;
import static me.joe.mpe.impl.mpe.Attack_TIMEOUT;

public class HomeCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("home").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            String argName = "home";
            String uuid = String.valueOf(playerEntity.getGameProfile().getId());
            UUID player = playerEntity.getUuid();
            if (!mpe.INSTANCE.getPlayerHomeManager().has(uuid)) {
               playerEntity.sendMessage((new TranslatableText("You have no set homes. Use §7'/sethome'")).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
               return 1;
            } else {
               HomeManager homeManager = (HomeManager)mpe.INSTANCE.getPlayerHomeManager().get(uuid);
               Home home = homeManager.get(argName);
               if (home == null) {
                  StringBuilder base = new StringBuilder();
                  int i = 0;

                  for(Iterator<Home> var8 = homeManager.getElements().iterator(); var8.hasNext(); ++i) {
                     Home h = var8.next();
                     String str = h.getName();
                     if (i != homeManager.getElements().size() - 1) {
                        base.append("§7").append(str).append("§f, ");
                     } else {
                        base.append("§7").append(str);
                     }
                  }
                  playerEntity.sendMessage((new TranslatableText("Homes: " + base)).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);
               } else {

                     long current = System.currentTimeMillis();
                     Long lastAttack = AttackTimer.get(playerEntity.getUuid());
                     if (lastAttack == null || current - lastAttack > Attack_TIMEOUT) {


                        playerEntity.teleport(playerEntity.server.getWorld(home.getDimension()), home.getX(), home.getY() + 0.5, home.getZ(), playerEntity.getYaw(), playerEntity.getPitch());


                         playerEntity.sendMessage((new TranslatableText("Teleported to §7'" + home.getName() + "'")).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);

                     } else {

                        playerEntity.sendMessage((new TranslatableText("§cYou cannot teleport while in combat")).setStyle((Style.EMPTY)), false);
                     }

               }
               return 1;
            }
         }));
         dispatcher.register(CommandManager.literal("home").then(CommandManager.argument("name", StringArgumentType.string()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            String argName = StringArgumentType.getString(context, "name");
            String uuid = String.valueOf(playerEntity.getGameProfile().getId());
            UUID player = playerEntity.getUuid();
            if (!mpe.INSTANCE.getPlayerHomeManager().has(uuid)) {
               playerEntity.sendMessage((new TranslatableText("You have not set any homes")).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
               return 1;
            } else {
               HomeManager homeManager = (HomeManager)mpe.INSTANCE.getPlayerHomeManager().get(uuid);
               Home home = homeManager.get(argName);
               if (home == null) {
                  playerEntity.sendMessage((new TranslatableText("Could not find home §7'" + argName + "'")).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
               } else {


                     long current = System.currentTimeMillis();
                     Long lastAttack = AttackTimer.get(playerEntity.getUuid());
                     if (lastAttack == null || current - lastAttack > Attack_TIMEOUT) {

                        playerEntity.teleport(playerEntity.server.getWorld(home.getDimension()), home.getX(), home.getY()+0.5, home.getZ(), playerEntity.getYaw(), playerEntity.getPitch());

                        playerEntity.sendMessage((new TranslatableText("Teleported to §7'" + home.getName() + "'")).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);
                     } else {

                        playerEntity.sendMessage((new TranslatableText("§cYou cannot teleport while in combat")).setStyle((Style.EMPTY)), false);
                     }


               }

               return 1;
            }
         })));
   }


}
