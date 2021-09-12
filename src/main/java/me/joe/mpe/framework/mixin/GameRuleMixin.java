package me.joe.mpe.framework.mixin;


import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({GameRules.class})
public class GameRuleMixin {
}
