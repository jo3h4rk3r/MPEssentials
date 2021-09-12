package me.joe.api.event.bus;

import me.joe.api.event.Event;
import me.joe.api.event.data.EventPriority;
import me.joe.api.event.data.MethodData;
import me.joe.api.event.data.interfaces.Subscribe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
   protected final Map<Class<?>, List<MethodData>> LISTENERS = new HashMap<Class<?>, List<MethodData>>();

   protected void detach(Object object, Class eventClass) {
      if (this.LISTENERS.containsKey(eventClass)) {
         Iterator var3 = ((List)this.LISTENERS.get(eventClass)).iterator();

         while(var3.hasNext()) {
            MethodData methodData = (MethodData)var3.next();
            if (methodData.getSource().equals(object)) {
               ((List)this.LISTENERS.get(eventClass)).remove(methodData);
            }
         }

         this.cleanMap(true);
      }

   }

   public void attach(Method method, Object object) {
      Class<?> indexClass = method.getParameterTypes()[0];
      final MethodData data = new MethodData(object, method, method.getAnnotation(Subscribe.class).value());
      if (!data.getTarget().isAccessible()) {
         data.getTarget().setAccessible(true);
      }

      if (this.LISTENERS.containsKey(indexClass)) {
         if (!this.LISTENERS.get(indexClass).contains(data)) {
            this.LISTENERS.get(indexClass).add(data);
            this.sortListValue(indexClass);
         }
      } else {
         this.LISTENERS.put(indexClass, new CopyOnWriteArrayList() {
            {
               this.add(data);
            }
         });
      }

   }

   public void removeEntry(Class indexClass) {
      Iterator<Entry<Class<?>, List<MethodData>>> mapIterator = this.LISTENERS.entrySet().iterator();

      while(mapIterator.hasNext()) {
         if (((Class)(mapIterator.next()).getKey()).equals(indexClass)) {
            mapIterator.remove();
            break;
         }
      }

   }

   public void cleanMap(boolean onlyEmptyEntries) {
      Iterator<Entry<Class<?>, List<MethodData>>> mapIterator = this.LISTENERS.entrySet().iterator();

      while(true) {
         do {
            if (!mapIterator.hasNext()) {
               return;
            }
         } while(onlyEmptyEntries && !((List)(mapIterator.next()).getValue()).isEmpty());

         mapIterator.remove();
      }
   }

   private void sortListValue(Class<?> indexClass) {
      List<MethodData> sortedList = new CopyOnWriteArrayList<>();
      byte[] var3 = EventPriority.VALUE_ARRAY;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         byte priority = var3[var5];
         Iterator var7 = ((List)this.LISTENERS.get(indexClass)).iterator();

         while(var7.hasNext()) {
            MethodData d = (MethodData)var7.next();
            if (d.getPriority() == priority) {
               sortedList.add(d);
            }
         }
      }

      this.LISTENERS.put(indexClass, sortedList);
   }

   public boolean isSubscribed(Method method) {
      return method.getParameterTypes().length == 1 && method.isAnnotationPresent(Subscribe.class);
   }

   public void invoke(MethodData d, Event event) {
      try {
         d.getTarget().invoke(d.getSource(), event);
      } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var4) {
      }
   }
}
