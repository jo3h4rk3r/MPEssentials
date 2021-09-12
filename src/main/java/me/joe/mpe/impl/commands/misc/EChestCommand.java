package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.mpe.api.Command;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class EChestCommand {

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
      EChestCommand.build(dispatcher);
   }

   public static void build(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register((CommandManager.literal("enderchest").executes((context) -> EChestCommand.execute(context, context.getSource()))));

      dispatcher.register(CommandManager.literal("ec").executes((context) -> EChestCommand.execute(context, context.getSource())));
      dispatcher.register(CommandManager.literal("echest").executes((context) -> EChestCommand.execute(context, context.getSource())));
      dispatcher.register(CommandManager.literal("enderchest").executes((context) -> EChestCommand.execute(context, context.getSource())));
   }

   private static int execute(CommandContext<ServerCommandSource> context, ServerCommandSource source) throws CommandSyntaxException {
      LiteralText literalText = new LiteralText("");
      ServerPlayerEntity target = source.getPlayer();
      ServerPlayerEntity sender = context.getSource().getPlayer();
      Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(sender.getGameProfile().getId()));
      if (Rank.hasEnderchest(rank)) {
         literalText.append(new LiteralText("You have opened ")).setStyle((Style.EMPTY).withColor(Formatting.YELLOW));
         literalText.append((new LiteralText(target.getName().getString())).setStyle((Style.EMPTY).withColor(Formatting.AQUA).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ENTITY, HoverEvent.EntityContent.parse( new LiteralText(target.getName().getString()))))));
         literalText.append(new LiteralText("'s Ender chest")).setStyle((Style.EMPTY).withColor(Formatting.YELLOW));
         sender.sendMessage(literalText, false);
         EnderChestInventory enderChestInventory = target.getEnderChestInventory();
         sender.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, pInv, pEntity) ->
                 GenericContainerScreenHandler.createGeneric9x3(i, pInv, enderChestInventory), new TranslatableText("container.enderchest"))
         );
      } else {
         sender.sendMessage(new LiteralText("§4You do not have access to this command"), false);
         sender.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
      }

      return 1;
   }
}
