package me.dominik.Jumper;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Settings {

    @Getter @Setter private int lobbyCountdown = 120;
    @Getter @Setter private int ingameCountdow = 600;
    @Getter @Setter private int deathMatchCountdown = 300;

    @Getter @Setter private GameState startupMode = GameState.WAITING;

    @Getter @Setter private Location lobbySpawn = new Location(Bukkit.getWorld("world"),0,0,0,0F,0F);

    @Getter @Setter private int minPlayers = 1;

    public void updateSettings(){

    }

    public boolean UUIDExists(String UUID){
        ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Stats WHERE UUID= '" + UUID + "'");
        try {
            if(rs.next()){
                return rs.getString("UUID") != null;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getMySQLlobbyCountdown(){
        int lobbyC = this.lobbyCountdown;
        ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM ");
        return lobbyC;
    }


}




