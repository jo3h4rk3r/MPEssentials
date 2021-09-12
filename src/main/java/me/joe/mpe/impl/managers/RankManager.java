package me.joe.mpe.impl.managers;

import me.joe.api.manage.impl.HashMapManager;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.utilities.CfgFile;
import me.joe.mpe.impl.utilities.FileDirectory;

import java.util.ArrayList;
import java.util.List;

public class RankManager extends HashMapManager<String, Rank> {
   private final CfgFile file = new CfgFile("ranks", new FileDirectory("core"));

   public RankManager() {
      this.init();
      this.load();
   }

   public Rank get(String uuid) {
      return this.getElements().get(uuid);
   }

   public void load() {

      for (String line : this.file.read()) {
         String uuidS = line.split(":")[0];
         String rankName = line.split(":")[1];
         this.add(uuidS, Rank.getRank(rankName));
      }

   }

   public void save() {
      List<String> lines = new ArrayList<>();

      for (String s : this.getElements().keySet()) {
         lines.add(String.format("%s:%s", s, this.get(s).name));
      }

      this.file.write(lines);
   }
}
