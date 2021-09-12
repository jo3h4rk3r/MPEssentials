package me.joe.mpe.impl.commands.homes;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.managers.HomeManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.Iterator;

public class HomesCommand extends Command {
   public HomesCommand() {
      super("Homes", "List your homes");
   }

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("homes").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            String uuid = String.valueOf(playerEntity.getGameProfile().getId());
            if (!mpe.INSTANCE.getPlayerHomeManager().has(uuid)) {
               playerEntity.sendMessage((new TranslatableText("You have no set home")).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
               return 1;
            } else {
               HomeManager homeManager = (HomeManager) mpe.INSTANCE.getPlayerHomeManager().get(uuid);
               String base = "";
               int i = 0;

               for (Iterator<Home> var6 = homeManager.getElements().iterator(); var6.hasNext(); ++i) {
                  Home h = var6.next();
                  String str = h.getName();
                  if (i != homeManager.getElements().size() - 1) {
                     base = base + "§7" + str + "§f, ";
                  } else {
                     base = base + "§7" + str;
                  }
               }

               playerEntity.sendMessage((new TranslatableText("Homes: " + base)).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);
               return 1;
            }
         }));
   }
}
