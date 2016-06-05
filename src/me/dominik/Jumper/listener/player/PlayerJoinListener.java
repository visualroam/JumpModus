package me.dominik.Jumper.listener.player;

import me.dominik.Jumper.Countdowns;
import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.SpielerDatenbankManager;
import me.dominik.Jumper.manager.StatsManager;
import me.dominik.Jumper.manager.StatsWall;
import me.dominik.Jumper.scoreboards.LobbyScoreboard;
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
        SpielerDatenbankManager spielerDatenbankManager = new SpielerDatenbankManager();
        spielerDatenbankManager.createPlayer(p);
        spielerDatenbankManager.sameName(p);


        if (!dropsRemoved) {
            p.getActivePotionEffects().stream().map(PotionEffect::getType).forEach(p::removePotionEffect);
            dropsRemoved = true;
        }
        e.setJoinMessage(Jumper.getPREFIX() + "§2[+] §e" + p.getName());
        StatsManager statsManager = new StatsManager();
        statsManager.createPlayer(uuid);

        p.teleport(new Location(Bukkit.getWorld("world"),0,152,3000));

        StatsWall statsWall = new StatsWall();
        statsWall.updateOne(1);

        Jumper.getInstance().getLobbyScoreboard().setLobbyScoreboard(p);
        Jumper.getInstance().getLobbyScoreboard().update();
        p.getInventory().setHeldItemSlot(0);
        p.setGameMode(GameMode.ADVENTURE);
        p.getInventory().clear();
        p.setMaxHealth(20.0D);
        p.setHealth(20.0D);
        p.setFoodLevel(20);
        p.setExp(0.0F);
        p.setLevel(0);

        if (Jumper.getInstance().getGameState().isLobby() && Jumper.getInstance().getGameState() != GameState.ENDING) {

            if (Jumper.getInstance().getGameState() == GameState.WAITING && Bukkit.getOnlinePlayers().size() >= Jumper.getInstance().getSettings().getMinPlayers()) {
                Bukkit.broadcastMessage(Jumper.PREFIX + "Es sind genügend Spieler da um das Spiel zu starten.");
                Jumper.getInstance().setGameState(GameState.COUNTDOWN);
                Countdowns.getLobbyCountdown().startCountdown();
            }


        }



    }

}
