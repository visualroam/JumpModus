package me.dominik.Jumper.listener.player;

import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.DeathmatchManager;
import me.dominik.Jumper.manager.StatsManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.List;


public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        Player p = e.getEntity();
        Player killer = p.getKiller();
        if(Jumper.getInstance().getGameState() == GameState.DEATHMATCH){
            e.getKeepInventory();
            StatsManager statsManager = new StatsManager();
            statsManager.addKills(killer.getUniqueId().toString(), 1);
            statsManager.addDeaths(p.getUniqueId().toString(), 1);
            DeathmatchManager deathmatchManager = Jumper.getInstance().getDeathmatchManager();
            deathmatchManager.death(p);

            if(deathmatchManager.getLives(p) == 0){
                p.getLocation().getBlock().setType(Material.CHEST);

                Chest chest = (Chest) p.getLocation().getBlock().getState();

                PlayerInventory inventory = p.getInventory();
                ItemStack[] contents = inventory.getContents();
                ItemStack[] armor = inventory.getArmorContents();
                int i = 0;

                for (ItemStack itemStack : contents) {
                    if (itemStack == null || itemStack.getType() == Material.AIR) continue;
                    chest.getInventory().setItem(i++, itemStack.clone());
                }

                for (ItemStack itemStack : armor) {
                    if (itemStack == null || itemStack.getType() == Material.AIR) continue;
                    chest.getInventory().setItem(i++, itemStack.clone());
                }

                chest.update(true);

                Bukkit.getScheduler().scheduleSyncDelayedTask(Jumper.getInstance(), () -> p.getLocation().getBlock().setType(Material.AIR), 5 * 20L);
                p.setGameMode(GameMode.SPECTATOR);
                Jumper.getInstance().getSpieler().remove(p);
            }
            Bukkit.broadcastMessage(Jumper.getPREFIX() + "§c" + p.getName() + "§e wurde von §c" + killer.getName() + "§e getötet.");
            p.sendMessage(Jumper.getPREFIX() +" Verbleibende Leben: §6" + Jumper.getInstance().getDeathmatchManager().getLives(p));
            double lostHealth = killer.getMaxHealth() - killer.getHealth();
            Double regenerationTime = lostHealth * 0.15625D * 20;
            int roundRegenerationTime = (int) Math.round(regenerationTime);
            killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, roundRegenerationTime, 4), true);
            Jumper.getInstance().checkEnd();
        }
    }

}
