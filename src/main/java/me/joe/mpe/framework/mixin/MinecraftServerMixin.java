package me.joe.mpe.framework.mixin;

import me.joe.mpe.impl.events.ServerEvents;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.utilities.Globals;
import me.joe.mpe.impl.utilities.math.time.RollingAverage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.ServerNetworkIo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BooleanSupplier;

@Mixin({MinecraftServer.class})
public abstract class MinecraftServerMixin implements Globals {
   private int currentTick = 0;
   private long tickSection;
   @Shadow
   private long timeReference;

   @Inject(
      at = {@At("HEAD")},
      method = "shutdown"
   )
   public void close(CallbackInfo inf) {
      mpe.INSTANCE.close();
   }

   @Inject(
      at = {@At("HEAD")},
      method = "runServer"
   )
   private void run(CallbackInfo ci) {
      long start = System.nanoTime();
      this.tickSection = start;
      mpe.INSTANCE.main();
   }

   @Inject(
      at = {@At("HEAD")},
      method = "tick(Ljava/util/function/BooleanSupplier;)V"
   )
   private void tick(BooleanSupplier supplier, CallbackInfo ci) {
      long curTime;
      long i = (curTime = System.nanoTime()) / 1000000L - this.timeReference;
      if (++this.currentTick % 20 == 0) {
         long diff = curTime - this.tickSection;
         BigDecimal currentTps = RollingAverage.TPS_BASE.divide(new BigDecimal(diff), 30, RoundingMode.HALF_UP);
         TPS1.add(currentTps, diff);
         TPS5.add(currentTps, diff);
         TPS15.add(currentTps, diff);
         this.tickSection = curTime;
      }
   }

   @Inject(at = @At("HEAD"), method = "tick")
   private void ke$onTickStart(BooleanSupplier booleanSupplier, CallbackInfo ci) {
      ServerEvents.TICK.invoker().onTick();
   }

   @Inject(method = "prepareStartRegion", at = @At(value = "HEAD"), cancellable = true)
   public void noSpawnChunks(CallbackInfo ci) {
      /*if (!ServerSettings.getBoolean("patch.load_spawn"))*/ ci.cancel();
   }


   @Shadow
   public abstract PlayerManager getPlayerManager();

   @Shadow
   public abstract ServerNetworkIo getNetworkIo();
}
