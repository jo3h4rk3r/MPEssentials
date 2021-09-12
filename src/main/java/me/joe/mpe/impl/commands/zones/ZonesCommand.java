package me.joe.mpe.impl.commands.zones;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.managers.ProtectedZonesManager;
import me.joe.mpe.impl.utilities.ProtectedZone;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.Iterator;

public class ZonesCommand {


   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("zones").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 3)) {
               if (mpe.INSTANCE.getZonesManager().getElements().size() <= 0) {
                  playerEntity.sendMessage((new TranslatableText("No server zones created")).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
                  playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
                  return 1;
               }

               ProtectedZonesManager zoneManager = mpe.INSTANCE.getZonesManager();
               StringBuilder base = new StringBuilder();
               int i = 0;

               for(Iterator var5 = zoneManager.getElements().iterator(); var5.hasNext(); ++i) {
                  ProtectedZone h = (ProtectedZone)var5.next();
                  String str = h.getName();
                  if (i != zoneManager.getElements().size() - 1) {
                     base.append("§7").append(str).append("§f, ");
                  } else {
                     base.append("§7").append(str);
                  }
               }

               playerEntity.sendMessage((new TranslatableText("Zones: " + base)).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);
            } else {
               playerEntity.sendMessage(new LiteralText("§cYou do not have required permissions!"), false);
               playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 1;
         }));
   }
}
