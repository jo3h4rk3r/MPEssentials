package me.joe.api.event.data;

import java.lang.reflect.Method;

public class MethodData {
   private Object source;
   private Method target;
   private byte priority;

   public MethodData(Object source, Method target, byte priority) {
      this.source = source;
      this.target = target;
      this.priority = priority;
   }

   public Object getSource() {
      return this.source;
   }

   public Method getTarget() {
      return this.target;
   }

   public byte getPriority() {
      return this.priority;
   }
}
