package me.dominik.Jumper.listener.player;

import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.ChekpointManager;
import me.dominik.Jumper.manager.StatsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player p = event.getPlayer();
        if((Jumper.getInstance().getGameState() == GameState.INGAME  && p.getLocation().getY() < 100) || (Jumper.getInstance().getGameState() == GameState.INGAME  && p.getLocation().getY() < 100)){
            StatsManager statsManager = new StatsManager();
            statsManager.addFails(p.getUniqueId().toString(),1);
            Jumper.getInstance().getFails().put(p, Jumper.getInstance().getFails().get(p) + 1);
            ChekpointManager chekpointManager = Jumper.getInstance().getChekpointManager();
            p.setFallDistance(0);
            p.teleport(chekpointManager.getCheckPoint(p));
            p.setFallDistance(0);
        }
    }

}
