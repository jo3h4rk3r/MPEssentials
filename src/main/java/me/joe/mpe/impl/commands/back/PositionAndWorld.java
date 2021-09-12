package me.joe.mpe.impl.commands.back;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class PositionAndWorld {
    private final Vec3d position;
    private final ServerWorld world;

    public PositionAndWorld(Vec3d position, ServerWorld world) {
        this.position = position;
        this.world = world;
    }

    public Vec3d getPosition() {
        return position;
    }

    public ServerWorld getWorld() {
        return world;
    }
}
