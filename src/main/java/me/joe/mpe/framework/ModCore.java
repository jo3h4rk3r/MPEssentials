package me.joe.mpe.framework;

import me.joe.mpe.impl.commands.PrivateWarps.PWarpCommand;
import me.joe.mpe.impl.commands.admin.*;
import me.joe.mpe.impl.commands.back.BackCommand;
import me.joe.mpe.impl.commands.back.StuckCommand;
import me.joe.mpe.impl.commands.homes.DelHomeCommand;
import me.joe.mpe.impl.commands.homes.HomeCommand;
import me.joe.mpe.impl.commands.homes.HomesCommand;
import me.joe.mpe.impl.commands.homes.SetHomeCommand;
import me.joe.mpe.impl.commands.info.*;
import me.joe.mpe.impl.commands.misc.*;
import me.joe.mpe.impl.commands.spawn.SetSpawnCommand;
import me.joe.mpe.impl.commands.spawn.SpawnCommand;
import me.joe.mpe.impl.commands.tpa.TpaCommand;
import me.joe.mpe.impl.commands.tpa.TpaIgnore;
import me.joe.mpe.impl.commands.warps.DelWarpCommand;
import me.joe.mpe.impl.commands.warps.SetWarpCommand;
import me.joe.mpe.impl.commands.warps.WarpCommand;
import me.joe.mpe.impl.commands.warps.WarpsCommand;
import me.joe.mpe.impl.commands.zones.DelZoneCommand;
import me.joe.mpe.impl.commands.zones.SetZoneCommand;
import me.joe.mpe.impl.commands.zones.ZonesCommand;
import me.joe.mpe.impl.config.Config;
import me.joe.mpe.impl.config.Data;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

import java.io.IOException;

public class ModCore implements DedicatedServerModInitializer {



