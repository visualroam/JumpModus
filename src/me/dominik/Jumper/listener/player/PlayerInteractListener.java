package me.dominik.Jumper.listener.player;

import me.dominik.Jumper.Countdowns;
import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.ChekpointManager;
import me.dominik.Jumper.manager.EnderChestItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Random;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(Jumper.getInstance().getGameState() == GameState.INGAME){
            if(e.getAction() == Action.PHYSICAL){
                if(e.getClickedBlock().getType() == Material.IRON_PLATE){
                    ChekpointManager chekpointManager = new ChekpointManager();
                    chekpointManager.newCheckpoint(player);
                }
                if(e.getClickedBlock().getType() == Material.GOLD_PLATE){
                    Jumper.getInstance().setGameState(GameState.GRACEPERIOD);
                    Countdowns.getInGameCountdown().stopCountdown(false);
                    Bukkit.broadcastMessage(Jumper.getPREFIX() + " Der Spieler §c" + player.getDisplayName() + "§e hat das Ziel als erstes erreicht.");
                }
            }
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(e.getClickedBlock().getType() == Material.NOTE_BLOCK){
                    EnderChestItemManager enderChestItemManager = new EnderChestItemManager();
                    Location blockLocation = e.getClickedBlock().getLocation();
                    Random random = new Random();
                    int i = random.nextInt(enderChestItemManager.getSize());
                    Inventory chest = enderChestItemManager.getChestInventory(i,blockLocation);
                    player.openInventory(chest);
                    player.sendMessage(String.valueOf(enderChestItemManager.getSize()) + " <--");
                    player.sendMessage(String.valueOf(i));
                }
            }
        }
    }

}
