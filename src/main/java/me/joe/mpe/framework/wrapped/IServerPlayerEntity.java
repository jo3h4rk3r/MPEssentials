package me.joe.mpe.framework.wrapped;

public interface IServerPlayerEntity {
   double getLastHorizontalSpeed();

   double getLastUpwardSpeed();

   boolean isMoving();

   void setLastHorizontalSpeed(double var1);

   void setLastUpwardSpeed(double var1);
}
