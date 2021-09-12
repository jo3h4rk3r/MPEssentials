package me.joe.mpe.api.server;

import me.joe.mpe.api.world.impl.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Server implements IServer {
   private final MinecraftServer server;
   private final String serverBrand;
   private String serverDisplayBrand;

   public Server(MinecraftServer server, String serverBrand) {
      this.server = server;
      this.serverBrand = serverBrand;
      this.serverDisplayBrand = serverBrand;
   }

   public MinecraftServer getVanillaServer() {
      return this.server;
   }

   public PlayerManager getPlayerManager() {
      return this.server.getPlayerManager();
   }

   public String getName() {
      return null;
   }

   public String getVersion() {
      return this.server.getVersion();
   }

   public Collection<ServerPlayerEntity> getPlayerList() {
      Set<ServerPlayerEntity> players = new HashSet<ServerPlayerEntity>();
      this.server.getPlayerManager().getPlayerList().forEach((e) -> {
         players.add(e);
      });
      return players;
   }

   public List<World> getWorlds() {
      List<World> worlds = new ArrayList<World>();
      this.server.getWorlds().forEach((world) -> {
         worlds.add(new World(world));
      });
      return worlds;
   }

   public boolean isMainThread() {
      return Thread.currentThread().getName().equals("Server thread");
   }

   public Optional getPlayerByName(String playerName) {
      ServerPlayerEntity e = this.server.getPlayerManager().getPlayer(playerName);
      return e == null ? Optional.empty() : Optional.of(e);
   }

   public void exec(String command) {
      this.server.getCommandManager().execute(this.server.getCommandSource(), command);
   }

   public void setDisplayBrandName(String brand) {
      this.serverDisplayBrand = brand;
   }

   public String getDisplayBrandName() {
      return this.serverDisplayBrand;
   }

   public String getBrandName() {
      return this.serverBrand;
   }
}
