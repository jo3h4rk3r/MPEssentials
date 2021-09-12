package me.joe.mpe.impl.managers;

import me.joe.api.manage.impl.ArrayListManager;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.utilities.ProtectedZone;
import me.joe.mpe.impl.utilities.CfgFile;
import me.joe.mpe.impl.utilities.FileDirectory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

import java.io.File;
import java.util.Iterator;

public class ProtectedZonesManager extends ArrayListManager<ProtectedZone> {
   public static FileDirectory zones;

   public ProtectedZonesManager() {
      this.init();
      zones = new FileDirectory("zones");
      this.loadAllZones();
   }

   private void loadAllZones() {
      File directory = zones.getDirectory();
      String[] files = directory.list();

      assert files != null;

      String[] var3 = files;
      int var4 = files.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String s = var3[var5];
         String zoneName = s.split("\\.")[0];
         this.add(new ProtectedZone(new CfgFile(zoneName, zones)));
      }

   }

   public ProtectedZone get(String name) {
      /*for (ProtectedZone zone : this.getElements()) {
         if (zone.getName().equalsIgnoreCase(name)) {
            return zone;
         }
      }*/

      Iterator var2 = this.getElements().iterator();

      ProtectedZone h;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         h = (ProtectedZone)var2.next();
      } while(!h.getName().equalsIgnoreCase(name));

      return h;
   }

   public ActionResult getActionResult(PlayerEntity player) {
      return (Rank.canBuild(player) || getZonePlayerIsIn(player) == null) ? ActionResult.PASS : ActionResult.SUCCESS;
   }

   public ProtectedZone getZonePlayerIsIn(PlayerEntity player) {
      Iterator var2 = this.getElements().iterator();

      ProtectedZone zone;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         zone = (ProtectedZone)var2.next();
      } while(!zone.isPlayerInside(player));

      return zone;
   }
}
