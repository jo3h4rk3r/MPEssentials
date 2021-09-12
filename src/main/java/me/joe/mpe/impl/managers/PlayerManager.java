package me.joe.mpe.impl.managers;

import me.joe.api.manage.impl.ArrayListManager;
import me.joe.mpe.impl.utilities.CfgFile;
import me.joe.mpe.impl.utilities.FileDirectory;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;

public class PlayerManager extends ArrayListManager<CfgFile> {
   public static FileDirectory playersDir;

   public PlayerManager() {
      this.init();
      playersDir = new FileDirectory("players");
      this.loadAllPlayers();
   }

   public void addNewPlayer(String uuid, String name) {
      CfgFile file = new CfgFile(uuid, playersDir);
      file.write(Collections.singletonList(name));
      this.add(file);
   }

   public boolean has(String uuid) {
      Iterator<CfgFile> var2 = this.getElements().iterator();

      CfgFile cfgFile;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         cfgFile = var2.next();
      } while(!cfgFile.getFileName().equalsIgnoreCase(uuid));

      return true;
   }

   public CfgFile get(String uuid) {
      Iterator<CfgFile> var2 = this.getElements().iterator();

      CfgFile cfgFile;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         cfgFile = var2.next();
      } while(!cfgFile.getFileName().equalsIgnoreCase(uuid));

      return cfgFile;
   }

   public String getPlayerName(String uuid) {
      CfgFile file = this.get(uuid);
      String u = "";
      if (file != null && file.read().size() > 0) {
         u = (String)file.read().get(0);
      }

      return u;
   }

   public String getUUID(String name) {
      Iterator<CfgFile> var2 = this.getElements().iterator();

      CfgFile f;
      do {
         if (!var2.hasNext()) {
            return "";
         }

         f = var2.next();
      } while(!((String)f.read().get(0)).equalsIgnoreCase(name));

      return f.getFileName();
   }

   private void loadAllPlayers() {
      File directory = playersDir.getDirectory();
      String[] folders = directory.list();

      assert folders != null;

      String[] var3 = folders;
      int var4 = folders.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String s = var3[var5];
         String name = s.split("\\.")[0];
         this.add(new CfgFile(name, playersDir));
      }

   }
}
