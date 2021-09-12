package me.joe.mpe.impl.commands.homes;

import me.joe.mpe.impl.managers.PlayerHomeManager;
import me.joe.mpe.impl.utilities.CfgFile;
import me.joe.mpe.impl.utilities.FileDirectory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class Home {
   private Vec3d pos;
  // private String owner;
   private String name;
   private String dimension;
   private CfgFile file;
   private FileDirectory playerNameDirectory;
   public Home(String name, String dimension, Vec3d pos, String owner) {
      this.name = name;
      this.pos = pos;
   //   this.owner = owner;
      this.dimension = dimension;
      this.playerNameDirectory = new FileDirectory(owner, PlayerHomeManager.homeDirectory.getDirectory());
      this.file = new CfgFile(name, this.playerNameDirectory);
   }

   public Home(CfgFile cfgFile) {
      this.file = cfgFile;
      this.playerNameDirectory = cfgFile.getDirectory();
      this.name = cfgFile.getFileName();
  //    this.owner = cfgFile.getDirectory().getFolderName();
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
      return this.getPos().x;
   }

   public double getY() {
      return this.getPos().y;
   }

   public double getZ() {
      return this.getPos().z;
   }

   public Vec3d getPos() { return this.pos; }

  // public String getOwner() { return this.owner; }

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
