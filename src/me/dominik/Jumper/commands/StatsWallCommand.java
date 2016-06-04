package me.dominik.Jumper.commands;

import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.AreaManager;
import me.dominik.Jumper.manager.RegionManager;
import me.dominik.Jumper.manager.StatsWall;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;


public class StatsWallCommand implements CommandExecutor {

    private HashMap<String, Location> loc1 = new HashMap<>();
    private HashMap<String, Location> loc2 = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            Location location = p.getLocation();
            if(args.length == 0 || args.length == 1){
                p.sendMessage(Jumper.getPREFIX() + " Bitte mehr Argumente eingeben");
                return true;
            }
            if(args.length >= 2){
                if(args[0].equalsIgnoreCase("setpoint")){
                    if(args[1].equalsIgnoreCase("1")){

                        loc1.put(p.getName(),location);
                        p.sendMessage(Jumper.getPREFIX() + "Punkt 1 gesetzt.");
                        return true;
                    } else if(args[1].equalsIgnoreCase("2")){
                        loc2.put(p.getName(), location);
                        p.sendMessage(Jumper.getPREFIX() + "Punkt 2 gesetzt");
                        return true;
                    }
                    p.sendMessage(Jumper.getPREFIX() + "Bitte /setarea setpoint <1/2> eingeben");
                    return true;
                }
                if(args[0].equalsIgnoreCase("create")){
                    Location locs1 = loc1.get(p.getName());
                    Location locs2 = loc2.get(p.getName());
                    if(locs1 == null || locs2 == null) {
                        p.sendMessage(" Setzte zuerst 2 Punkte.");
                        return true;
                    }
                    if(locs1.getWorld() != locs2.getWorld()){
                        p.sendMessage("Setzte zuerst 2 Punkte");
                        return true;
                    }
                    try {
                        int anzahl = Integer.parseInt(args[1]);
                        String grund = args[2];
                        StatsWall statsWall = new StatsWall();
                        RegionManager regionManager = new RegionManager(locs1,locs2);
                        statsWall.addStatsWall(regionManager.getStatsWallLocation(anzahl),anzahl,grund);
                        return true;
                    } catch (NumberFormatException e){
                        p.sendMessage(Jumper.getPREFIX() +" Bitte eine Zahl eingeben.");
                        return true;
                    }
                }
            }

        }
        return false;
    }
}
