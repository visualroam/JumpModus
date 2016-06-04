package me.dominik.Jumper.manager;

import me.dominik.Jumper.Jumper;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class DeathmatchManager {

    public DeathmatchManager(List<Player> spieler){

        for(int i = 0; i < Jumper.getInstance().getSpieler().size(); i++){
            Player player = (Player) Jumper.getInstance().getSpieler().stream().toArray()[i];
            Jumper.getInstance().getLives().put(player, 3);
        }
    }

    public Integer getLives(Player player){
        int lives = 0;
        lives = Jumper.getInstance().getLives().get(player);
        return lives;
    }

}
