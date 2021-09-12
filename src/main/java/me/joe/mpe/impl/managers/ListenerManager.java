package me.joe.mpe.impl.managers;

import me.joe.api.manage.impl.ArrayListManager;
import me.joe.mpe.api.Listener;
import me.joe.mpe.impl.listeners.CommandExecutionListener;
import me.joe.mpe.impl.listeners.PacketListener;
import me.joe.mpe.impl.listeners.PlayerConnectionListener;
import me.joe.mpe.impl.listeners.PlayerListener;
import me.joe.mpe.impl.listeners.PlayerTickListener;

import java.util.Iterator;

public class ListenerManager extends ArrayListManager<Listener> {
   public ListenerManager() {
      this.init();
      this.addListeners();
   }

   private void addListeners() {
      this.add(new PlayerListener(), new CommandExecutionListener(), new PacketListener(), new PlayerTickListener(), new PlayerConnectionListener());
   }

   public void setAllToListen() {
      Iterator<Listener> var1 = this.getElements().iterator();

      while(var1.hasNext()) {
         Listener l = var1.next();
         if (!l.isListening()) {
            l.setListening(true);
         }
      }

   }
}
