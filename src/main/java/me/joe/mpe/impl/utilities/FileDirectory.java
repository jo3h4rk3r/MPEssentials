package me.joe.mpe.impl.utilities;

import me.joe.mpe.impl.mpe;

import java.io.File;

public class FileDirectory {
   private String folderName;
   private File parentDirectory;

   public FileDirectory(String folderName, File fileDirectory) {
      this.folderName = folderName;
      this.parentDirectory = fileDirectory;
   }

   public FileDirectory(String folderName) {
      this(folderName, mpe.INSTANCE.DIRECTORY);
   }

   public void rename(String dirName) {
      File file = new File(this.parentDirectory, dirName);
      this.getDirectory().renameTo(file);
   }

   public File getDirectory() {
      File file = new File(this.parentDirectory, this.folderName);
      if (!file.exists()) {
         file.mkdir();
      }

      return file;
   }

   public String getFolderName() {
      return this.folderName;
   }

   public File getParentDirectory() {
      return this.parentDirectory;
   }
}
