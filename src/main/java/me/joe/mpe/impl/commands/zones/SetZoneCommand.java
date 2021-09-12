package me.joe.mpe.impl.commands.zones;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.managers.ProtectedZonesManager;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.utilities.ProtectedZone;
import me.joe.mpe.impl.utilities.math.MathUtility;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class SetZoneCommand {


   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("setzone").then(CommandManager.argument("name", StringArgumentType.string()).then(CommandManager.argument("radius", IntegerArgumentType.integer()).then(CommandManager.argument("pvp", StringArgumentType.string()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();


            if (Rank.hasPermission(playerEntity, 3)) {
               RegistryKey<World> worldKey = playerEntity.getEntityWorld().getRegistryKey();
               String argName = StringArgumentType.getString(context, "name");
               int radius = IntegerArgumentType.getInteger(context, "radius");
               String pvp = StringArgumentType.getString(context, "pvp");

               boolean shouldPvp = pvp.equalsIgnoreCase("pvpon");

               ProtectedZonesManager zoneManager = mpe.INSTANCE.getZonesManager();
               double roundedX = MathUtility.round(playerEntity.getX(), 1);
               double roundedY = MathUtility.round(playerEntity.getY(), 1);
               double roundedZ = MathUtility.round(playerEntity.getZ(), 1);
               String dimName = worldKey.getValue().getPath();
               ProtectedZone zone = new ProtectedZone(argName, dimName, new Vec3d(roundedX, roundedY, roundedZ), radius, shouldPvp, true);
               zoneManager.add(zone);
               zone.save();
               playerEntity.sendMessage((new TranslatableText(String.format("Zone §7'%s'§e set to current location", zone.getName()))).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);
            } else {
               playerEntity.sendMessage(new LiteralText("§cYou do not have access to this command!"), false);
               playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 1;
         })))));
   }
}
