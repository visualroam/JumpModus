package me.dominik.Jumper.commands;

import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.PlayerDatabaseManager;
import me.dominik.Jumper.manager.StatsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 0){
                StatsManager statsManager = new StatsManager();

                p.sendMessage(Jumper.getPREFIX() + " Deine Stats sind:");
                p.sendMessage("");
                p.sendMessage("   §cGamesPlayed: " + statsManager.getGamePlayed(p.getUniqueId().toString()));
                p.sendMessage("   §6Kills: " + statsManager.getKills(p.getUniqueId().toString()));
                p.sendMessage("   §bDeaths: " + statsManager.getDeaths(p.getUniqueId().toString()));
                p.sendMessage("   §8Fails: " + statsManager.getFails(p.getUniqueId().toString()));
                p.sendMessage("");
                return true;
            } else if (args.length == 1){
                if(args[0].equalsIgnoreCase("reset")){
                    StatsManager statsManager = new StatsManager();
                    statsManager.resetStats(p.getUniqueId().toString());
                    return true;
                }
                PlayerDatabaseManager playerDatabaseManager = new PlayerDatabaseManager();
                String uuid = playerDatabaseManager.getPlayerUUID(args[0]);
                StatsManager statsManager = new StatsManager();

                p.sendMessage(Jumper.getPREFIX() + " Die Stats von  " + args[0] + "sind:");
                p.sendMessage("");
                p.sendMessage("   §4Ranking: " + statsManager.getOrderWithString("Kills", statsManager.getSize("Stats")).get(uuid) + " Platz.");
                p.sendMessage("   §cGamesPlayed: " + statsManager.getGamePlayed(uuid));
                p.sendMessage("   §6Kills: " + statsManager.getKills(uuid));
                p.sendMessage("   §bDeaths: " + statsManager.getDeaths(uuid));
                p.sendMessage("   §8Fails: " + statsManager.getFails(uuid));
                p.sendMessage("");
                return true;
            }

        } else {
            sender.sendMessage(Jumper.getPREFIX() + " U NO SPIELER");
        }

        return false;
    }
}
