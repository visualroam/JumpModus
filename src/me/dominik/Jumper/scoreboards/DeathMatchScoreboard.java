package me.dominik.Jumper.scoreboards;

import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.DeathmatchManager;
import me.dominik.Jumper.methoden.Formatting;
import org.bukkit.Bukkit;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;


public class DeathMatchScoreboard {

    private DeathmatchManager deathmatchManager;
    private Scoreboard deathMatchScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private Objective objective = deathMatchScoreboard.registerNewObjective("1","2");

    public static int scoreboardTimer = Jumper.getInstance().getSettings().getDeathMatchCountdown();

    public DeathMatchScoreboard(DeathmatchManager deathmatchManager){
        this.deathmatchManager = deathmatchManager;
        objective.setDisplayName(Jumper.getPREFIX() + " §600:00");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for(int i = 0; i < Bukkit.getOnlinePlayers().size(); i++){
            Player p = (Player) Bukkit.getOnlinePlayers().stream().toArray()[i];
            objective.getScore(p.getDisplayName()).setScore(deathmatchManager.getLives(p));
        }
    }

    public void setIngameScoreboard(){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.setScoreboard(deathMatchScoreboard);
        }
    }

    public void update(){
        objective.setDisplayName(Jumper.getPREFIX() + " " + Formatting.formatSecondsToMMSS(scoreboardTimer));
        for(int i = 0; i < Bukkit.getOnlinePlayers().size(); i++){
            Player p = (Player) Bukkit.getOnlinePlayers().stream().toArray()[i];
            objective.getScore(p.getDisplayName()).setScore(deathmatchManager.getLives(p));
        }
    }


}
