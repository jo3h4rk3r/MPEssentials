package me.joe.mpe.impl.utilities.math.time;

import java.util.Iterator;
import java.util.LinkedList;

public class TPSHelper {
   private static int mspt = 1;
   private transient long lastPoll = System.nanoTime();
   private LinkedList<Double> history = new LinkedList<>();
   private long tickInterval = 50L;

   public TPSHelper() {
      this.history.add(20.0D);
   }

   public void run() {
      long startTime = System.nanoTime();
      long timeSpent = (startTime - this.lastPoll) / 1000L;
      if (timeSpent == 0L) {
         timeSpent = 1L;
      }

      if (this.history.size() > 10) {
         this.history.remove();
      }

      double tps = (double)this.tickInterval * 1000000.0D / (double)timeSpent;
      if (tps <= 21.0D) {
         this.history.add(tps);
      }

      this.lastPoll = startTime;
   }

   public double getAverageTPS() {
      double avg = 0.0D;
      Iterator<Double> var3 = this.history.iterator();

      while(var3.hasNext()) {
         Double f = var3.next();
         if (f != null) {
            avg += f;
         }
      }

      return avg / (double)this.history.size();

   }
}
