package me.joe.mpe.impl.commands.homes;

import net.minecraft.server.network.ServerPlayerEntity;

public class HomeTpThread extends Thread {
   private ServerPlayerEntity playerEntity;
   private Home home;

   public HomeTpThread(ServerPlayerEntity playerEntity, Home home) {
      this.playerEntity = playerEntity;
      this.home = home;
   }
   public void run() {
      try {
         sleep(3000L);
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

   }
}



