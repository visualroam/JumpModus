package me.dominik.Jumper.listener.player;

import me.dominik.Jumper.Countdowns;
import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.AchievementManager;
import me.dominik.Jumper.manager.PlayerDatabaseManager;
import me.dominik.Jumper.manager.StatsManager;
import me.dominik.Jumper.manager.StatsWall;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;

public class PlayerJoinListener implements Listener {

    private ArrayList<Player> spieler = new ArrayList<>();

    private boolean dropsRemoved = false;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();
        PlayerDatabaseManager playerDatabaseManager = new PlayerDatabaseManager();
        playerDatabaseManager.createPlayer(p);
        playerDatabaseManager.sameName(p);


        if (!dropsRemoved) {
            p.getActivePotionEffects().stream().map(PotionEffect::getType).forEach(p::removePotionEffect);
            dropsRemoved = true;
        }
        Jumper.getInstance().getFails().put(p, 0);
        e.setJoinMessage(Jumper.getPREFIX() + "§2[+] §e" + p.getName());
        StatsManager statsManager = new StatsManager();
        statsManager.createPlayer(uuid);Jumper.getInstance().getChekpointmessage().put(p, false);
        p.teleport(new Location(Bukkit.getWorld("world"),-70,70,274));

        StatsWall statsWall = new StatsWall();
        statsWall.updateAll();


        Jumper.getInstance().getLobbyScoreboard().setLobbyScoreboard(p);
        Jumper.getInstance().getLobbyScoreboard().update();
        p.getInventory().setHeldItemSlot(0);
        p.setGameMode(GameMode.ADVENTURE);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.setMaxHealth(20.0D);
        p.setHealth(20.0D);
        p.setFoodLevel(20);
        p.setExp(0.0F);
        p.setLevel(0);

        Jumper.getInstance().getVoteingManager().addPlayer(p);

        AchievementManager achievementManager = Jumper.getInstance().getAchievementManager();
        achievementManager.createPlayer(p.getUniqueId().toString());
        Jumper.getInstance().getGainedAch().put(p, achievementManager.gainedAchievments(p));
        achievementManager.newAchievment(p);

        if(!Jumper.getInstance().getAchievementManager().hasAchievement(Jumper.getInstance().getAchievementManager().getAchievement("First Play"), p)){
            Jumper.getInstance().getAchievementManager().gotAchievment(p, Jumper.getInstance().getAchievementManager().getAchievement("First Play"));
        }

        p.getInventory().setItem(4, Jumper.getInstance().getItemManager().getItem("vote"));
        p.getInventory().setItem(5, Jumper.getInstance().getItemManager().getItem("ach"));

        if (Jumper.getInstance().getGameState().isLobby() && Jumper.getInstance().getGameState() != GameState.ENDING) {

            if (Jumper.getInstance().getGameState() == GameState.WAITING && Bukkit.getOnlinePlayers().size() >= Jumper.getInstance().getSettings().getMinPlayers()) {
                Bukkit.broadcastMessage(Jumper.PREFIX + "Es sind genügend Spieler da um das Spiel zu starten.");
                Jumper.getInstance().setGameState(GameState.COUNTDOWN);
                Countdowns.getLobbyCountdown().startCountdown();
            }


        }



    }

}
