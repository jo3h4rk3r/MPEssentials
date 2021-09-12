package me.joe.mpe.impl.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.*;
import java.util.*;

public class Data {
    private static final String CONFIG_FILE = "config/ignore.json";

    public HashMap<UUID, Set<UUID>> ignoreMap = new HashMap<>();

    public void save() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File config = new File(CONFIG_FILE);
        if(!config.exists()){
            config.createNewFile();
        }
        try (FileWriter file = new FileWriter(CONFIG_FILE)) {
            file.write(gson.toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() throws IOException{
        File config1 = new File(CONFIG_FILE);
        if(!config1.exists()){
            this.save();
        }
        FileReader reader;
        try {
            reader = new FileReader(CONFIG_FILE);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Data config = gson.fromJson(reader, Data.class);
            this.ignoreMap = config.ignoreMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}