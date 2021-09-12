package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.framework.mixin.ServerPlayerEntityMixin;
import me.joe.mpe.framework.wrapped.IServerPlayerEntity;
import me.joe.mpe.impl.utilities.math.MathUtility;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class PosHUDCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
            dispatcher.register(CommandManager.literal("hud").executes((context) -> {
                ServerPlayerEntity player = context.getSource().getPlayer();




                return 0;
            }));
    }

}