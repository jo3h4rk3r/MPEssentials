package me.joe.mpe.api;

public abstract class Command {
   private boolean showInHelp;
   private String name;
   private String description;

   public Command(String name, String description) {
      this(name, description, true);
   }

   public Command(String name, String description, boolean show) {
      this.showInHelp = true;
      this.name = name;
      this.description = description;
      this.showInHelp = show;
   }

    public void register() {
    }

    public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public boolean shouldShowInHelp() {
      return this.showInHelp;
   }
}
