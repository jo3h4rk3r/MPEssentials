package me.joe.mpe.api;

import me.joe.mpe.impl.mpe;
import net.minecraft.entity.player.PlayerEntity;

public enum Rank {

  /* GUEST("Guest", "", 3, 0),
   DONOR("Donor", String.format("%s%sDONOR%s ", "§8§l", "§6§l", "§8§l"), 5, 0),
   SPONSOR("Sponsor", String.format("%s%sVIP%s ", "§8§l", "§e§l", "§8§l"), 8, 0),
   VIPPLUS("Vip+", String.format("%s%sVIP%s+%s ", "§8§l", "§e§l","§f§l", "§8§l"), 10, 0),
   MVP("Mvp", String.format("%s%sMVP%s ", "§8§l", "§5§l", "§8§l"), 12, 0),
   SUPREME("Supreme", String.format("%s%sSUPREME%s ", "§5§l", "§4§l", "§5§l"), 25, 0),
   CHAMPION("Champion", String.format("%s%sChampion%s ", "§8§l", "§4§l", "§8§l"), 25, 0),
   BUILDER("Builder", String.format("%s%sBUILDER%s ", "§6§l", "§e§l", "§6§l"), 12, 3),
   HELPER("Helper", String.format("%s%sHELPER%s ", "§1§l", "§9§l", "§1§l"), 12, 2),
   MODERATOR("Moderator", String.format("%s%sMOD%s ", "§2§l", "§a§l", "§2§l"), 12, 3),
   DEVELOPER("Developer", String.format("%s%sDEV%s ", "§5§l", "§d§l", "§5§l"), 12, 4),
   ADMIN("Admin", String.format("%s%sADMIN%s ", "§3§l", "§3§l", "§3§l"), 12, 4),
   UNDERCOVER("UnderCover", "", 3, 4),
   COOWNER("CoOwner", String.format("%s%sCoOWNER%s ", "§8§l", "§5§l", "§8§l"), 25, 4),
   OWNER("Owner", String.format("%s%sOWNER%s ", "§4§l", "§c§l", "§4§l"), 25, 4);

   */


   GUEST("Guest", "", 3, 0),
   DONOR("Donor", String.format("%s[%sDonor%s] ", "§8", "§a", "§8"), 5, 0),
   SPONSOR("Sponsor", String.format("%s[%sVIP%s] ", "§8", "§6", "§8"), 8, 0),
   VIPPLUS("Vip+", String.format("%s[%sVIP%s+%s] ", "§8", "§6","§f", "§8"), 10, 0),
   MVP("Mvp", String.format("%s[%sMVP%s] ", "§8", "§5", "§8"), 12, 0),
   SUPREME("Supreme", String.format("%s[%sSupreme%s] ", "§8", "§4", "§8"), 25, 0),
   CHAMPION("Champion", String.format("%s[%sChampion%s] ", "§8", "§4", "§8"), 30, 0),
   BUILDER("Builder", String.format("%s[%sBuilder%s] ", "§8", "§6", "§8"), 50, 3),
   HELPER("Helper", String.format("%s[%sHelper%s] ", "§8", "§9", "§8"), 50, 2),
   MODERATOR("Moderator", String.format("%s[%sMod%s] ", "§8", "§3", "§8"), 50, 3),
   DEVELOPER("Developer", String.format("%s[%sDev%s] ", "§8", "§d", "§8"), 50, 4),
   ADMIN("Admin", String.format("%s[%sAdmin%s] ", "§8", "§c", "§8"), 50, 4),
   UNDERCOVER("UnderCover", "", 3, 4),
   COOWNER("CoOwner", String.format("%s[%sCoOwner%s] ", "§8", "§5", "§8"), 100, 4),
   OWNER("Owner", String.format("%s[%sOwner%s] ", "§8", "§7", "§8"), 100, 4);



   public String name;
   public String prefix;
   public int maxHomes;
   public int permissionLevel;

   Rank(String name, String prefix, int maxHomes, int permissionLevel) {
      this.prefix = prefix;
      this.name = name;
      this.maxHomes = maxHomes;
      this.permissionLevel = permissionLevel;
   }

   public static Rank getRank(String a) {
      Rank[] var1 = values();

      for (Rank r : var1) {
         if (r.name.equalsIgnoreCase(a)) {
            return r;
         }
      }

      return GUEST;
   }

   public static boolean hasEnderchest(Rank rank) {
      return rank.ordinal() >= 2 || rank.permissionLevel >= 2;
   }

   public static boolean hasHat(Rank rank) {
      return rank.ordinal() >= 1 || rank.permissionLevel >= 1;
   }

   public static boolean hasFeed(Rank rank) {
      return rank.ordinal() >= 1 || rank.permissionLevel >= 1;
   }

   public static boolean hasCrafting(Rank rank) {
      return rank.ordinal() >= 1 || rank.permissionLevel >= 2;
   }

   public static boolean hasAnvil(Rank rank) {
      return rank.ordinal() >= 2 || rank.permissionLevel >= 2;
   }

   public static boolean hasNick(Rank rank) {
      return rank.ordinal() >= 1 || rank.permissionLevel >= 2;
   }

   public static int getMaxHomes(Rank rank) {
      return rank.maxHomes >= 50 ? 99999 : rank.maxHomes;
   }

   public static int getPermissionLevel(Rank rank) {
      return rank.permissionLevel;
   }

   public static boolean hasPermission(PlayerEntity player, int perm) {
      return mpe.INSTANCE.getRankManager().get(player.getUuidAsString()).permissionLevel >= perm;
   }

   public static boolean canBuild(PlayerEntity player) {
      Rank rank = mpe.INSTANCE.getRankManager().get(player.getUuidAsString());
      return rank == BUILDER || rank.permissionLevel >= 3;
      //  return rank == MODERATOR || rank.permissionLevel >= 4;

   }
}
