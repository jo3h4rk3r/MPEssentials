package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.managers.RankManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;

public class NickCommand{

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
         dispatcher.register(CommandManager.literal("nick")
                 .then(CommandManager.argument("name", greedyString())
                         .executes((context) -> {
            RankManager rankManager = mpe.INSTANCE.getRankManager();
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            String argName = StringArgumentType.getString(context, "name");
            String uuid = String.valueOf(playerEntity.getGameProfile().getId());
            if (Rank.hasNick(rankManager.get(uuid))) {
               if (argName.length() < 75) {


                  mpe.INSTANCE.getNickManager().add(uuid, argName);
                  playerEntity.sendMessage(new LiteralText("§eChanged your nickname to §7" + argName), false);
               } else {
                  playerEntity.sendMessage(new LiteralText("§cNickname must be under 75 characters!"), false);
               }
            } else {
               playerEntity.sendMessage(new LiteralText("§4You do not have access to this command."), false);
               playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
            }

            mpe.INSTANCE.getNickManager().save();
            return 1;
         })));
   }
}
