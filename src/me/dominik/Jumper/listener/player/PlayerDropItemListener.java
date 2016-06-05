package me.dominik.Jumper.listener.player;

import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if(Jumper.getInstance().getGameState() == GameState.WAITING || Jumper.getInstance().getGameState() == GameState.COUNTDOWN){
            e.setCancelled(true);
        }
    }

}

