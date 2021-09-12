package me.joe.mpe.impl.managers;

import me.joe.api.manage.impl.HashMapManager;
import me.joe.mpe.impl.utilities.CfgFile;
import me.joe.mpe.impl.utilities.FileDirectory;

import java.util.ArrayList;
import java.util.List;

public class NickManager extends HashMapManager<String, String> {
   private final CfgFile file = new CfgFile("nicks", new FileDirectory("core"));

   public NickManager() {
      this.init();
      this.load();
   }

   public void load() {
      for (String line : this.file.read()) {
         String uuid = line.split(":")[0];
         String nickName = line.split(":")[1];
         this.add(uuid, nickName);
      }
   }

   public void save() {
      List<String> lines = new ArrayList<>();

      for (String s : this.getElements().keySet()) {
         lines.add(String.format("%s:%s", s, this.get(s)));
      }

      this.file.write(lines);
   }
}
