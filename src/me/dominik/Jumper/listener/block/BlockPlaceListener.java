package me.dominik.Jumper.listener.block;

import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(Jumper.getInstance().getGameState() != GameState.EDIT){
            if(e.getPlayer().isOp()){
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        }
    }
}
