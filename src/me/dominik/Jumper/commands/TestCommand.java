package me.dominik.Jumper.commands;

import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.AchievementManager;
import me.dominik.Jumper.manager.ChekpointManager;
import me.dominik.Jumper.manager.StatsManager;
import me.dominik.Jumper.manager.StatsWall;
import me.dominik.Jumper.methoden.Achievement;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TestCommand implements CommandExecutor {
    int id = 0;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        AchievementManager achievementManager = Jumper.getInstance().getAchievementManager();
        p.sendMessage(achievementManager.StringToList(achievementManager.getDataBaseString(p.getUniqueId().toString())).get("Der Teufel").toString());
        return true;
    }
}
