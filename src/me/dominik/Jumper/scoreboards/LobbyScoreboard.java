package me.dominik.Jumper.scoreboards;

import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.methoden.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;


public class LobbyScoreboard {

    public static int scoreboardTimer = Jumper.getInstance().getSettings().getLobbyCountdown();

    private Scoreboard lobbyScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private Objective objective = lobbyScoreboard.registerNewObjective("1","2");
    private Team space;
    private Team playercount;
    private Team placercount2;

    public LobbyScoreboard() {
        objective.setDisplayName(Jumper.getPREFIX() + " §600:00");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.space = lobbyScoreboard.registerNewTeam("space");
        this.space.setPrefix("");
        this.space.addEntry(ChatColor.WHITE.toString());

        this.playercount = lobbyScoreboard.registerNewTeam("spieleranzahl");
        playercount.setPrefix("§b§l> §bSpieler");
        playercount.addEntry(ChatColor.BLACK.toString());

        this.placercount2 = lobbyScoreboard.registerNewTeam("spieleranzahl2");
        placercount2.setPrefix(" §b§l0" + " §6Spieler");
        placercount2.addEntry(ChatColor.AQUA.toString());

        objective.getScore(ChatColor.WHITE.toString()).setScore(2);
        objective.getScore(ChatColor.BLACK.toString()).setScore(1);
        objective.getScore(ChatColor.AQUA.toString()).setScore(0);
    }

    public void setLobbyScoreboard(Player player) {
        player.setScoreboard(lobbyScoreboard);
    }

    public void update() {
        objective.setDisplayName(Jumper.getPREFIX() + " " + Formatting.formatSecondsToMMSS(scoreboardTimer));
        lobbyScoreboard.getTeam("spieleranzahl2").setPrefix(String.valueOf(Bukkit.getOnlinePlayers().size()));
    }


}
