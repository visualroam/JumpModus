package me.dominik.Jumper.listener.other;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event){
        event.setCancelled(true);
    }

}
