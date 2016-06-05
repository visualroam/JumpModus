package me.dominik.Jumper.listener.player;

import me.dominik.Jumper.Countdowns;
import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.AreaManager;
import me.dominik.Jumper.manager.ChekpointManager;
import me.dominik.Jumper.methoden.Countdown;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener{
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        if(Jumper.getInstance().getGameState() == GameState.INGAME){
            ChekpointManager chekpointManager = Jumper.getInstance().getChekpointManager();
            e.setRespawnLocation(chekpointManager.getCheckPoint(p));

        }
        if(Jumper.getInstance().getGameState() == GameState.DEATHMATCH){
            e.setRespawnLocation(Jumper.getInstance().getDeathmatchManager().getRespawnLocation(p));
        }
    }

}
