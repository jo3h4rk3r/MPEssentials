package me.joe.mpe.impl.listeners;

import me.joe.api.event.data.interfaces.Subscribe;
import me.joe.mpe.api.Listener;
import me.joe.mpe.impl.commands.back.BackCommand;
import me.joe.mpe.impl.commands.back.PositionAndWorld;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.events.ChangeDimensionEvent;
import me.joe.mpe.impl.events.PlayerAttackEvent;
import me.joe.mpe.impl.events.PlayerDeathEvent;
import me.joe.mpe.impl.utilities.CfgFile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Collections;

public class PlayerListener extends Listener {
   public PlayerListener() {
      super("Player");
   }

   @Subscribe
   public void listenForDeath(PlayerDeathEvent event) {
      PlayerEntity player = event.getPlayer();

      BackCommand.previousPlayerPositions.put(player.getUuid(), new PositionAndWorld(player.getPos(), (ServerWorld) player.world));
   }

   @Subscribe
   public void changeDimensionListener(ChangeDimensionEvent event) {
      if (!BackCommand.previousPlayerPositions.containsKey(event.getPlayer().getUuid())) {
         this.writeLastDimension(event);
      }
   }

   private void writeLastDimension(ChangeDimensionEvent event) {
      CfgFile file = mpe.INSTANCE.getPlayerManager().get(String.valueOf(event.getPlayer().getGameProfile().getId()));
      file.write(Collections.singletonList("dimensionBeforeDeath:" + event.getDimension().toString().split(":")[1]));
   }

   @Subscribe
   public void playerAttack(PlayerAttackEvent event) {
   }
}
