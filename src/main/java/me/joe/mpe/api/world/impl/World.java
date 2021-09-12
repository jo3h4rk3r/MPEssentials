package me.joe.mpe.api.world.impl;

import me.joe.mpe.api.world.IBlock;
import me.joe.mpe.api.world.IWorld;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class World implements IWorld {
   private net.minecraft.world.World serverWorld;

   public World(net.minecraft.world.World world) {
      this.serverWorld = world;
   }

   public void setBlockAt(Vec3d pos, Blocks block) {
      Optional<net.minecraft.block.Block> b = InternalBlockConverter.convertBlock(block);
      b.ifPresent((value) -> {
         this.serverWorld.setBlockState(new BlockPos(pos.x, pos.y, pos.z), value.getDefaultState());
      });
   }

   public IBlock getBlockAt(Vec3d pos) {
      return new Block(this, this.serverWorld.getBlockState(new BlockPos(pos.x, pos.y, pos.z)), pos);
   }

   public String getName() {
      return null;
   }
}
