package me.joe.mpe.impl.utilities.files;

import com.mojang.authlib.GameProfile;
import me.joe.mpe.framework.mixin.ServerPlayerEntityMixin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Shadow;

public class PlayerCounter {
    private static ServerPlayerEntityMixin player;
    private static int playersOnline = getPlayersOnline();
    //private static int playersPing = getPlayersPing();
    public static void onTickPlayer(MinecraftServer minecraftServer){
        playersOnline = (int) minecraftServer.getCurrentPlayerCount();
    }
    public static int getPlayersOnline(){
        return playersOnline;
    }
  //  public static int getPlayersPing(){
     //   return playersPing;
   // }
}
