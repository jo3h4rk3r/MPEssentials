package me.joe.mpe.impl.commands.warps;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.managers.WarpManager;
import me.joe.mpe.impl.mpe;
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

public class SetWarpCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("setwarp").then(CommandManager.argument("name", StringArgumentType.string()).executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            RegistryKey<World> worldKey = playerEntity.getEntityWorld().getRegistryKey();
            String argName = StringArgumentType.getString(context, "name");
            if (Rank.hasPermission(playerEntity, 3)) {
               WarpManager warpManager = mpe.INSTANCE.getWarpManager();
               double roundedX = MathUtility.round(playerEntity.getX(), 1);
               double roundedY = MathUtility.round(playerEntity.getY(), 1);
               double roundedZ = MathUtility.round(playerEntity.getZ(), 1);
               String dimName = worldKey.getValue().getPath();
               Warp warp = new Warp(argName, dimName, new Vec3d(roundedX, roundedY, roundedZ));
               warpManager.add(warp);
               warp.save(roundedX, roundedY, roundedZ);
               playerEntity.sendMessage((new TranslatableText(String.format("Warp §7'%s'§e set to current location", warp.getName()))).setStyle((Style.EMPTY).withColor(Formatting.YELLOW)), false);
            } else {
               playerEntity.sendMessage(new LiteralText("§cYou do not have required permissions!"), false);
               playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            return 1;
         })));
   }
}
