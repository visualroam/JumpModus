package me.dominik.Jumper.listener.player;

import me.dominik.Jumper.Countdowns;
import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.ChekpointManager;
import me.dominik.Jumper.manager.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        try {

            Player player = e.getPlayer();
            if(Jumper.getInstance().getGameState() == GameState.WAITING || Jumper.getInstance().getGameState() == GameState.COUNTDOWN){
                if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK ){
                    if(e.getItem().equals(Jumper.getInstance().getItemManager().getItem("vote"))){
                        Jumper.getInstance().getVoteingManager().VoteInventory(player);
                    }
                    if(e.getItem().equals(Jumper.getInstance().getItemManager().getItem("ach"))){
                        Jumper.getInstance().getAchievementManager().openInventory(player);
                        player.sendMessage("TEST");
                    }
                }
            }
            if(Jumper.getInstance().getGameState() == GameState.INGAME || Jumper.getInstance().getGameState() == GameState.AFTER){
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
                        if(Jumper.getInstance().getGameState() == GameState.AFTER){
                            player.getInventory().addItem(new ItemStack(Material.IRON_BOOTS, 1));
                            Bukkit.broadcastMessage(Jumper.getPREFIX() + " Der Spieler §c" + player.getDisplayName() + "§e hat ebenfalls das Ziel erreicht.");
                        }

                        if(!Jumper.getInstance().getAchievementManager().hasAchievement(Jumper.getInstance().getAchievementManager().getAchievement("Der UBER-Pro"), player)){
                            Jumper.getInstance().getAchievementManager().gotAchievment(player, Jumper.getInstance().getAchievementManager().getAchievement("Der UBER-Pro"));
                        }

                        Jumper.getInstance().setGameState(GameState.AFTER);
                        Countdowns.getInGameCountdown().stopCountdown(false);
                        player.getInventory().addItem(new ItemStack(Material.DIAMOND_BOOTS, 1));
                        Bukkit.broadcastMessage(Jumper.getPREFIX() + " Der Spieler §c" + player.getDisplayName() + "§e hat das Ziel als erstes erreicht.");
                        Countdowns.getAfterIngame().startCountdown();
                    }
                }
                if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
                    if(e.getClickedBlock().getType() == Material.NOTE_BLOCK){
                        e.getPlayer().openInventory(generateInventory(e.getClickedBlock().getLocation()));
                        e.setCancelled(true);
                    }
                }
                if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK ){
                    if(e.getItem().equals(Jumper.getInstance().getItemManager().getItem("kill"))){
                        if(e.getItem().getItemMeta().getDisplayName().equals("§5§lBitte Warten")){
                            return;
                        }
                        player.getInventory().clear(0);
                        ItemStack i = e.getItem();
                        ItemMeta meta = i.getItemMeta();
                        meta.setDisplayName("§5§lBitte Warten");
                        i.setItemMeta(meta);
                        player.getInventory().setItem(0, i);
                        StatsManager statsManager = new StatsManager();
                        statsManager.addFails(player.getUniqueId().toString(),1);
                        Jumper.getInstance().getFails().put(player, Jumper.getInstance().getFails().get(player) + 1);
                        ChekpointManager chekpointManager = Jumper.getInstance().getChekpointManager();
                        player.setFallDistance(0);
                        player.teleport(chekpointManager.getCheckPoint(player));
                        player.setFallDistance(0);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Jumper.getInstance(), () -> {
                            player.getInventory().clear(0);
                            meta.setDisplayName("§6I§dn§cs§bt§ea§aK§3i§5l§4l");
                            i.setItemMeta(meta);
                            player.getInventory().setItem(0, i);
                        }, 100L);

                    }
                }
            }
        }catch (NullPointerException aex){

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
