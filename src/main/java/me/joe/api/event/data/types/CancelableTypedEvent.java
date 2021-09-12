package me.joe.api.event.data.types;

import me.joe.api.event.data.EventType;
import me.joe.api.event.data.interfaces.Typeable;

public class CancelableTypedEvent extends CancelableEvent implements Typeable {
   private EventType type;

   public CancelableTypedEvent(EventType type) {
      this.type = type;
   }

   public EventType getType() {
      return this.type;
   }

   public void setType(EventType type) {
      this.type = type;
   }
}
