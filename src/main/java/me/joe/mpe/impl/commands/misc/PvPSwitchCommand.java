package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.utilities.ProtectedZone;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

import java.util.HashMap;
import java.util.UUID;

public class PvPSwitchCommand {
    public static int count = 0;
    public static HashMap<UUID, UUID> PvPSwitch = new HashMap<UUID, UUID>();
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("pvp").then(CommandManager.literal("on").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
          //  ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");


            ProtectedZone zone = mpe.INSTANCE.getZonesManager().getZonePlayerIsIn(playerEntity);
            if (zone != null) {
                if (!(PvPSwitchCommand.PvPSwitch.containsKey(playerEntity.getUuid())))  {
                    playerEntity.sendMessage(new LiteralText("§aPvP is now enabled"), false);
                    PvPSwitch.put(playerEntity.getUuid(), playerEntity.getUuid());

                    playerEntity.getAbilities().invulnerable = false;
                    playerEntity.sendAbilitiesUpdate();

                } else {
                    playerEntity.sendMessage(new LiteralText("§cPvP is already enabled"), false);

                }
            } else {

                playerEntity.sendMessage(new LiteralText("§cThis is only for protected areas"), false);
                PvPSwitch.remove(playerEntity.getUuid(), playerEntity.getUuid());
                playerEntity.getAbilities().invulnerable = false;
                playerEntity.sendAbilitiesUpdate();

            }



            //playerEntity.damage(DamageSource.STARVE, 1);
            return 1;
        })));
        dispatcher.register(CommandManager.literal("pvp").then(CommandManager.literal("off").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
          //  ServerPlayerEntity targetedPlayer = EntityArgumentType.getPlayer(context, "player");


            ProtectedZone zone = mpe.INSTANCE.getZonesManager().getZonePlayerIsIn(playerEntity);
            if (zone != null) {
                if (PvPSwitchCommand.PvPSwitch.containsKey(playerEntity.getUuid()))  {
                    playerEntity.sendMessage(new LiteralText("§aPvP is now disabled"), false);


                    playerEntity.getAbilities().invulnerable = true;
                    playerEntity.sendAbilitiesUpdate();

                    PvPSwitch.remove(playerEntity.getUuid(), playerEntity.getUuid());
                } else {
                    playerEntity.sendMessage(new LiteralText("§cPvP is already disabled" + playerEntity.getGameProfile().getName()), false);

                }
            } else {

                playerEntity.sendMessage(new LiteralText("§cThis is only for protected areas"), false);
                PvPSwitch.remove(playerEntity.getUuid(), playerEntity.getUuid());
                playerEntity.getAbilities().invulnerable = false;
                playerEntity.sendAbilitiesUpdate();
            }



            //playerEntity.damage(DamageSource.STARVE, 1);
            return 1;
        })));
    }
}
