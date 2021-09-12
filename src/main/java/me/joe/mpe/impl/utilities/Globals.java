package me.joe.mpe.impl.utilities;

import me.joe.mpe.impl.utilities.math.time.RollingAverage;

public interface Globals {
   RollingAverage TPS1 = new RollingAverage(60);
   RollingAverage TPS5 = new RollingAverage(300);
   RollingAverage TPS15 = new RollingAverage(900);

   String getServerModName();
}
