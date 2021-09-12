package me.joe.mpe.framework.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.MeCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.UUID;

@Mixin({Entity.class})
public abstract class PlayerNameTagMixin {


    @Shadow @Nullable
    public abstract MinecraftServer getServer();

    @Shadow protected UUID uuid;

    @Inject(
            at = {@At("HEAD")},
            method = "getDisplayName"
                //   cancellable = true
    )
    private void PlayerNameTag(CallbackInfoReturnable<Text> cir) {



    //this.getServer().getPlayerManager().getPlayer(uuid).sendMessage(new LiteralText("FUCJHGAJSVJHVFS"), false);


             //cir.cancel();

    }
}