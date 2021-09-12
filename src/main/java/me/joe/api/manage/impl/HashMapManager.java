package me.joe.api.manage.impl;

import me.joe.api.manage.types.AbstractMapManager;

import java.util.HashMap;
import java.util.Map;

public class HashMapManager<K, V> extends AbstractMapManager<K, V> {
   public HashMapManager() {
      this.init();
   }

   public void init() {
      this.elements = new HashMap<>();
   }

   public Object get(K key) {
      return this.elements.get(key);
   }

   public void add(K key, V value) {
      this.elements.put(key, value);
   }

   public void remove(K key) {
      this.elements.remove(key);
   }

   public boolean has(K key) {
      return this.elements.containsKey(key);
   }

   public Map<K, V> getElements() {
      return this.elements;
   }
}
