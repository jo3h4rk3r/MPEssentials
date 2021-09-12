package me.joe.api.manage.types;

import me.joe.api.manage.AbstractManager;

import java.util.List;

public abstract class AbstractListManager<T> extends AbstractManager {
   public List<T> elements;
}
