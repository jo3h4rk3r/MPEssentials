package me.joe.mpe.impl.commands.spawn;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class SpawnTpThread extends Thread {
   private ServerPlayerEntity playerEntity;
   private long startTime;

   public SpawnTpThread(ServerPlayerEntity playerEntity, long startTime) {
      this.playerEntity = playerEntity;
      this.startTime = startTime;
   }

   public void run() {
      this.playerEntity.sendMessage(new LiteralText("§eTeleporting in 3 seconds... §cDo not move!"), false);
   }
}
