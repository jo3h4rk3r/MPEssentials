package me.joe.api.manage.types;

import me.joe.api.manage.AbstractManager;

import java.util.Map;

public abstract class AbstractMapManager<K, V> extends AbstractManager {
   public Map<K, V> elements;
}
