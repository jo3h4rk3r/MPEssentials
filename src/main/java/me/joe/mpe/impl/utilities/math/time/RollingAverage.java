package me.joe.mpe.impl.utilities.math.time;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RollingAverage {
   public static final int SAMPLE_INTERVAL = 20;
   public static final BigDecimal TPS_BASE = (new BigDecimal("1.0E9")).multiply(new BigDecimal(20));
   private static final int TPS = 20;
   public static final int TICK_TIME = 50000000;
   private static final long SEC_IN_NANO = 1000000000L;
   private static final long MAX_CATCHUP_BUFFER = 60000000000L;
   private final int size;
   private final BigDecimal[] samples;
   private final long[] times;
   private long time;
   private BigDecimal total;
   private int index = 0;

   public RollingAverage(int size) {
      this.size = size;
      this.time = (long)size * 1000000000L;
      this.total = dec(20L).multiply(dec(1000000000L)).multiply(dec((long)size));
      this.samples = new BigDecimal[size];
      this.times = new long[size];

      for(int i = 0; i < size; ++i) {
         this.samples[i] = dec(20L);
         this.times[i] = 1000000000L;
      }

   }

   private static BigDecimal dec(long t) {
      return new BigDecimal(t);
   }

   public void add(BigDecimal x, long t) {
      this.time -= this.times[this.index];
      this.total = this.total.subtract(this.samples[this.index].multiply(dec(this.times[this.index])));
      this.samples[this.index] = x;
      this.times[this.index] = t;
      this.time += t;
      this.total = this.total.add(x.multiply(dec(t)));
      if (++this.index == this.size) {
         this.index = 0;
      }

   }

   public double getAverage() {
      return this.total.divide(dec(this.time), 30, RoundingMode.HALF_UP).doubleValue();
   }
}
