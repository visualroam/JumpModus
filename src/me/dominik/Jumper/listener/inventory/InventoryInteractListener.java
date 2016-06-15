package me.dominik.Jumper.listener.inventory;

import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryInteractListener implements Listener {

    @EventHandler
    public void onInteract(InventoryClickEvent e){
        if(Jumper.getInstance().getGameState() == GameState.WAITING || Jumper.getInstance().getGameState() == GameState.COUNTDOWN){
            e.setCancelled(true);
        }
        if(e.getCurrentItem().equals(Jumper.getInstance().getItemManager().getItem("kill"))){
            e.setCancelled(true);
        }
    }

}



