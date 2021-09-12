package me.joe.api.event.data.types;

import me.joe.api.event.Event;
import me.joe.api.event.data.EventType;
import me.joe.api.event.data.interfaces.Typeable;

public class TypedEvent extends Event implements Typeable {
   private EventType type;

   public TypedEvent(EventType type) {
      this.type = type;
   }

   public EventType getType() {
      return this.type;
   }

   public void setType(EventType type) {
      this.type = type;
   }
}
