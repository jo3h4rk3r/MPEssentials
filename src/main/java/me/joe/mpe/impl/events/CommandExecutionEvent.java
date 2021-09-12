package me.joe.mpe.impl.events;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.api.event.Event;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

public class CommandExecutionEvent extends Event {
   private final ServerCommandSource source;
   private final String command;
   private final CommandDispatcher<ServerCommandSource> dispatcher;

   public CommandExecutionEvent(CommandDispatcher<ServerCommandSource> dispatcher, ServerCommandSource source, String command) {
      this.command = command;
      this.source = source;
      this.dispatcher = dispatcher;
   }

   public String getCommand() {
      return this.command;
   }

   public ServerCommandSource getSource() {
      return this.source;
   }

   public MinecraftServer getServer() {
      return this.source.getServer();
   }

   public CommandDispatcher<ServerCommandSource> getDispatcher() {
      return this.dispatcher;
   }
}
