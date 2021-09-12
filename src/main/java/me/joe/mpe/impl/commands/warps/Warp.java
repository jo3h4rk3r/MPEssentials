package me.joe.mpe.impl.commands.warps;

import me.joe.mpe.impl.managers.WarpManager;
import me.joe.mpe.impl.utilities.CfgFile;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class Warp {
   private Vec3d pos;
   private String name;
   private String dimension;
   private CfgFile file;

   public Warp(String name, String dimension, Vec3d pos) {
      this.name = name;
      this.pos = pos;
      this.dimension = dimension;
      this.file = new CfgFile(name, WarpManager.warps);
   }

   public Warp(CfgFile cfgFile) {
      this.file = cfgFile;
      this.name = cfgFile.getFileName();
      List<String> lines = cfgFile.read();

      for (String line : lines) {
         String[] params = line.split(":");
         if (params[0].equalsIgnoreCase("coords")) {
            String[] coords = params[1].split(",");
            this.pos = new Vec3d(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2]));
         }

         if (params[0].equalsIgnoreCase("dimension")) {
            this.dimension = params[1];
         }
      }

   }

   public void save(double x, double y, double z) {
      List<String> lines = new ArrayList<>();
      lines.add(String.format("coords:%s,%s,%s", x, y, z));
      lines.add("dimension:" + this.dimension);
      this.file.write(lines);
   }

   public double getX() {
      return this.getPos().getX();
   }

   public double getY() {
      return this.getPos().getY();
   }

   public double getZ() {
      return this.getPos().getZ();
   }

   public Vec3d getPos() {
      return this.pos;
   }

   public String getName() {
      return this.name;
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

   public CfgFile getFile() {
      return this.file;
   }
}
