package me.dominik.Jumper.commands;

import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.EnderChestItemManager;
import me.dominik.Jumper.manager.RegionManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.jar.JarEntry;

public class ChestCommand implements CommandExecutor {

    private HashMap<String, Location> loc1 = new HashMap<>();
    private HashMap<String, Location> loc2 = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            Location location = p.getLocation();
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
            }
            if(args[0].equalsIgnoreCase("get")){
                EnderChestItemManager enderChestItemManager = new EnderChestItemManager();
                RegionManager regionManager = new RegionManager(loc1.get(p.getName()),loc2.get(p.getName()));
                enderChestItemManager.createItemList(regionManager);
                p.sendMessage(Jumper.getPREFIX() + " Insgesamt wurden " + enderChestItemManager.getAnzahlEingelesenenKisten() + " Kisten eingelesen");
                return true;
            }
        }
        return false;
    }
}
