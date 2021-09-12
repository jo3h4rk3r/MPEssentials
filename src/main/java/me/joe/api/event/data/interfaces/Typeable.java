package me.joe.api.event.data.interfaces;

import me.joe.api.event.data.EventType;

public interface Typeable {
   EventType getType();

   void setType(EventType var1);
}
