package me.joe.mpe.impl.commands.homes;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.managers.HomeManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.Iterator;

public class DelHomeCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("delhome").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            String uuid = String.valueOf(playerEntity.getGameProfile().getId());
            if (!mpe.INSTANCE.getPlayerHomeManager().has(uuid)) {
               playerEntity.sendMessage((new TranslatableText("You have not set any homes")).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
               return 1;
            } else {
               HomeManager homeManager = (HomeManager)mpe.INSTANCE.getPlayerHomeManager().get(uuid);
               String base = "";
               int i = 0;

               for(Iterator<Home> var6 = homeManager.getElements().iterator(); var6.hasNext(); ++i) {
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
         dispatcher.register(CommandManager.literal("delhome").then(CommandManager.argument("name", StringArgumentType.string()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            String argName = StringArgumentType.getString(context, "name");
            String uuid = String.valueOf(playerEntity.getGameProfile().getId());
            HomeManager homeManager = (HomeManager)mpe.INSTANCE.getPlayerHomeManager().get(uuid);
            Home home = homeManager.get(argName);
            if (homeManager.has(home)) {
               homeManager.remove(home);
               if (homeManager.getElements().isEmpty()) {
                  mpe.INSTANCE.getPlayerHomeManager().remove(uuid);
                  home.getFile().delete();
                  home.getFile().getDirectory().getDirectory().delete();
               } else {
                  home.getFile().delete();
               }

               playerEntity.sendMessage((new TranslatableText("Deleted home §7'" + argName + "'")).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);
            } else {
               playerEntity.sendMessage((new TranslatableText("Could not find home §7'" + argName + "'")).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
            }

            return 1;
         })));
   }
}
