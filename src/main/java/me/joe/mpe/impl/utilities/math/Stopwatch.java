package me.joe.mpe.impl.utilities.math;

public class Stopwatch {
   private long current = System.currentTimeMillis();

   public boolean hasReached(float delay) {
      return this.hasReached((long)delay);
   }

   public boolean hasReached(double delay) {
      return this.hasReached((long)delay);
   }

   public boolean hasReached(long delay) {
      return System.currentTimeMillis() - this.current >= delay;
   }

   public boolean hasReached(long delay, boolean reset) {
      if (reset) {
         this.reset();
      }

      return System.currentTimeMillis() - this.current >= delay;
   }

   public void reset() {
      this.current = System.currentTimeMillis();
   }

   public long getTimePassed() {
      return System.currentTimeMillis() - this.current;
   }

   public void setTimePassed(long difference) {
      this.current = System.currentTimeMillis() - difference;
   }
}
