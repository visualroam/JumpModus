package me.dominik.Jumper.listener.inventory;

import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryInteractListener implements Listener {

    @EventHandler
    public void onInteract(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if(e.getInventory().getName().equals("§4§lVoting")){
            System.out.println(e.getCurrentItem().getItemMeta().getDisplayName() + " = §4" + Jumper.getInstance().getVoteingManager().getMapName(1));
            Jumper.getInstance().getVoteingManager().playerVote(player, Jumper.getInstance().getVoteingManager().getMapID(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())));
            player.closeInventory();
        }
        if(Jumper.getInstance().getGameState() == GameState.WAITING || Jumper.getInstance().getGameState() == GameState.COUNTDOWN){
            e.setCancelled(true);
        }
        try  {
            if(e.getCurrentItem().equals(Jumper.getInstance().getItemManager().getItem("kill"))){
                e.setCancelled(true);
            }
        } catch (NullPointerException ee){
            System.out.println("WTF");
        }
    }

}



