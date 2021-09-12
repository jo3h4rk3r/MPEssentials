package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.mpe;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.screen.AnvilScreenHandler;
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

public class OldAnvilCommand {


   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
      OldAnvilCommand.build(dispatcher);
   }

   public static void build(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = (CommandManager.literal("anvil").then(CommandManager.argument("target", EntityArgumentType.player()).executes((context) -> OldAnvilCommand.execute(context, EntityArgumentType.getPlayer(context, "target").getCommandSource(), true)))).executes((context) -> OldAnvilCommand.execute(context, context.getSource(), false));
      dispatcher.register(literalArgumentBuilder);
      dispatcher.register(CommandManager.literal("anvil").executes((context) -> OldAnvilCommand.execute(context, context.getSource(), false)));
   }

   private static int execute(CommandContext<ServerCommandSource> context, ServerCommandSource source, boolean sendFeedback) throws CommandSyntaxException {
      LiteralText literalText = new LiteralText("NOTE! MUST TAKE ITEMS OUT BEFORE CLOSING GUI. IT WILL EAT YOUR ITEMS IF NOT.");
      ServerPlayerEntity target = source.getPlayer();
      ServerPlayerEntity sender = context.getSource().getPlayer();
      Rank rank = mpe.INSTANCE.getRankManager().get(String.valueOf(sender.getGameProfile().getId()));
      if (Rank.hasAnvil(rank)) {
         literalText.append(new LiteralText("You have opened the Anvil for ")).setStyle((Style.EMPTY).withColor(Formatting.YELLOW));
         literalText.append((new LiteralText(target.getName().getString())).setStyle((Style.EMPTY).withColor(Formatting.AQUA).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ENTITY, HoverEvent.EntityContent.parse(new LiteralText(target.getName().getString()))))));
         if (sendFeedback) {
            sender.sendMessage(literalText, false);
         }

         target.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, inv, pEntity) -> {
            return new AnvilScreenHandler(i, inv);
         }, new TranslatableText("container.repair")));
      } else {
         sender.sendMessage(new LiteralText("§4You do not have access to this command."), false);
         sender.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
      }

      return 1;
   }
}
