package me.joe.mpe.api.world.impl;

import me.joe.mpe.api.world.IBlock;
import me.joe.mpe.api.world.IWorld;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Vec3d;

public class Block implements IBlock {
   private final BlockState block;
   private final Vec3d pos;
   private final IWorld world;

   public Block(IWorld world, BlockState block, Vec3d pos) {
      this.world = world;
      this.block = block;
      this.pos = pos;
   }

   public Vec3d getLocation() {
      return this.pos;
   }

   public IWorld getWorld() {
      return this.world;
   }
}
