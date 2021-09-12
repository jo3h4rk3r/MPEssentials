package me.joe.mpe.framework.mixin;

import com.mojang.authlib.GameProfile;
//import jdk.internal.net.http.frame.PingFrame;
import me.joe.mpe.api.Rank;
import me.joe.mpe.framework.wrapped.IServerPlayerEntity;
import me.joe.mpe.impl.commands.back.BackCommand;
import me.joe.mpe.impl.commands.back.PositionAndWorld;
import me.joe.mpe.impl.commands.misc.PvPSwitchCommand;
import me.joe.mpe.impl.events.ChangeDimensionEvent;
import me.joe.mpe.impl.events.PlayerAttackEvent;
import me.joe.mpe.impl.events.PlayerDeathEvent;
import me.joe.mpe.impl.events.PlayerTickEvent;
import me.joe.mpe.impl.mpe;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.MultiplayerServerListPinger;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.realms.dto.PingResult;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.Set;

import static me.joe.mpe.impl.commands.misc.AFKCommand.isAFK;


@Mixin({ServerPlayerEntity.class})
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements IServerPlayerEntity {


   @Shadow @Final public MinecraftServer server;

   @Shadow public abstract void sendMessage(Text message, boolean actionBar);

   @Shadow
   public int pingMilliseconds;


   @Shadow public abstract ServerWorld getServerWorld();

   private double lastHorizontalSpeed = 0.0D;
   private double lastUpwardSpeed = 0.0D;
   @Shadow
   public ServerPlayNetworkHandler networkHandler;

   public ServerPlayerEntityMixin(World world, BlockPos pos,PlayerPositionLookS2CPacket yaw, GameProfile profile) {
      super(world, pos, 0, profile);
   }

   @Inject(
           at = @At("HEAD"),
           method = "moveToWorld(Lnet/minecraft/server/world/ServerWorld;)Lnet/minecraft/entity/Entity;"
   )
   public void changeDimension(ServerWorld newDimension, CallbackInfoReturnable<Entity> cir) {
      ChangeDimensionEvent event = new ChangeDimensionEvent(newDimension.getDimension(), this.networkHandler);
      mpe.INSTANCE.getEventBus().invoke(event);
   }

   @Inject(
           at = {@At("TAIL")},
           method = "tick()V"
   )
   public void playerTick(CallbackInfo info) {
      PlayerTickEvent event = new PlayerTickEvent((ServerPlayerEntity) (Object) this);
      mpe.INSTANCE.getEventBus().invoke(event);

   }

   @Inject(
           method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V",
           at = {@At("TAIL")}
   )
   private void onDeath(DamageSource damageSource_1, CallbackInfo ci) {
      ServerPlayerEntity playerEntity = (ServerPlayerEntity)(Object) this;
      PlayerDeathEvent event = new PlayerDeathEvent(playerEntity);
      mpe.INSTANCE.getEventBus().invoke(event);


   /*
      PvPSwitchCommand.PvPSwitch.remove(playerEntity.getUuid(), playerEntity.getUuid());
      playerEntity.getAbilities().invulnerable = false;
      playerEntity.sendAbilitiesUpdate();

    */
   }


   @Inject(
           at = {@At("HEAD")},
           method = "attack(Lnet/minecraft/entity/Entity;)V"
   )
   public void attackEntity(Entity entity, CallbackInfo info) {
      if (entity instanceof ServerPlayerEntity) {
         PlayerAttackEvent event = new PlayerAttackEvent((ServerPlayerEntity)(Object) this, (ServerPlayerEntity)entity);
         mpe.INSTANCE.getEventBus().invoke(event);
      }

   }

   @Inject(method = "teleport(Lnet/minecraft/server/world/ServerWorld;DDDFF)V", at = @At("HEAD"))
   private void onTeleport(ServerWorld targetWorld, double x, double y, double z, float yaw, float pitch, CallbackInfo ci) {


      //server.getCommandManager().execute(this.getCommandSource().withMaxLevel(4).withSilent(), "say IT WORKS!");

      BackCommand.previousPlayerPositions.put(this.getUuid(), new PositionAndWorld(this.getPos(), this.getServerWorld()));
   }



   @Inject(method = "getPlayerListName()Lnet/minecraft/text/Text;", at = @At("HEAD"), cancellable = true)
   private void onGetName(CallbackInfoReturnable<Text> cir) {
      Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(this.getGameProfile().getId()));
      String name = String.valueOf(this.getGameProfile().getId());
      String displayName = "" + this.getGameProfile().getName();

      if (mpe.INSTANCE.getNickManager().has(name)) {
         displayName = "" + mpe.INSTANCE.getNickManager().get(name);
      }

      String afk = "" ;
      if (mpe.INSTANCE.getAFKManager().has(String.valueOf(this.getGameProfile().getName()))) {
         afk = " §d§l*AFK*" ;
      }

      int pings = this.pingMilliseconds;
      String ping = " &7" + pings + "ms";
      TranslatableText display = new TranslatableText("%s%s%s%s", rank.prefix, displayName, afk, ping);
      //TranslatableText display = new TranslatableText("%s%s%s", rank.prefix, displayName, afk);


      AbstractTeam abstractTeam = this.getScoreboardTeam();

      if (abstractTeam instanceof Team) {
         Team team = (Team) abstractTeam;
         cir.setReturnValue(new LiteralText("").append(team.getSuffix()).append(display));
      } else {
         cir.setReturnValue(display);
      }


   }



   public double getLastHorizontalSpeed() {
      return this.lastHorizontalSpeed;
   }

   public double getLastUpwardSpeed() {
      return this.lastUpwardSpeed;
   }

   public void setLastHorizontalSpeed(double lastHorizontalSpeed) {
      this.lastHorizontalSpeed = lastHorizontalSpeed;
   }

   public void setLastUpwardSpeed(double lastUpwardSpeed) {
      this.lastUpwardSpeed = lastUpwardSpeed;
   }

   public boolean isMoving() {
      boolean horizontal = (double)this.horizontalSpeed != this.lastHorizontalSpeed;
      boolean upward = this.getVelocity().getY() != this.lastUpwardSpeed;
      return horizontal || upward;
   }

   @Shadow
   public abstract boolean isSpectator();

   @Shadow
   public abstract boolean isCreative();
}
