package me.joe.mpe.impl.commands.homes;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.mpe.api.Command;
import me.joe.mpe.impl.managers.HomeManager;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.utilities.math.MathUtility;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class SetHomeCommand extends Command {
   public SetHomeCommand() {
      super("SetHome", "Set your homes");
   }

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("sethome").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            RegistryKey<World> worldKey = playerEntity.getEntityWorld().getRegistryKey();
            String playerUUID = String.valueOf(playerEntity.getGameProfile().getId());
            if (!mpe.INSTANCE.getPlayerHomeManager().has(playerUUID)) {
               mpe.INSTANCE.getPlayerHomeManager().add(playerUUID, new HomeManager(playerUUID));
            }

            int maxHomes = mpe.INSTANCE.getPlayerHomeManager().maxHomes(playerUUID);
            HomeManager homeManager = (HomeManager)mpe.INSTANCE.getPlayerHomeManager().get(playerUUID);
            if (homeManager.getElements().size() < maxHomes) {
               String argName = "home";
               if (homeManager.get(argName) == null) {
                  double roundedX = MathUtility.round(playerEntity.getX(), 1);
                  double roundedY = MathUtility.round(playerEntity.getY(), 1);
                  double roundedZ = MathUtility.round(playerEntity.getZ(), 1);
                  String dimName = worldKey.getValue().getPath();
                  Home home = new Home(argName, dimName, new Vec3d(roundedX, roundedY, roundedZ), playerUUID);
                  home.save(roundedX, roundedY, roundedZ);
                  homeManager.add(home);
                  playerEntity.sendMessage((new TranslatableText("Home set to current location")).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);
               } else {
                  playerEntity.sendMessage(new LiteralText("§cYou have an existing home with that name"), false);
               }
            } else {
               playerEntity.sendMessage((new TranslatableText("You have already set your max homes of " + maxHomes)).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
            }

            return 1;
         }));
         dispatcher.register(CommandManager.literal("sethome").then(CommandManager.argument("name", StringArgumentType.string()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            RegistryKey<World> worldKey = playerEntity.getEntityWorld().getRegistryKey();
            String uuid = String.valueOf(playerEntity.getGameProfile().getId());
            if (!mpe.INSTANCE.getPlayerHomeManager().has(uuid)) {
               mpe.INSTANCE.getPlayerHomeManager().add(uuid, new HomeManager(uuid));
            }

            HomeManager homeManager = (HomeManager)mpe.INSTANCE.getPlayerHomeManager().get(uuid);
            int maxHomes = mpe.INSTANCE.getPlayerHomeManager().maxHomes(uuid);
            if (homeManager.getElements().size() < maxHomes) {
               String argName = StringArgumentType.getString(context, "name");
               if (homeManager.get(argName) == null) {
                  double roundedX = MathUtility.round(playerEntity.getX(), 1);
                  double roundedY = MathUtility.round(playerEntity.getY(), 1);
                  double roundedZ = MathUtility.round(playerEntity.getZ(), 1);
                  String dimName = worldKey.getValue().getPath();
                  Home home = new Home(argName, dimName, new Vec3d(roundedX, roundedY, roundedZ), uuid);
                  homeManager.add(home);
                  home.save(roundedX, roundedY, roundedZ);
                  playerEntity.sendMessage((new TranslatableText(String.format("Home §7'%s'§e set to current location", home.getName()))).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);
               } else {
                  playerEntity.sendMessage(new LiteralText("§cYou have an existing home with that name"), false);
               }
            } else {
               playerEntity.sendMessage((new TranslatableText("You have already set your max homes of " + maxHomes)).setStyle((Style.EMPTY).withColor(Formatting.RED)), false);
            }

            return 1;
         })));
   }
}
