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
        if(strings.length == 0){
            sender.sendMessage(Jumper.getPREFIX() + "Nur wegen Summer muss ich dir jetzt sagen das du zu dumm bist eine Zahl von 10 oder größer einzugeben. Mit freundlichen Grüßen Das Plugin");
            return true;
        }
        try{
            if(Integer.parseInt(strings[0]) < 10){
                sender.sendMessage(Jumper.getPREFIX() + " Bitte eine Zahl größer 10 eingeben.");
                return true;
            }
        } catch (NumberFormatException e){
            sender.sendMessage(Jumper.getPREFIX() + "Nur wegen Summer muss ich dir jetzt sagen das du zu dumm bist eine Zahl von 10 oder größer einzugeben. Mit freundlichen Grüßen Das Plugin");
        }
        Countdowns.getLobbyCountdown().setFrom(Integer.parseInt(strings[0]));
        Bukkit.broadcastMessage(Jumper.getPREFIX() + " Das Spiel wurder vorverlegt!");

        return true;
    }
}
