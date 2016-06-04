package me.dominik.Jumper.manager;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.mysql.MySQL;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AreaManager {

    private Type STRING_LOCATION_MAP = new TypeToken<Map<String, Location>>() { }.getType();

    Map<String, Location> spawns;
    Map<String, Location> finishs;

    @Getter private File file = new File("plugins/Jumper", "Arene.yml");
    @Getter private YamlConfiguration ymcfg = YamlConfiguration.loadConfiguration(file);

    @Getter @Setter private String name;
    @Getter @Setter private String authors;

    @Getter @Setter private RegionManager regionManager;

    @Getter private Location minLoc;
    @Getter private Location maxLoc;

    private Location specloc;

    @Getter private int rndid = 0;


    public AreaManager(String name, String authors, RegionManager regionManager) {
        this.name = name;
        this.authors = authors;
        this.regionManager = regionManager;
        this.minLoc = regionManager.getLocationMin();
        this.maxLoc = regionManager.getLocationMax();
        this.spawns = regionManager.getSpawns();
        this.finishs = regionManager.getFinish();
    }

    public AreaManager() {

    }

    public void createArena(String name, String authors, Map<String, Location> spawns, Map<String, Location> finishs){
        String location1 = Jumper.locationGson.toJson(spawns, STRING_LOCATION_MAP);
        String location2 = Jumper.locationGson.toJson(finishs, STRING_LOCATION_MAP);
        try {
            PreparedStatement statement = Jumper.getInstance().getCon().prepareStatement("INSERT INTO Arenas(wfew, AUTHORS, SPAWNS, FINISHS) VALUES (?,?,?,?);");
            statement.setString(1, name);
            statement.setString(2, authors);
            statement.setString(3, location1);
            statement.setString(4, location2);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Map<String, Location> getSpawns(int id){
        try {
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Arenas WHERE ID= '" + id + "'");
            if((!rs.next()) || (rs.getString("SPAWNS") == null));
             String loc = rs.getString("SPAWNS");
             spawns = Jumper.locationGson.fromJson(loc, STRING_LOCATION_MAP);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return spawns;
    }

    public Map<String, Location> getFinishs(int id){
        try {
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Arenas WHERE ID= '" + id + "'");
            if((!rs.next()) || (rs.getString("FINISHS") == null));
            String loc = rs.getString("FINISHS");
            finishs = Jumper.locationGson.fromJson(loc, STRING_LOCATION_MAP);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return finishs;
    }

    public String getMapName(int id){
        String Mapname = "Unknown";
        try{
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Arenas WHERE ID= '" + id + "'");
            if((!rs.next()) || (rs.getString("wfew") == null));
            Mapname = rs.getString("wfew");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return Mapname;
    }

    public String getMapAuthor(int id){
        String MapAuthor = "Unknown";
        try{
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Arenas WHERE ID= '" + id + "'");
            if((!rs.next()) || (rs.getString("AUTHORS") == null));
            MapAuthor = rs.getString("AUTHORS");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return MapAuthor;
    }

    public int getSize(String name){
            ResultSet set = Jumper.getInstance().getMySQL().query("SELECT COUNT(*) FROM " + name);
            try {
                if(set.next()) {
                    return set.getInt(1);
                }
            } catch (SQLException var3) {
                var3.printStackTrace();
            }

            return -1;
    }

    public void createDeathmatchArena(String name, String authors, Map<String, Location> spawns, Map<String, Location> finishs){
        String location1 = Jumper.locationGson.toJson(spawns, STRING_LOCATION_MAP);
        String location2 = Jumper.locationGson.toJson(finishs, STRING_LOCATION_MAP);
        try {
            PreparedStatement statement = Jumper.getInstance().getCon().prepareStatement("INSERT INTO Deathmatch(NAME, AUTHOR, SPAWNS, SPECTATOR) VALUES (?,?,?,?);");
            statement.setString(1, name);
            statement.setString(2, authors);
            statement.setString(3, location1);
            statement.setString(4, location2);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Map<String, Location> getSpectatorSpawn(int id){
        try {
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Deathmatch WHERE ID= '" + id + "'");
            if((!rs.next()) || (rs.getString("SPECTATOR") == null));
            String loc = rs.getString("SPECTATOR");
            spawns = Jumper.locationGson.fromJson(loc, STRING_LOCATION_MAP);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return spawns;
    }

    public Map<String, Location> getDeathMatchSpawns(int id){
        try {
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Deathmatch WHERE ID= '" + id + "'");
            if((!rs.next()) || (rs.getString("SPAWNS") == null));
            String loc = rs.getString("SPAWNS");
            finishs = Jumper.locationGson.fromJson(loc, STRING_LOCATION_MAP);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return finishs;
    }

    public String getDeathMatchName(int id){
        String Mapname = "Unknown";
        try{
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Deathmatch WHERE ID= '" + id + "'");
            if((!rs.next()) || (rs.getString("NAME") == null));
            Mapname = rs.getString("NAME");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return Mapname;
    }

    public String getDeathMatchAuthor(int id){
        String MapAuthor = "Unknown";
        try{
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Deathmatch WHERE ID= '" + id + "'");
            if((!rs.next()) || (rs.getString("AUTHOR") == null));
            MapAuthor = rs.getString("AUTHOR");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return MapAuthor;
    }







}

/*
{"spawn10":{"world":"world","x":330.0,"y":120.0,"z":357.0,"yaw":0.0,"pitch":0.0},"spawn2":{"world":"world","x":326.0,"y":119.0,"z":156.0,"yaw":0.0,"pitch":0.0},"spawn1":{"world":"world","x":325.0,"y":120.0,"z":137.0,"yaw":0.0,"pitch":0.0},"spawn8":{"world":"world","x":330.0,"y":120.0,"z":308.0,"yaw":0.0,"pitch":0.0},"finish3":{"world":"world","x":424.0,"y":120.0,"z":178.0,"yaw":0.0,"pitch":0.0},"spawn7":{"world":"world","x":329.0,"y":119.0,"z":286.0,"yaw":0.0,"pitch":0.0},"finish4":{"world":"world","x":423.0,"y":120.0,"z":202.0,"yaw":0.0,"pitch":0.0},"finish5":{"world":"world","x":424.0,"y":120.0,"z":227.0,"yaw":0.0,"pitch":0.0},"finish10":{"world":"world","x":427.0,"y":120.0,"z":357.0,"yaw":0.0,"pitch":0.0},"spawn9":{"world":"world","x":329.0,"y":120.0,"z":332.0,"yaw":0.0,"pitch":0.0},"finish6":{"world":"world","x":425.0,"y":120.0,"z":267.0,"yaw":0.0,"pitch":0.0},"spawn4":{"world":"world","x":326.0,"y":120.0,"z":202.0,"yaw":0.0,"pitch":0.0},"finish7":{"world":"world","x":426.0,"y":119.0,"z":286.0,"yaw":0.0,"pitch":0.0},"spawn3":{"world":"world","x":327.0,"y":120.0,"z":178.0,"yaw":0.0,"pitch":0.0},"finish8":{"world":"world","x":427.0,"y":120.0,"z":308.0,"yaw":0.0,"pitch":0.0},"spawn6":{"world":"world","x":328.0,"y":120.0,"z":267.0,"yaw":0.0,"pitch":0.0},"finish9":{"world":"world","x":426.0,"y":120.0,"z":332.0,"yaw":0.0,"pitch":0.0},"spawn5":{"world":"world","x":327.0,"y":120.0,"z":227.0,"yaw":0.0,"pitch":0.0},"finish1":{"world":"world","x":422.0,"y":120.0,"z":137.0,"yaw":0.0,"pitch":0.0},"finish2":{"world":"world","x":423.0,"y":119.0,"z":156.0,"yaw":0.0,"pitch":0.0}}
 */
