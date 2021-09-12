package me.joe.mpe.impl.utilities.math;

import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class MathUtility {
   public static final float deg2Rad = 0.017453292F;
   private static final Random random = new Random();

   public static double round(double in, int places) {
      places = MathHelper.clamp(places, 0, Integer.MAX_VALUE);
      return Double.parseDouble(String.format("%." + places + "f", in));
   }

   public static double getRandom(double min, double max) {
      return MathHelper.clamp(min + random.nextDouble() * max, min, max);
   }

   public static int getRandom(int min, int max) {
      return MathHelper.clamp(min + random.nextInt() * max, min, max);
   }

   public static double square(double a) {
      return Math.pow(a, 2.0D);
   }

   public static double getPercent(double current, double max) {
      return current / max;
   }

   public static double getIncremental(double val, double inc) {
      double one = 1.0D / inc;
      return (double)Math.round(val * one) / one;
   }

   public static boolean withinRange(double check, double min, double max) {
      return check <= max && check >= min;
   }

   public static boolean withinRange(int check, int min, int max) {
      return check <= max && check >= min;
   }

   public static boolean isInteger(String s) {
      try {
         Integer.parseInt(s);
         return true;
      } catch (NumberFormatException var2) {
         return false;
      }
   }

   public static boolean isDouble(String s) {
      try {
         Double.parseDouble(s);
         return true;
      } catch (NumberFormatException var2) {
         return false;
      }
   }
}
