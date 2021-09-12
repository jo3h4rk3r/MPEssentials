package me.joe.mpe.impl.commands.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.joe.mpe.api.Rank;
import me.joe.mpe.impl.managers.EventsManager;
import me.joe.mpe.impl.mpe;
import me.joe.mpe.impl.utilities.CfgFile;
import me.joe.mpe.impl.utilities.FileDirectory;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.*;

public class EventCommand {

    public static CfgFile eventStartFile;
    public static CfgFile newEventFile;

    static {
        eventStartFile = new CfgFile("event_start", new FileDirectory("core"));
    }
    static {
        newEventFile = new CfgFile("events", new FileDirectory("core"));
    }




    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("event").then(CommandManager.literal("join").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (newEventFile.getFile().exists() && newEventFile.read().size() > 0) {



                EventCommand.executeJoinEvent(context, context.getSource(), false);


            } else {
                playerEntity.sendMessage(new LiteralText("§aThere is no current events."), false);
            }

            return 0;
        })));
        dispatcher.register(CommandManager.literal("event").then(CommandManager.literal("leave").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();




                EventCommand.executeLeaveEvent(context, context.getSource(), false);




            return 0;
        })));
        dispatcher.register(CommandManager.literal("event").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            playerEntity.sendMessage(new LiteralText("§aEvent help page:"), false);
            playerEntity.sendMessage(new LiteralText(""), false);
            playerEntity.sendMessage(new LiteralText("§9/events - list current events"), false);
            playerEntity.sendMessage(new LiteralText("§9/event join 'event' - Join a event"), false);
            playerEntity.sendMessage(new LiteralText(""), false);
            return 0;
        }));
        dispatcher.register(CommandManager.literal("events").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            playerEntity.sendMessage(new LiteralText("§aCurrent events:"), false);
            playerEntity.sendMessage(new LiteralText(""), false);
            playerEntity.sendMessage(new LiteralText("§9(1) Base Build Battle! - Starts 7/01/2020 - Ends 8/01/2020"), false);
            playerEntity.sendMessage(new LiteralText("§9(2) Impossible Maze! - Starts 7/18/2020 - Ends 7/18/2020"), false);
            playerEntity.sendMessage(new LiteralText(""), false);
            playerEntity.sendMessage(new LiteralText("§3Join our discord and check out the events channel for more info."), false);
            return 0;
        }));
        dispatcher.register(CommandManager.literal("event").then(CommandManager.literal("create").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();

            if (Rank.hasPermission(playerEntity, 2)) {

               // if (!newEventFile.getFile().exists() && newEventFile.read().size() > 0) {
                    Text text = new LiteralText("§8§l[§5§lEVENT§8§l]§a New event created!");
                    context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, playerEntity.getUuid());



                    List<String> lines = new ArrayList<>();
                    lines.add(String.format("%s", playerEntity));
                    EventCommand.newEventFile.write(lines);



                }
              //  else {
               //     playerEntity.sendMessage(new LiteralText("§cYou've already created that event"), false);
                //}

             else {
                playerEntity.sendMessage(new LiteralText("§cYou do not have access to this command"), false);
            }

            return 0;
        })));
        dispatcher.register(CommandManager.literal("event").then(CommandManager.literal("remove").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 2)) {
                if (newEventFile.getFile().exists() && newEventFile.read().size() > 0) {
                    Text text = new LiteralText("§8§l[§5§lEVENT§8§l]§a Removed event!");
                    context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, playerEntity.getUuid());
                    context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "execute as @a run event leave");

                    List<String> lines = new ArrayList<>();
                    lines.remove(String.format("%s", playerEntity));
                    EventCommand.newEventFile.write(lines);


                } else {
                    playerEntity.sendMessage(new LiteralText("§cThere is no event to remove!"), false);
                }
            }else {
                playerEntity.sendMessage(new LiteralText("§cYou do not have access to this command!"), false);

                }



            return 0;
        })));
        dispatcher.register(CommandManager.literal("event").then(CommandManager.literal("start").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();
            if (Rank.hasPermission(playerEntity, 2)) {
                if (newEventFile.getFile().exists() && newEventFile.read().size() > 0) {
                  //  PlayerTickListener.teleportCooldowns.put(players, (new Date()).getTime());
                    Text text = new LiteralText("§8§l[§5§lEVENT§8§l]§a Starting in 30 seconds!");
                   // Text text = new LiteralText("§8§l[§5§lEVENT§8§l]§a The event has started!");
                    context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, playerEntity.getUuid());
                    List<String> lines = new ArrayList<>();
                    lines.add(String.format("%s", playerEntity));
                    EventCommand.eventStartFile.write(lines);




                } else {
                    playerEntity.sendMessage(new LiteralText("§cThere is no event to start!"), false);
                }
            }else {
                playerEntity.sendMessage(new LiteralText("§cYou do not have access to this command!"), false);

                }


            return 0;
        })));
        dispatcher.register(CommandManager.literal("event").then(CommandManager.literal("end").executes((context) -> {
            ServerPlayerEntity playerEntity = context.getSource().getPlayer();

            if (Rank.hasPermission(playerEntity, 2)) {
                if (mpe.INSTANCE.getEventsManager().has(String.valueOf(playerEntity.getGameProfile().getName()))) {
                    Text text = new LiteralText("§8§l[§5§lEVENT§8§l]§a Ended..");
                    context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, playerEntity.getUuid());


                    List<String> lines = new ArrayList<>();
                    lines.remove(String.format("%s", playerEntity));
                    EventCommand.eventStartFile.write(lines);
                    EventCommand.newEventFile.write(lines);

                    context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "execute as @a run event leave");


                } else {
                    playerEntity.sendMessage(new LiteralText("§cThere is no event to end!"), false);
                }
            }else{
                playerEntity.sendMessage(new LiteralText("§cYou do not have access to this command!"), false);
                }


            return 0;
        })));



    }



    private static int executeJoinEvent(CommandContext<ServerCommandSource> context, ServerCommandSource source, boolean sendFeedback) throws
            CommandSyntaxException {
        ServerPlayerEntity playerEntity = context.getSource().getPlayer();
        if (!mpe.INSTANCE.getEventsManager().has(String.valueOf(playerEntity.getGameProfile().getName()))) {
            mpe.INSTANCE.getEventsManager().add(String.valueOf(playerEntity.getGameProfile().getName()));
            String displayName = playerEntity.getGameProfile().getName();
            String name = String.valueOf(playerEntity.getGameProfile().getId());
            if (mpe.INSTANCE.getNickManager().has(name)) {
                displayName = "" + mpe.INSTANCE.getNickManager().get(name);
            }
            Text text = new LiteralText("§8§l[§5§lEVENT§8§l]§f " + displayName + " §ahas joined the event!");
            context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, playerEntity.getUuid());
            EventsManager eventsManager = mpe.INSTANCE.getEventsManager();


            mpe.INSTANCE.getEventsManager().save();
            

        } else {
            playerEntity.sendMessage(new LiteralText(String.format("§cYou've already joined this event!")), false);
        }


        return 1;
    }
    
    private static int executeLeaveEvent(CommandContext<ServerCommandSource> context, ServerCommandSource source, boolean sendFeedback) throws
            CommandSyntaxException {
        ServerPlayerEntity playerEntity = context.getSource().getPlayer();
        if (mpe.INSTANCE.getEventsManager().has(String.valueOf(playerEntity.getGameProfile().getName()))) {
            mpe.INSTANCE.getEventsManager().remove(String.valueOf(playerEntity.getGameProfile().getName()));
            String displayName = playerEntity.getGameProfile().getName();
            String name = String.valueOf(playerEntity.getGameProfile().getId());
            if (mpe.INSTANCE.getNickManager().has(name)) {
                displayName = "" + mpe.INSTANCE.getNickManager().get(name);
            }
            mpe.INSTANCE.getEventsManager().save();
            Text text = new LiteralText("§8§l[§5§lEVENT§8§l]§f " + displayName + " §ahas left the event!");
            context.getSource().getServer().getPlayerManager().broadcastChatMessage(text, MessageType.CHAT, playerEntity.getUuid());
            EventsManager eventsManager = mpe.INSTANCE.getEventsManager();

            playerEntity.teleport(-193,72, -293);
            context.getSource().getServer().getCommandManager().execute(playerEntity.getCommandSource().withMaxLevel(4).withSilent(), "spawn" + name);


        } else {
            playerEntity.sendMessage(new LiteralText(String.format("§cYou're not in a event!")), false);
        }

        return 1;
    }

}



