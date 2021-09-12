package me.joe.mpe.impl.commands.admin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.mpe.api.Rank;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;

import static net.minecraft.command.argument.EntityArgumentType.getPlayer;
import static net.minecraft.server.command.CommandManager.argument;

public class InvseeCommand {


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,  boolean b) {

        //CommandRegistry.INSTANCE.register(true, (CommandDispatcher<ServerCommandSource> dispatcher) -> {

        dispatcher.register(CommandManager.literal("invsee")


                .then(argument("player", EntityArgumentType.player())
                        .executes(InvseeCommand::invSee)));


        dispatcher.register(CommandManager.literal("endersee")
                .then(argument("player", EntityArgumentType.player())
                        .executes(InvseeCommand::enderSee)));

        }




    private static int invSee(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity playerEntity = ctx.getSource().getPlayer();
            ServerPlayerEntity target = (getPlayer(ctx, "player"));


        if (Rank.hasPermission(playerEntity, 2)) {

            ctx.getSource().getPlayer().openHandledScreen(invSee(target, ctx.getSource().getPlayer()));

        } else {
            playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
            playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
        }


        return 1;
    }
    private static int enderSee(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity playerEntity = ctx.getSource().getPlayer();
        ServerPlayerEntity target = (getPlayer(ctx, "player"));
        if (Rank.hasPermission(playerEntity, 2)) {
        ctx.getSource().getPlayer().openHandledScreen(enderSee(target, ctx.getSource().getPlayer()));
        } else {
            playerEntity.sendMessage(new LiteralText("§4You do not have access to that command."), false);
            playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1f, 0f);
        }
        return 1;

    }

    private static NamedScreenHandlerFactory enderSee(ServerPlayerEntity target, ServerPlayerEntity user) {
        return new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return new LiteralText("Ender Chest");
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player1) {
                return GenericContainerScreenHandler.createGeneric9x3(syncId, user.getInventory(), target.getEnderChestInventory());
            }
        };
    }
    private static NamedScreenHandlerFactory invSee(ServerPlayerEntity target, ServerPlayerEntity user) {
        NamedScreenHandlerFactory factory = new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return new LiteralText("Player Inventory");
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player1) {
                ScreenHandler handler = GenericContainerScreenHandler.createGeneric9x5(syncId, user.getInventory());
                handler.addListener(new ScreenHandlerListener() {

                    @Override
                    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
                        copySlotsFromInventory(target, handler, slotId);
                    }

                    @Override
                    public void onPropertyUpdate(ScreenHandler handler, int propertyId, int value) {

                    }
                });
                return handler;
            }
        };
        return factory;
    }

   /* private static void setSlotsInit(ServerPlayerEntity target, ScreenHandler handler){
        for(int i = 0; i < 36; i++){
            handler.setStackInSlot(i, target.getInventory().main.get(i));
        }
        for(int i = 0; i < 4; i++){
            handler.setStackInSlot(i + 36, target.getInventory().armor.get(i));
        }
        handler.setStackInSlot(44, target.getInventory().offHand.get(0));
    }

    */

    private static void copySlotsFromInventory(ServerPlayerEntity target, ScreenHandler handler, int slotID){
        if(slotID < 36){
            target.getInventory().main.set(slotID, handler.getStacks().get(slotID));
        } else if(slotID < 40){
            target.getInventory().armor.set(slotID - 36, handler.getStacks().get(slotID));
        } else if(slotID == 44){
            target.getInventory().offHand.set(0, handler.getStacks().get(slotID));
        }
    }
}