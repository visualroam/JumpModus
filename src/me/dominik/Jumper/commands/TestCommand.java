package me.dominik.Jumper.commands;

import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.ChekpointManager;
import me.dominik.Jumper.manager.StatsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage("UPDATED");
        StatsManager statsManager = new StatsManager();
        return true;
    }
}
