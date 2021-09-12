package me.joe.mpe.impl.managers;

import me.joe.api.manage.impl.ArrayListManager;
import me.joe.mpe.impl.commands.homes.Home;
import me.joe.mpe.impl.utilities.CfgFile;
import me.joe.mpe.impl.utilities.FileDirectory;

import java.io.File;
import java.util.Iterator;

public class HomeManager extends ArrayListManager<Home> {
   private final String ownerUUID;
   private final FileDirectory myHomes;

   public HomeManager(String label) {
      this.init();
      this.ownerUUID = label;
      this.myHomes = new FileDirectory(label, PlayerHomeManager.homeDirectory.getDirectory());
      this.loadAllHomes();
   }

   private void loadAllHomes() {
      File directory = this.myHomes.getDirectory();
      String[] files = directory.list();

      assert files != null;

      String[] var3 = files;
      int var4 = files.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String s = var3[var5];
         String homeName = s.split("\\.")[0];
         this.add(new Home(new CfgFile(homeName, this.myHomes)));
      }

   }

   public Home get(String name) {
      Iterator<Home> var2 = this.getElements().iterator();

      Home h;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         h = var2.next();
      } while(!h.getName().equalsIgnoreCase(name));

      return h;
   }

   public String getOwnerUUID() {
      return this.ownerUUID;
   }

   public FileDirectory getHomeDirectory() {
      return this.myHomes;
   }
}
