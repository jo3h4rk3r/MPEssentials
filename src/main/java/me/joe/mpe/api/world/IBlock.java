package me.joe.mpe.api.world;

import net.minecraft.util.math.Vec3d;

public interface IBlock {
   Vec3d getLocation();

   IWorld getWorld();
}
