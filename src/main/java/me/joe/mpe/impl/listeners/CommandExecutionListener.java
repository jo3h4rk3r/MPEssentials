package me.joe.mpe.impl.listeners;

import me.joe.api.event.data.interfaces.Subscribe;
import me.joe.mpe.api.Listener;
import me.joe.mpe.impl.events.CommandExecutionEvent;

public class CommandExecutionListener extends Listener {
   public CommandExecutionListener() {
      super("CommandExecution");
   }

   @Subscribe
   public void onCommandExectue(CommandExecutionEvent event) {
   }
}
