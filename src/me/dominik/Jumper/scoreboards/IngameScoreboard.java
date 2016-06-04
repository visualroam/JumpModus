package me.dominik.Jumper.scoreboards;

import lombok.Getter;
import lombok.Setter;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.methoden.Formatierung;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;


public class IngameScoreboard {

    @Getter @Setter private int distance;
    public static int scoreboardTimer = Jumper.getInstance().getSettings().getIngameCountdow();

    private Map<String, Location> finish;
    private Map<String, Location> spawns;

    private Scoreboard ingameScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private Objective objective = ingameScoreboard.registerNewObjective("1","2");

    public IngameScoreboard(Map<String, Location> spawns, Map<String, Location> finish){
        this.spawns = spawns;
        this.finish = finish;
        objective.setDisplayName(Jumper.getPREFIX() + " §600:00");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for(int i = 0; i < Bukkit.getOnlinePlayers().size(); i++){
            Player p = (Player) Bukkit.getOnlinePlayers().stream().toArray()[i];
            objective.getScore(p.getDisplayName()).setScore(0);
        }
    }

    public void setIngameScoreboard(){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.setScoreboard(ingameScoreboard);
        }
    }

    public void update(){
        objective.setDisplayName(Jumper.getPREFIX() + " " + Formatierung.formatSecondsToMMSS(scoreboardTimer));
        for(int i = 0; i < Bukkit.getOnlinePlayers().size(); i++){
            Player p = (Player) Bukkit.getOnlinePlayers().stream().toArray()[i];
            objective.getScore(p.getDisplayName()).setScore(getDistance(i,p));
        }
    }

    public int getDistance(int i, Player p){
        Location finish = this.finish.get("finish" + String.valueOf(i+1));
        Location spawn = this.spawns.get("spawn" + String.valueOf(i+1));
        Location playerLoc = p.getLocation();

        Location finish1 = new Location(finish.getWorld(),finish.getX(),0,finish.getZ());
        Location spawn1 = new Location(spawn.getWorld(),spawn.getX(),0,spawn.getZ());
        Location playerLoc1 = new Location(playerLoc.getWorld(),playerLoc.getX(),0,playerLoc.getZ());
        distance = (int) (spawn1.distance(playerLoc1) * 100 / finish1.distance(spawn1));
        if(distance > 100){
            distance = 100;
        }
        if(distance < 0){
            distance = 0;
        }
        return distance;
    }



}
