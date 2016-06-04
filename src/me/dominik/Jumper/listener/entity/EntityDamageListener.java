package me.dominik.Jumper.listener.entity;

import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Java15Compat;

import javax.persistence.Embeddable;

public class EntityDamageListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        GameState aktuell = Jumper.getInstance().getGameState();
        if(aktuell == GameState.WAITING || aktuell == GameState.COUNTDOWN || aktuell == GameState.GRACEPERIOD || aktuell == GameState.ENDING){
            e.setCancelled(true);
        }
    }
}
