package me.dominik.Jumper.manager;

import lombok.Getter;
import lombok.Setter;
import me.dominik.Jumper.Jumper;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class ChekpointManager {

    private HashMap<Player, Location> checkpoints = Jumper.getInstance().getCheckpoints();

    public ChekpointManager(List<Player> spieler){
        for(int i = 0; i < spieler.size(); i++){
            Player p = (Player) spieler.stream().toArray()[i];
            checkpoints.put(p, p.getLocation());
        }
    }

    public ChekpointManager(){

    }


    public void newCheckpoint(Player p){
        checkpoints.replace(p, p.getLocation());
    }

    public Location getCheckPoint(Player player){
        Location checkpointLoc = null;
            checkpointLoc = checkpoints.get(player);
        return checkpointLoc;
    }





}
