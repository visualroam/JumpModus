package me.dominik.Jumper.manager;

import com.google.gson.reflect.TypeToken;
import me.dominik.Jumper.Jumper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Banner;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StatsManager {

    private Type STRING_LOCATION_MAP = new TypeToken<Map<String, Location>>() { }.getType();
    private HashMap<Integer, String> rang = new HashMap<>();

    public StatsManager(){

    }

    public boolean playerExists(String UUID){
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
    public void createPlayer(String UUID){
        if(!(playerExists(UUID))){
            Jumper.getInstance().getMySQL().update("INSERT INTO Stats(UUID, GAMESPLAYED, KILLS, DEATHS, FAILS) VALUES ('" + UUID + "', '0', '0', '0', '0');");
        }

    }

    // Getbefehl

    public int getGamePlayed(String UUID) {
        Integer gamesplayed = 0;
        if(playerExists(UUID)){
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Stats WHERE UUID= '" + UUID + "'");
            try {
                if((!rs.next()) || (Integer.valueOf(rs.getInt("GAMESPLAYED")) == null));
                gamesplayed = rs.getInt("GAMESPLAYED");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            createPlayer(UUID);
            getGamePlayed(UUID);
        }
        return gamesplayed;
    }


    public int getKills(String UUID) {
        Integer kills = 0;
        if(playerExists(UUID)){
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Stats WHERE UUID= '" + UUID + "'");
            try {
                if((!rs.next()) || (Integer.valueOf(rs.getInt("KILLS")) == null));
                kills = rs.getInt("KILLS");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            createPlayer(UUID);
            getKills(UUID);
        }
        return kills;
    }


    public int getDeaths(String UUID) {
        Integer deaths = 0;
        if(playerExists(UUID)){
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Stats WHERE UUID= '" + UUID + "'");
            try {
                if((!rs.next()) || (Integer.valueOf(rs.getInt("DEATHS")) == null));
                deaths = rs.getInt("DEATHS");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            createPlayer(UUID);
            getDeaths(UUID);
        }
        return deaths;
    }


    public int getFails(String UUID) {
        Integer fails = 0;
        if(playerExists(UUID)){
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Stats WHERE UUID= '" + UUID + "'");
            try {
                if((!rs.next()) || (Integer.valueOf(rs.getInt("FAILS")) == null));
                fails = rs.getInt("FAILS");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            createPlayer(UUID);
            getFails(UUID);
        }
        return fails;
    }

    public int get(String uuid, String grund){
        int points = 0;
        if(grund.equalsIgnoreCase("Kills")){
            points = getKills(uuid);
        }
        if(grund.equalsIgnoreCase("Deaths")){
            points = getDeaths(uuid);
        }
        if(grund.equalsIgnoreCase("Gamesplayed")){
            points = getGamePlayed(uuid);
        }
        if(grund.equalsIgnoreCase("Fails")){
            points = getFails(uuid);
        }
        return points;
    }

    //Setbefehl

    public void setGamesPlayed(String uuid, int gamesplayed){
        if(playerExists(uuid)){
            Jumper.getInstance().getMySQL().update("UPDATE Stats SET GAMESPLAYED= '" + gamesplayed + "' WHERE UUID= '" + uuid + "';");
        } else {
            createPlayer(uuid);
            setGamesPlayed(uuid, gamesplayed);
        }
    }

    public void setKills(String uuid, int kills){
        if(playerExists(uuid)){
            Jumper.getInstance().getMySQL().update("UPDATE Stats SET KILLS= '" + kills + "' WHERE UUID= '" + uuid + "';");
        } else {
            createPlayer(uuid);
            setKills(uuid, kills);
        }
    }

    public void setDeaths(String uuid, int deaths){
        if(playerExists(uuid)){
            Jumper.getInstance().getMySQL().update("UPDATE Stats SET DEATHS= '" + deaths + "' WHERE UUID= '" + uuid + "';");
        } else {
            createPlayer(uuid);
            setDeaths(uuid, deaths);
        }
    }

    public void setFails(String uuid, int fails){
        if(playerExists(uuid)){
            Jumper.getInstance().getMySQL().update("UPDATE Stats SET FAILS= '" + fails + "' WHERE UUID= '" + uuid + "';");
        } else {
            createPlayer(uuid);
            setFails(uuid, fails);
        }
    }

    //add befehl

    public void addGamesPlayed(String uuid, int gamesplayed){
        if(playerExists(uuid)){
            setGamesPlayed(uuid, Integer.valueOf(getGamePlayed(uuid) + gamesplayed));
        } else {
            createPlayer(uuid);
            addGamesPlayed(uuid,gamesplayed);
        }
    }

    public void addKills(String uuid, int kills){
        if(playerExists(uuid)){
            setKills(uuid, Integer.valueOf(getKills(uuid) + kills));
        } else {
            createPlayer(uuid);
            addKills(uuid, kills);
        }
    }

    public void addDeaths(String uuid, int deaths){
        if(playerExists(uuid)){
            setDeaths(uuid, Integer.valueOf(getDeaths(uuid) + deaths));
        } else {
            createPlayer(uuid);
            addDeaths(uuid,deaths);
        }
    }

    public void addFails(String uuid, int fails){
        if(playerExists(uuid)){
            setFails(uuid, Integer.valueOf(getFails(uuid) + fails));
        } else {
            createPlayer(uuid);
            addFails(uuid,fails);
        }
    }

    // Remove Befehl

    public void removeGamesPlayed(String uuid, int gamesplayed){
        if(playerExists(uuid)){
            setGamesPlayed(uuid, Integer.valueOf(getGamePlayed(uuid) - gamesplayed));
        } else {
            createPlayer(uuid);
            removeGamesPlayed(uuid,gamesplayed);
        }
    }

    public void removeKills(String uuid, int kills){
        if(playerExists(uuid)){
            setKills(uuid, Integer.valueOf(getKills(uuid) - kills));
        } else {
            createPlayer(uuid);
            removeKills(uuid, kills);
        }
    }

    public void removeDeaths(String uuid, int deaths){
        if(playerExists(uuid)){
            setDeaths(uuid, Integer.valueOf(getDeaths(uuid) - deaths));
        } else {
            createPlayer(uuid);
            removeDeaths(uuid,deaths);
        }
    }

    public void removeFails(String uuid, int fails){
        if(playerExists(uuid)){
            setFails(uuid, Integer.valueOf(getFails(uuid) - fails));
        } else {
            createPlayer(uuid);
            removeFails(uuid,fails);
        }
    }

    //Reset Befehl

    public void resetStats(String uuid){
        setGamesPlayed(uuid,0);
        setKills(uuid,0);
        setDeaths(uuid,0);
        setFails(uuid,0);
    }

    //Stats Wall

    public HashMap<Integer, String> getOrder(String name, int wieviel){
        ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT UUID FROM Stats ORDER BY " + name + " LIMIT " + wieviel);
            int i = 0;
            try {
                while (rs.next()){
                    i++;
                    rang.put(i, rs.getString("UUID"));
                }
            } catch (SQLException e){

            }
        return rang;
    }











}
