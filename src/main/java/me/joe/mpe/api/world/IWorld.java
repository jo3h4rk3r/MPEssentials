package me.joe.mpe.api.world;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.Vec3d;

public interface IWorld {
   void setBlockAt(Vec3d var1, Blocks var2);

   IBlock getBlockAt(Vec3d var1);

   String getName();
}
