package me.joe.mpe.impl.managers;

import me.joe.api.manage.impl.ArrayListManager;
import me.joe.mpe.impl.utilities.CfgFile;
import me.joe.mpe.impl.utilities.FileDirectory;

import java.util.ArrayList;
import java.util.List;

public class AFKManager extends ArrayListManager<String> {
    private CfgFile file = new CfgFile("afk_players", new FileDirectory("core"));

    public AFKManager() {
        this.init();
        this.load();
    }

    public void load() {
        for (String s : this.file.read()) {
            this.add(s);
        }
    }

    public void save() {
        List<String> lines = new ArrayList<>();

        for (String s : this.getElements()) {
            lines.add(String.format("%s", s));
        }

        this.file.write(lines);
    }
}
