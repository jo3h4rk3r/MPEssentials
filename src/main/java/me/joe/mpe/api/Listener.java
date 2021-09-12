package me.joe.mpe.api;

import me.joe.mpe.impl.mpe;

public class Listener {
   private boolean listening;
   private String name;

   public Listener(String name) {
      this.name = name;
   }

   public void setListening(boolean l) {
      this.listening = l;
      if (this.listening) {
         mpe.INSTANCE.getEventBus().attach(this);
      } else {
         mpe.INSTANCE.getEventBus().detach(this);
      }

   }

   public boolean isListening() {
      return this.listening;
   }

   public String getName() {
      return this.name;
   }
}
