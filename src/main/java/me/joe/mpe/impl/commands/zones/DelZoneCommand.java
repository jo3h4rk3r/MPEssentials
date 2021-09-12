package me.joe.mpe.impl.commands.zones;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
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

public class DelZoneCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("delzone").then(CommandManager.argument("name", StringArgumentType.string()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 4)) {
               String argName = StringArgumentType.getString(context, "name");
               ProtectedZonesManager zoneManager = mpe.INSTANCE.getZonesManager();
               ProtectedZone zone = zoneManager.get(argName);
               if (zoneManager.has(zone)) {
                  zoneManager.remove(new ProtectedZone[]{zone});
                  if (zoneManager.getElements().isEmpty()) {
                     zone.getFile().delete();
                     zone.getFile().getDirectory().getDirectory().delete();
                  } else {
                     zone.getFile().delete();
                  }

                  playerEntity.sendMessage((new TranslatableText("Deleted zone §7'" + argName + "'")).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);
               } else {
                  playerEntity.sendMessage((new TranslatableText("Could not find zone §7'" + argName + "'")).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
                  playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
               }
            } else {
               playerEntity.sendMessage(new LiteralText("§cYou do not have required permissions!"), false);
               playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 1;
         })));
   }
}
