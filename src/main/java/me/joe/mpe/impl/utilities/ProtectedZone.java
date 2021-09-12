package me.joe.mpe.impl.utilities;

import me.joe.mpe.impl.managers.ProtectedZonesManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ProtectedZone {
   private String name;
   private String dimension;
   private CfgFile file;
   private Vec3d centerBlock;
   private int radius;
   private boolean expandToSky;
   private GameMode checkGamemode;
   private GameMode setGamemode;
   private boolean pvp;

   public ProtectedZone(String name, String dimension, Vec3d centerBlock, int radius, boolean pvp, boolean shouldExpandToSky) {
      this.name = name;
      this.dimension = dimension;
      this.centerBlock = centerBlock;
      this.radius = radius;
      this.expandToSky = shouldExpandToSky;
      this.checkGamemode = GameMode.SURVIVAL;
      this.setGamemode = GameMode.ADVENTURE;
      this.pvp = pvp;
      this.file = new CfgFile(name, ProtectedZonesManager.zones);
   }

   public ProtectedZone(CfgFile cfgFile) {
      this.file = cfgFile;
      this.name = cfgFile.getFileName();
      this.load(cfgFile);
   }

   private void load(CfgFile cfgFile) {
      List<String> lines = cfgFile.read();
      for (String line : lines) {
         String[] params = line.split(":");
         if (params[0].equalsIgnoreCase("coords")) {
            String[] coords = params[1].split(",");
            this.centerBlock = new Vec3d(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2]));
         }

         if (params[0].equalsIgnoreCase("dimension")) {
            this.dimension = params[1];
         }

         if (params[0].equalsIgnoreCase("radius")) {
            this.radius = Integer.parseInt(params[1]);
         }

         if (params[0].equalsIgnoreCase("pvp")) {
            this.pvp = Boolean.parseBoolean(params[1]);
         }
      }

      this.expandToSky = true;
      this.checkGamemode = GameMode.SURVIVAL;
      this.setGamemode = GameMode.ADVENTURE;
   }

   public void save() {
      List<String> lines = new ArrayList<>();
      lines.add(String.format("coords:%s,%s,%s", this.getX(), this.getY(), this.getZ()));
      lines.add(String.format("radius:%s", this.radius));
      lines.add(String.format("expand:%s", this.expandToSky));
      lines.add("dimension:" + this.dimension);
      lines.add("pvp:" + this.pvp);
      this.file.write(lines);
   }

   public boolean isPlayerInside(PlayerEntity player) {
      boolean inDimension = player.world.getRegistryKey() == this.getDimension();
      boolean withinX = player.getX() < this.centerBlock.x + (double)this.radius && player.getX() > this.centerBlock.x - (double)this.radius;
      boolean withinZ = player.getZ() < this.centerBlock.z + (double)this.radius && player.getZ() > this.centerBlock.z - (double)this.radius;
      return withinX && withinZ && inDimension;
   }

   public Vec3d getCenterBlock() {
      return this.centerBlock;
   }

   public double getX() {
      return this.centerBlock.getX();
   }

   public double getY() {
      return this.centerBlock.getY();
   }

   public double getZ() {
      return this.centerBlock.getZ();
   }

   public int getRadius() {
      return this.radius;
   }

   public boolean isExpandedToSky() {
      return this.expandToSky;
   }

   public boolean isPvp() {
      return this.pvp;
   }

   public String getName() {
      return this.name;
   }

   public CfgFile getFile() {
      return this.file;
   }

   public boolean isExpandToSky() {
      return this.expandToSky;
   }

   public GameMode getCheckGamemode() {
      return this.checkGamemode;
   }

   public GameMode getSetGamemode() {
      return this.setGamemode;
   }

   public RegistryKey<World> getDimension() {
      switch (this.dimension) {
         case "the_nether":
            return World.NETHER;
         case "the_end":
            return World.END;
         default:
         case "overworld":
            return World.OVERWORLD;
      }
   }

   public GameMode getGamemode(String check) {
      byte var3 = -1;
      switch(check.hashCode()) {
      case -1684593425:
         if (check.equals("spectator")) {
            var3 = 3;
         }
         break;
      case -1600582850:
         if (check.equals("survival")) {
            var3 = 1;
         }
         break;
      case -694094064:
         if (check.equals("adventure")) {
            var3 = 2;
         }
         break;
      case 1820422063:
         if (check.equals("creative")) {
            var3 = 0;
         }
      }

      switch(var3) {
      case 0:
         return GameMode.CREATIVE;
      case 1:
         return GameMode.SURVIVAL;
      case 2:
         return GameMode.ADVENTURE;
      case 3:
         return GameMode.SPECTATOR;
      default:
         return GameMode.ADVENTURE;
      }
   }
}
