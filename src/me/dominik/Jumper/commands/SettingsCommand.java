package me.dominik.Jumper.commands;

import me.dominik.Jumper.listener.player.PlayerDropItemListener;
import me.dominik.Jumper.manager.AreaManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SettingsCommand implements CommandExecutor {

    public HashMap<Integer, Location> spawns = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {


        Player p = (Player) commandSender;

        int i = 1;

        Location loc1 = p.getLocation();
        Location loc2 = new Location(p.getWorld(),loc1.getX() + 10, loc1.getY() + 10, loc1.getZ() + 10);

        World w = loc1.getWorld();

        int minX = loc1.getBlockX();
        int minY = loc1.getBlockY();
        int minZ = loc1.getBlockZ();

        int maxX = loc2.getBlockX();
        int maxY = loc2.getBlockY();
        int maxZ = loc2.getBlockZ();
        for(int x = minX; x <= maxX; x++){
            System.out.println(x);
            for(int y = minY; y <= maxY; y++){
                System.out.println(y);
                for(int z = minZ; z <= maxZ; z++){
                    System.out.println(z);
                    Location spawn = new Location(w,x,y,z);
                    Block b = w.getBlockAt(spawn);
                    if(b.getType().equals(Material.CHEST)){
                        spawns.put(i, spawn);
                        System.out.println("FOUND!");
                        i++;
                    }
                }
            }
        }
        p.teleport(spawns.get(i-1));



        return true;
    }
}
