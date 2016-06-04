package me.dominik.Jumper.listener.player;

import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.StatsManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.concurrent.atomic.AtomicReference;


public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        Player p = e.getEntity();
        Player killer = p.getKiller();
        if(Jumper.getInstance().getGameState() == GameState.INGAME){
            StatsManager statsManager = new StatsManager();
            statsManager.addFails(p.getUniqueId().toString(),1);
        }
        if(Jumper.getInstance().getGameState() == GameState.DEATHMATCH){
            StatsManager statsManager = new StatsManager();
            statsManager.addKills(killer.getUniqueId().toString(), 1);
            statsManager.addDeaths(p.getUniqueId().toString(), 1);
            p.setGameMode(GameMode.SPECTATOR);
            Jumper.getInstance().checkEnd();
        }
    }

}
