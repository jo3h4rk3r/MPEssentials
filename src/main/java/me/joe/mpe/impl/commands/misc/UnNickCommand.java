package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.managers.RankManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class UnNickCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("unnick").executes((context) -> {
            RankManager rankManager = mpe.INSTANCE.getRankManager();
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            String uuid = String.valueOf(playerEntity.getGameProfile().getId());
            if (Rank.hasNick(rankManager.get(uuid))) {
               mpe.INSTANCE.getNickManager().remove(uuid);
               playerEntity.sendMessage(new LiteralText("§eRemoved your nickname"), false);
            } else {
               playerEntity.sendMessage(new LiteralText("§4You do not have access to this command."), false);
            }

            mpe.INSTANCE.getNickManager().save();
            return 1;
         }));
   }
}
