package me.joe.mpe.api.server;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;

public interface IServer {
   MinecraftServer getVanillaServer();

   PlayerManager getPlayerManager();

   String getName();

   String getVersion();

   Collection<net.minecraft.server.network.ServerPlayerEntity> getPlayerList();

   List<me.joe.mpe.api.world.impl.World> getWorlds();

   boolean isMainThread();

   Optional getPlayerByName(String var1);

   void exec(String var1);

   void setDisplayBrandName(String var1);

   String getDisplayBrandName();

   String getBrandName();
}
