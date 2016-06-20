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

        return true;
    }
}
