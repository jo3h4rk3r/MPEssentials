package me.joe.api.event.data.types;

import me.joe.api.event.Event;
import me.joe.api.event.data.interfaces.Cancelable;

public class CancelableEvent extends Event implements Cancelable {
   private boolean cancelled;

   public CancelableEvent() {
      this.setCanceled(false);
   }

   public boolean isCanceled() {
      return this.cancelled;
   }

   public void setCanceled(boolean cancelled) {
      this.cancelled = cancelled;
   }
}
