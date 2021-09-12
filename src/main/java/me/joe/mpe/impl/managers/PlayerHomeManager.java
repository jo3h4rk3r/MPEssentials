package me.joe.mpe.impl.managers;

import me.joe.api.manage.impl.HashMapManager;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.commands.homes.Home;
import me.joe.mpe.impl.utilities.CfgFile;
import me.joe.mpe.impl.utilities.FileDirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerHomeManager extends HashMapManager<String, HomeManager> {
   public static FileDirectory homeDirectory;

   public PlayerHomeManager() {
      this.init();
      homeDirectory = new FileDirectory("homes");
   }

   public int maxHomes(String uuid) {
      Rank r = mpe.INSTANCE.getRankManager().get(uuid);
      return Rank.getMaxHomes(r);
   }

   public void loadPlayersHomes(String uuid, String name) {
      File directory = homeDirectory.getDirectory();
      String[] folders = directory.list();

      assert folders != null;

      for (String s : folders) {
         if (s.equalsIgnoreCase(uuid) || s.equalsIgnoreCase(name)) {
            this.add(s, new HomeManager(s));
         }
      }
   }

   private void loadAllPlayers() {
      File directory = homeDirectory.getDirectory();
      String[] folders = directory.list();

      assert folders != null;

      for (String s : folders) {
         this.add(s, new HomeManager(s));
      }
   }

   public List<Home> getAllHomesFromFiles(String dirName) {
      File directory = homeDirectory.getDirectory();
      String[] folders = directory.list();
      List<Home> r = new ArrayList<>();

      assert folders != null;

      for (String s : folders) {
         if (s.equalsIgnoreCase(dirName)) {
            FileDirectory dd = new FileDirectory(s, homeDirectory.getDirectory());
            File d = dd.getDirectory();
            String[] files = d.list();

            assert files != null;

            for (String ss : files) {
               String homeName = ss.split("\\.")[0];
               Home h = new Home(new CfgFile(homeName, dd));
               r.add(h);
            }
         }
      }

      return r;
   }
}
