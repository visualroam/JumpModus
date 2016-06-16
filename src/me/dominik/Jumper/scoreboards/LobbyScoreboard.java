package me.dominik.Jumper.scoreboards;

import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.AreaManager;
import me.dominik.Jumper.manager.VoteingManager;
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
    private Team titalVoting;
    private Team map1;
    private Team map2;
    private Team map3;
    private VoteingManager voteingManager = Jumper.getInstance().getVoteingManager();
    private AreaManager areaManager = new AreaManager();

    public LobbyScoreboard() {
        objective.setDisplayName(Jumper.getPREFIX() + " §600:00");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.space = lobbyScoreboard.registerNewTeam("space");
        this.space.setPrefix("");
        this.space.addEntry(ChatColor.WHITE.toString());

        this.space = lobbyScoreboard.registerNewTeam("space1");
        this.space.setPrefix("");
        this.space.addEntry(ChatColor.GOLD.toString());

        this.playercount = lobbyScoreboard.registerNewTeam("spieleranzahl");
        playercount.setPrefix("§b§l> §bSpieler");
        playercount.addEntry(ChatColor.BLACK.toString());

        this.placercount2 = lobbyScoreboard.registerNewTeam("spieleranzahl2");
        placercount2.setPrefix(" §b§l0" + " §6Spieler");
        placercount2.addEntry(ChatColor.AQUA.toString());

        this.titalVoting = lobbyScoreboard.registerNewTeam("titel");
        titalVoting.setPrefix(" §5§lVoting§e>>");
        titalVoting.addEntry(ChatColor.DARK_PURPLE.toString());

        if(Jumper.getInstance().getVoteingManager().getMapID().size() >= 1){
            String name = areaManager.getMapName(voteingManager.getMapID().get(0));
            if(name.length() >= 11){
                name = name.substring(0, 9) + "..";
            }
            this.map1 = lobbyScoreboard.registerNewTeam("map1");
            map1.setPrefix("§9" + name);
            map1.setSuffix("§c(§4" + voteingManager.getVotes(voteingManager.getMapID().get(0)) + "§c)");
            map1.addEntry(ChatColor.DARK_GRAY.toString());
            objective.getScore(ChatColor.DARK_GRAY.toString()).setScore(2);
        }
        if(Jumper.getInstance().getVoteingManager().getMapID().size() >= 2){
            String name = areaManager.getMapName(voteingManager.getMapID().get(1));
            if(name.length() >= 11){
                name = name.substring(0, 9) + "..";
            }
            this.map2 = lobbyScoreboard.registerNewTeam("map1");
            map2.setPrefix("§9" + name);
            map2.setSuffix("§c(§4" + voteingManager.getVotes(voteingManager.getMapID().get(1)) + "§c)");
            map2.addEntry(ChatColor.DARK_RED.toString());
            objective.getScore(ChatColor.DARK_RED.toString()).setScore(1);
        }
        if(Jumper.getInstance().getVoteingManager().getMapID().size() >= 3){
            String name = areaManager.getMapName(voteingManager.getMapID().get(2));
            if(name.length() >= 11){
                name = name.substring(0, 9) + "..";
            }
            this.map3 = lobbyScoreboard.registerNewTeam("map1");
            map3.setPrefix("§9" + name);
            map3.setSuffix("§c(§4" + voteingManager.getVotes(voteingManager.getMapID().get(2)) + "§c)");
            map3.setPrefix("§c§l" + areaManager.getMapName(voteingManager.getMapID().get(2)) + "§c(§4" + voteingManager.getVotes(voteingManager.getMapID().get(2)));
            map3.addEntry(ChatColor.DARK_AQUA.toString());
            objective.getScore(ChatColor.DARK_AQUA.toString()).setScore(0);
        }
        objective.getScore(ChatColor.GOLD.toString()).setScore(4);
        objective.getScore(ChatColor.DARK_PURPLE.toString()).setScore(3);
        objective.getScore(ChatColor.WHITE.toString()).setScore(7);
        objective.getScore(ChatColor.BLACK.toString()).setScore(6);
        objective.getScore(ChatColor.AQUA.toString()).setScore(5);
    }

    public void setLobbyScoreboard(Player player) {
        player.setScoreboard(lobbyScoreboard);
    }

    public void update() {
        objective.setDisplayName(Jumper.getPREFIX() + " " + Formatting.formatSecondsToMMSS(scoreboardTimer));
        lobbyScoreboard.getTeam("spieleranzahl2").setPrefix(String.valueOf(Bukkit.getOnlinePlayers().size()));

        if(Jumper.getInstance().getVoteingManager().getMapID().size() == 1){
            lobbyScoreboard.getTeam("map1").setSuffix("§c(§4" + voteingManager.getVotes(voteingManager.getMapID().get(0)) + "§c)");
        }
        if(Jumper.getInstance().getVoteingManager().getMapID().size() == 2){
            lobbyScoreboard.getTeam("map2").setSuffix("§c(§4" + voteingManager.getVotes(voteingManager.getMapID().get(1)) + "§c)");
        }
        if(Jumper.getInstance().getVoteingManager().getMapID().size() == 3){
            lobbyScoreboard.getTeam("map3").setSuffix("§c(§4" + voteingManager.getVotes(voteingManager.getMapID().get(2)) + "§c)");
        }

    }


}
