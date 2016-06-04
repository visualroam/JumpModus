package me.dominik.Jumper.commands;

import com.google.common.base.Joiner;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.AreaManager;
import me.dominik.Jumper.manager.RegionManager;
import me.dominik.Jumper.manager.StatsManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;

public class ArenaCreateCommand implements CommandExecutor{

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
                        p.sendMessage("Punkt 1 gesetzt.");
                        return true;
                    } else if(args[1].equalsIgnoreCase("2")){
                        loc2.put(p.getName(), location);
                        p.sendMessage("Punkt 2 gesetzt");
                        return true;
                    }
                    p.sendMessage("Bitte /setarea setpoint <1/2> eingeben");
                    return true;
                }
                if(args[0].equalsIgnoreCase("create")){
                    String name = args[1];
                    String author = Joiner.on(' ' ).join(Arrays.copyOfRange(args, 2, args.length));
                    Location locs1 = loc1.get(p.getName());
                    Location locs2 = loc2.get(p.getName());
                    AreaManager arenas = new AreaManager();
                    if(locs1 == null || locs2 == null) {
                        p.sendMessage(" Setzte zuerst 2 Punkte.");
                        return true;
                    }
                    if(locs1.getWorld() != locs2.getWorld()){
                        p.sendMessage("Setzte zuerst 2 Punkte");
                        return true;
                    }
                    RegionManager regionManager = new RegionManager(locs1, locs2);
                    AreaManager arena = new AreaManager(name,author,regionManager);
                    arena.createArena(name,author,regionManager.getSpawns(),regionManager.getFinish());
                    p.sendMessage("");
                    p.sendMessage(Jumper.getPREFIX() + " Name: " + name);
                    p.sendMessage(Jumper.getPREFIX() + " Author: " + author);
                    p.sendMessage("");
                }
                if(args[0].equalsIgnoreCase("deathmatch")){
                    String name = args[1];
                    String author = Joiner.on(' ' ).join(Arrays.copyOfRange(args, 2, args.length));
                    Location locs1 = loc1.get(p.getName());
                    Location locs2 = loc2.get(p.getName());
                    AreaManager arenas = new AreaManager();
                    if(locs1 == null || locs2 == null) {
                        p.sendMessage(" Setzte zuerst 2 Punkte.");
                        return true;
                    }
                    if(locs1.getWorld() != locs2.getWorld()){
                        p.sendMessage("Setzte zuerst 2 Punkte");
                        return true;
                    }
                    RegionManager regionManager = new RegionManager(locs1, locs2);
                    AreaManager arena = new AreaManager();
                    arena.createDeathmatchArena(name,author,regionManager.getDeathMatchSpawns(),regionManager.getSpectatorSpawnLocation(false));
                    p.sendMessage("");
                    p.sendMessage(Jumper.getPREFIX() + " Name: " + name);
                    p.sendMessage(Jumper.getPREFIX() + " Author: " + author);
                    p.sendMessage("");
                    return true;
                }
                if(args[0].equalsIgnoreCase("Stats")){
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
                    RegionManager regionManagers = new RegionManager(locs1, locs2);
                    StatsManager statsManager = new StatsManager();
                    p.sendMessage("");
                    p.sendMessage("Stats Wall wurde erstellt");
                    p.sendMessage("");
                    return true;
                }
            }

        }
        return false;
    }
}
