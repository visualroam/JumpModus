package me.dominik.Jumper.listener.player;

import me.dominik.Jumper.Countdowns;
import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.ChekpointManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerInteractListener implements Listener {

    private Map<Location, Inventory> inventories = new HashMap<>();
    //35% Chance
    private List<ItemStack> trash = Arrays.asList(
            new ItemStack(Material.FLINT,1),
            new ItemStack(Material.COOKED_BEEF,1),
            new ItemStack(Material.RABBIT_STEW,1),
            new ItemStack(Material.PUMPKIN_PIE,1),
            new ItemStack(Material.ARROW,1),
            new ItemStack(Material.BREAD,1),
            new ItemStack(Material.BAKED_POTATO,1)
            );
    //25%
    private List<ItemStack> common = Arrays.asList(
            new ItemStack(Material.WOOD_AXE,1),
            new ItemStack(Material.WOOD_SWORD,1),
            new ItemStack(Material.ARROW,3),
            new ItemStack(Material.PUMPKIN_PIE,3),
            new ItemStack(Material.FLINT,2),
            new ItemStack(Material.GOLD_INGOT,1),
            new ItemStack(Material.STICK),
            new ItemStack(Material.BREAD,3),
            new ItemStack(Material.LEATHER_BOOTS,1),
            new ItemStack(Material.LEATHER_HELMET,1),
            new ItemStack(Material.LEATHER_CHESTPLATE,1),
            new ItemStack(Material.LEATHER_LEGGINGS,1),
            new ItemStack(Material.BOW, 1)
    );
    //20%
    private List<ItemStack> uncommon = Arrays.asList(
            new ItemStack(Material.STONE_AXE,1),
            new ItemStack(Material.STONE_SWORD,1),
            new ItemStack(Material.COOKED_BEEF,4),
            new ItemStack(Material.FISHING_ROD,1),
            new ItemStack(Material.ARROW,5),
            new ItemStack(Material.GOLD_INGOT,2),
            new ItemStack(Material.IRON_INGOT,1),
            new ItemStack(Material.FLINT, 2),
            new ItemStack(Material.GOLD_BOOTS,1),
            new ItemStack(Material.GOLD_LEGGINGS,1),
            new ItemStack(Material.GOLD_CHESTPLATE,1),
            new ItemStack(Material.GOLD_HELMET,1),
            new ItemStack(Material.BOW, 1)
    );
    //15%
    private List<ItemStack> rare = Arrays.asList(
            new ItemStack(Material.GOLD_AXE,1),
            new ItemStack(Material.GOLD_SWORD,1),
            new ItemStack(Material.ARROW,7),
            new ItemStack(Material.FISHING_ROD,1),
            new ItemStack(Material.GOLD_INGOT,3),
            new ItemStack(Material.IRON_INGOT,2),
            new ItemStack(Material.FLINT_AND_STEEL,1),
            new ItemStack(Material.CHAINMAIL_BOOTS, 1),
            new ItemStack(Material.CHAINMAIL_HELMET, 1),
            new ItemStack(Material.BOW, 1)
    );

    private List<ItemStack> legendary = Arrays.asList(
            new ItemStack(Material.IRON_AXE,1),
            new ItemStack(Material.ARROW,12),
            new ItemStack(Material.IRON_INGOT,3),
            new ItemStack(Material.FLINT_AND_STEEL,1),
            new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1),
            new ItemStack(Material.CHAINMAIL_LEGGINGS, 1),
            new ItemStack(Material.DIAMOND, 1),
            new ItemStack(Material.GOLDEN_APPLE, 1)
    );




    private ThreadLocalRandom random = ThreadLocalRandom.current();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(Jumper.getInstance().getGameState() == GameState.INGAME){
            if(e.getAction() == Action.PHYSICAL){
                if(e.getClickedBlock().getType() == Material.IRON_PLATE){
                    ChekpointManager chekpointManager = new ChekpointManager();
                    if(!e.getClickedBlock().hasMetadata(player.getName())){
                        chekpointManager.newCheckpoint(player);
                        player.sendMessage(Jumper.getPREFIX() + " Checkpoint erreicht.");
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 0.5F, 0.5F);
                        e.getClickedBlock().setMetadata(player.getName(), new FixedMetadataValue(Jumper.getInstance(), true));
                    }
                }
                if(e.getClickedBlock().getType() == Material.GOLD_PLATE){
                    Jumper.getInstance().setGameState(GameState.GRACEPERIOD);
                    Countdowns.getInGameCountdown().stopCountdown(false);
                    Bukkit.broadcastMessage(Jumper.getPREFIX() + " Der Spieler §c" + player.getDisplayName() + "§e hat das Ziel als erstes erreicht.");
                }
            }
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(e.getClickedBlock().getType() == Material.NOTE_BLOCK){
                    e.getPlayer().openInventory(generateInventory(e.getClickedBlock().getLocation()));
                    e.setCancelled(true);
                }
            }
        }
    }

    private Inventory generateInventory(Location location) {
        if (inventories.containsKey(location)) return inventories.get(location);
        Inventory inventory = Bukkit.createInventory(null, 3 * 9);

        for (int i = 0; i < random.nextInt(3, 5); i++) {
            List<ItemStack> items = getRandomItemList();
            inventory.setItem(random.nextInt(27), items.get(random.nextInt(items.size())).clone());
        }

        inventories.put(location, inventory);

        return inventory;
    }

    public List<ItemStack> getRandomItemList(){
        int rnd = (int) (Math.random() * 100);
        if(rnd >= 0 && rnd <= 34){
            return trash;
        }
        if(rnd >= 35 && rnd <= 59){
            return common;
        }
        if(rnd >= 60 && rnd <= 79){
            return uncommon;
        }
        if(rnd >= 80 && rnd <= 94){
            return rare;
        }
        if(rnd >= 95 && rnd <= 100){
            return legendary;
        }
        return trash;
    }


}
