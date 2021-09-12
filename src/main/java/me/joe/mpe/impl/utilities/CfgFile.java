package me.joe.mpe.impl.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CfgFile {
   private String fileName;
   private File file;
   private FileDirectory directory;

   public CfgFile(String name, FileDirectory directory) {
      this.fileName = name;
      this.directory = directory;
      this.file = this.getFile();
   }

   public List<String> read() {
      List<String> readContent = new ArrayList<>();

      try {
         BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8));

         String str;
         while((str = in.readLine()) != null) {
            readContent.add(str);
         }

         in.close();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return readContent;
   }

   public void write(List<String> writeContent) {
      try {
         Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.file), StandardCharsets.UTF_8));

         for (String outputLine : writeContent) {
            out.write(outputLine + System.getProperty("line.separator"));
         }

         out.close();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   public void delete() {
      if (this.file.exists()) {
         try {
            this.file.delete();
         } catch (Exception var2) {
            var2.printStackTrace();
         }
      }

   }

   public void rename(String fName) {
      File file = new File(this.directory.getDirectory(), fName + ".cfg");
      this.getFile().renameTo(file);
   }

   public File getFile() {
      File file = new File(this.directory.getDirectory(), String.format("%s.cfg", this.fileName));
      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

      return file;
   }

   public String getFileName() {
      return this.fileName;
   }

   public FileDirectory getDirectory() {
      return this.directory;
   }
}
