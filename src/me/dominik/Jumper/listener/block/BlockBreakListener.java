package me.dominik.Jumper.listener.block;

import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(Jumper.getInstance().getGameState() != GameState.EDIT){
            if(e.getPlayer().isOp()){
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        }
    }

}
