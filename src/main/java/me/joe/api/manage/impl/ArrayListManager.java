package me.joe.api.manage.impl;

import me.joe.api.manage.types.AbstractListManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ArrayListManager<T> extends AbstractListManager<T> {
   public void init() {
      this.elements = new ArrayList<>();
   }

   public void add(T... elements) {
      this.elements.addAll(Arrays.asList(elements));
   }

   public void remove(T... elements) {
      Arrays.stream(elements).forEachOrdered((e) -> {
         this.elements.remove(e);
      });
   }

   public boolean has(T element) {
      return this.elements.contains(element);
   }

   public Object get(Class<? extends T> clazz) {
      Iterator<T> var2 = this.getElements().iterator();

      Object m;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         m = var2.next();
      } while(m.getClass() != clazz);

      return m;
   }

   public List<T> getElements() {
      return this.elements;
   }
}
