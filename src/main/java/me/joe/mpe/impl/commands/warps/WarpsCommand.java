package me.joe.mpe.impl.commands.warps;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.managers.WarpManager;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.Iterator;

public class WarpsCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("warps").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            String playerName = playerEntity.getGameProfile().getName();
            if (mpe.INSTANCE.getWarpManager().getElements().size() <= 0) {
               playerEntity.sendMessage((new TranslatableText("No server warps created")).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
               playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
               return 1;
            } else {
               WarpManager warpManager = mpe.INSTANCE.getWarpManager();
               String base = "";
               int i = 0;

               for(Iterator<Warp> var6 = warpManager.getElements().iterator(); var6.hasNext(); ++i) {
                  Warp h = var6.next();
                  String str = h.getName();
                  if (i != warpManager.getElements().size() - 1) {
                     base = base + "§7" + str + "§f, ";
                  } else {
                     base = base + "§7" + str;
                  }
               }

               playerEntity.sendMessage((new TranslatableText("Warps: " + base)).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);
               return 1;
            }
         }));
   }
}
