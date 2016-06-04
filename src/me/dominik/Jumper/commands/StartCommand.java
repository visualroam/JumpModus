package me.dominik.Jumper.commands;

import me.dominik.Jumper.Countdowns;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.methoden.CountdownEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(Integer.parseInt(strings[0]) < 10){
            sender.sendMessage(Jumper.getPREFIX() + " Bitte eine Zahl größer 10 eingeben.");
            return true;
        }
        Countdowns.getLobbyCountdown().setFrom(Integer.parseInt(strings[0]));
        Bukkit.broadcastMessage(Jumper.getPREFIX() + " Das Spiel wurder vorverlegt!");

        return true;
    }
}
