package me.joe.mpe.impl.managers;

import me.joe.api.manage.impl.ArrayListManager;
import me.joe.mpe.impl.commands.warps.Warp;
import me.joe.mpe.impl.utilities.CfgFile;
import me.joe.mpe.impl.utilities.FileDirectory;

import java.io.File;
import java.util.Iterator;

public class WarpManager extends ArrayListManager<Warp> {
   public static FileDirectory warps;

   public WarpManager() {
      this.init();
      warps = new FileDirectory("warps");
      this.loadAllWarps();
   }
   private void loadAllWarps() {
      File directory = warps.getDirectory();
      String[] files = directory.list();
      assert files != null;
      String[] var3 = files;
      int var4 = files.length;
      for(int var5 = 0; var5 < var4; ++var5) {
         String s = var3[var5];
         String warpName = s.split("\\.")[0];
         this.add(new Warp(new CfgFile(warpName, warps)));
      }
   }
   public Warp get(String name) {
      Iterator<Warp> var2 = this.getElements().iterator();
      Warp h;
      do {
         if (!var2.hasNext()) {
            return null;
         }
         h = var2.next();
      } while(!h.getName().equalsIgnoreCase(name));

      return h;
   }
}
