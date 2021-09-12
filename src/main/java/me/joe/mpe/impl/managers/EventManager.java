package me.joe.mpe.impl.managers;

import me.joe.api.event.Event;
import me.joe.api.event.bus.EventBus;
import me.joe.api.event.data.MethodData;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class EventManager extends EventBus {
   public void attach(Object object) {
      Method[] var2 = object.getClass().getDeclaredMethods();
      int var3 = var2.length;

      for (Method method : var2) {
         if (this.isSubscribed(method)) {
            this.attach(method, object);
         }
      }

   }

   public void detach(Object object) {
      Iterator<List<MethodData>> var2 = this.LISTENERS.values().iterator();

      while(var2.hasNext()) {
         List<MethodData> listeners = var2.next();
         listeners.removeIf((methodData) -> {
            return methodData.getSource().equals(object);
         });
      }

      this.cleanMap(true);
   }

   public Event invoke(Event event) {
      List<MethodData> dataList = (List<MethodData>)this.LISTENERS.get(event.getClass());
      if (dataList != null) {
         Iterator<MethodData> var3 = dataList.iterator();

         while(var3.hasNext()) {
            MethodData d = var3.next();
            this.invoke(d, event);
         }
      }

      return event;
   }
}
