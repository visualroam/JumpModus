package me.dominik.Jumper.commands;

import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.ChekpointManager;
import me.dominik.Jumper.manager.StatsManager;
import me.dominik.Jumper.manager.StatsWall;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    int id = 0;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage("UPDATED");
        try {
            id = Integer.parseInt(strings[0]);
        } catch (NumberFormatException e){
            commandSender.sendMessage(Jumper.getPREFIX() + "Bitte eine Zahl eingeben!");
        }
        StatsWall statsWall = new StatsWall();
        statsWall.updateOne(id);
        return true;
    }
}
