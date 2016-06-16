package me.dominik.Jumper.listener.other;

import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.manager.AchievementManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player p = event.getPlayer();
        if(event.getMessage().contains("666")){
            if(!Jumper.getInstance().getAchievementManager().hasAchievement(Jumper.getInstance().getAchievementManager().getAchievement("Der Teufel"), p)){
                Jumper.getInstance().getAchievementManager().gotAchievment(p, Jumper.getInstance().getAchievementManager().getAchievement("Der Teufel"));
            }
        }
    }

}
