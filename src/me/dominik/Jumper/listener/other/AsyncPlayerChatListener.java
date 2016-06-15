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
            if(!Jumper.getInstance().getGainedAch().get(p).contains(Jumper.getInstance().getAchievementManager().getAchievement("Der Teufel"))){
                AchievementManager manager = Jumper.getInstance().getAchievementManager();
                HashMap<String, Boolean> test = manager.StringToList(manager.getDataBaseString(p.getUniqueId().toString()));
                test.replace("Der Teufel", true);
                manager.setString(p.getUniqueId().toString(), manager.ListToString(test));
                p.sendMessage("§7§m✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛");
                p.sendMessage("§2Erfolg erziehlt: §a" + " Der Teufel");
                p.sendMessage("§2" + manager.getAchievement("Der Teufel").getDescription());
                p.sendMessage("§7§m✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛");
            }
        }
    }

}