   public static Data tpData = new Data();
   public static Config settings = new Config();
   @Override
   public void onInitializeServer() {
      System.out.println("MPEssentials Init");


      try {
         tpData.load();
         settings.load();
      } catch (IOException e) {
         e.printStackTrace();
      }

      CommandRegistrationCallback.EVENT.register(WildCommand::register);
      //CommandRegistrationCallback.EVENT.register(FreezeCommand::register);
      //CommandRegistrationCallback.EVENT.register(TestTPCommand::register);
      CommandRegistrationCallback.EVENT.register(PlayerTagCommand::register);
      CommandRegistrationCallback.EVENT.register(FeedCommand::register);
      CommandRegistrationCallback.EVENT.register(BackCommand::register);
      CommandRegistrationCallback.EVENT.register(MsgIgnoreCommand::register);
      CommandRegistrationCallback.EVENT.register(PWarpCommand::register);
      CommandRegistrationCallback.EVENT.register(StuckCommand::register);
      CommandRegistrationCallback.EVENT.register(TimeCommand::register);
      CommandRegistrationCallback.EVENT.register(DelHomeCommand::register);
      CommandRegistrationCallback.EVENT.register(HomeCommand::register);
      //CommandRegistrationCallback.EVENT.register(CheckClientCommand::register);
      CommandRegistrationCallback.EVENT.register(HomesCommand::register);
      //CommandRegistrationCallback.EVENT.register(PvPSwitchCommand::register);
      CommandRegistrationCallback.EVENT.register(SuicideCommand::register);
      CommandRegistrationCallback.EVENT.register(SetHomeCommand::register);
      CommandRegistrationCallback.EVENT.register(DiscordCommand::register);
      CommandRegistrationCallback.EVENT.register(DonateCommand::register);
      CommandRegistrationCallback.EVENT.register(FormatCommand::register);
      CommandRegistrationCallback.EVENT.register(HelpCommand::register);
      CommandRegistrationCallback.EVENT.register(InfoCommand::register);
      CommandRegistrationCallback.EVENT.register(MsgCommand::register);
      CommandRegistrationCallback.EVENT.register(TradeCommand::register);
      CommandRegistrationCallback.EVENT.register(PingCommand::register);
      CommandRegistrationCallback.EVENT.register(PosCommand::register);
      CommandRegistrationCallback.EVENT.register(RanksCommand::register);
      CommandRegistrationCallback.EVENT.register(RulesCommand::register);
      CommandRegistrationCallback.EVENT.register(TpsCommand::register);
      CommandRegistrationCallback.EVENT.register(VoteCommand::register);
      CommandRegistrationCallback.EVENT.register(AnvilCommand::register);
      CommandRegistrationCallback.EVENT.register(HomesCommand::register);
      CommandRegistrationCallback.EVENT.register(BroadcastCommand::register);
      CommandRegistrationCallback.EVENT.register(ClearChatCommands::register);
      CommandRegistrationCallback.EVENT.register(EChestCommand::register);
      CommandRegistrationCallback.EVENT.register(FlyCommand::register);
      CommandRegistrationCallback.EVENT.register(GamemodeCommand::register);
      CommandRegistrationCallback.EVENT.register(GodCommand::register);
      CommandRegistrationCallback.EVENT.register(HatCommand::register);
      CommandRegistrationCallback.EVENT.register(TpaIgnore::register);
      CommandRegistrationCallback.EVENT.register(HealCommand::register);
      CommandRegistrationCallback.EVENT.register(GlowCommand::register);
      CommandRegistrationCallback.EVENT.register(IgnoreCommand::register);
      CommandRegistrationCallback.EVENT.register(InvseeCommand::register);
      CommandRegistrationCallback.EVENT.register(KickCommand::register);
      CommandRegistrationCallback.EVENT.register(MutePlayerCommand::register);
      CommandRegistrationCallback.EVENT.register(NickCommand::register);
      CommandRegistrationCallback.EVENT.register(ParticlesCommand::register);
      CommandRegistrationCallback.EVENT.register(PlayerNickCommand::register);
      CommandRegistrationCallback.EVENT.register(PosHUDCommand::register);
      CommandRegistrationCallback.EVENT.register(RankCommand::register);
      CommandRegistrationCallback.EVENT.register(ReportCommand::register);
      CommandRegistrationCallback.EVENT.register(RTPCommand::register);
      CommandRegistrationCallback.EVENT.register(SeenCommand::register);
      CommandRegistrationCallback.EVENT.register(UnNickCommand::register);
      CommandRegistrationCallback.EVENT.register(VanishCommand::register);
      CommandRegistrationCallback.EVENT.register(WeatherCommand::register);
      CommandRegistrationCallback.EVENT.register(WildCommand::register);
      CommandRegistrationCallback.EVENT.register(SetSpawnCommand::register);
      CommandRegistrationCallback.EVENT.register(testCommand::register);
      CommandRegistrationCallback.EVENT.register(SpawnCommand::register);
      CommandRegistrationCallback.EVENT.register(TpaCommand::register);
      CommandRegistrationCallback.EVENT.register(CraftCommand::register);
      CommandRegistrationCallback.EVENT.register(pWeatherCommand::register);
      CommandRegistrationCallback.EVENT.register(VersionCommand::register);
      CommandRegistrationCallback.EVENT.register(DelWarpCommand::register);
      CommandRegistrationCallback.EVENT.register(BMeCommand::register);
      CommandRegistrationCallback.EVENT.register(SetWarpCommand::register);
      CommandRegistrationCallback.EVENT.register(WarpCommand::register);
      CommandRegistrationCallback.EVENT.register(WarpsCommand::register);
      CommandRegistrationCallback.EVENT.register(DelZoneCommand::register);
      CommandRegistrationCallback.EVENT.register(SetZoneCommand::register);
      CommandRegistrationCallback.EVENT.register(ZonesCommand::register);
      CommandRegistrationCallback.EVENT.register(AFKCommand::register);
      CommandRegistrationCallback.EVENT.register(BansCommand::register);
      //CommandRegistrationCallback.EVENT.register(UnbanCommand::register);
      //CommandRegistrationCallback.EVENT.register(EventCommand::register);
      CommandRegistrationCallback.EVENT.register(JailCommand::register);
      CommandRegistrationCallback.EVENT.register(CrashCommand::register);

   }
}
