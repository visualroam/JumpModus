package me.dominik.Jumper.manager;

import com.google.gson.reflect.TypeToken;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.methoden.StatsSign;
import org.bukkit.Location;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StatsWall {

    private Type STRING_LOCATION_MAP = new TypeToken<Map<String, Location>>() { }.getType();

    public StatsWall(){

    }

    public void addStatsWall(Map<String, Location> signs, int number, String grund){
            String location1 = Jumper.locationGson.toJson(signs, STRING_LOCATION_MAP);
            try {
                PreparedStatement statement = Jumper.getInstance().getCon().prepareStatement("INSERT INTO StatsWall(NUMBER, REASON, LOCATIONS) VALUES (?,?,?);");
                statement.setInt(1, number);
                statement.setString(2, grund);
                statement.setString(3, location1);
                statement.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            }
    }

    public Map<String, Location> getSigns(int id){
        HashMap<String, Location> signs = new HashMap<>();
        try {
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM StatsWall WHERE ID= '" + id + "'");
            if((!rs.next()) || (rs.getString("LOCATIONS") == null));
            String loc = rs.getString("LOCATIONS");
            signs = Jumper.locationGson.fromJson(loc, STRING_LOCATION_MAP);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return signs;
    }

    public void updateOne(int id){
        String grund = "";
        int anzahl = 0;
        StatsManager statsManager = new StatsManager();
        try {
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM StatsWall WHERE ID= '" + id + "'");
            if((!rs.next()) || (rs.getString("REASON") == null));
            grund = rs.getString("REASON").toUpperCase();
        } catch (SQLException e){
            e.printStackTrace();
        }
        ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM StatsWall WHERE ID= '" + id + "'");
        try {
            if((!rs.next()) || (Integer.valueOf(rs.getInt("NUMBER")) == null));
            anzahl = rs.getInt("NUMBER");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HashMap<Integer, String> ranks = statsManager.getOrder(grund,anzahl);
        for(int i = 0; i < ranks.size(); i++){
            int ids = i + 1;
            Location loc = getSigns(id).get("sign" + ids);
            StatsSign statsSign = new StatsSign(ids,loc,grund,ranks.get(id));
            statsSign.setSign();
            statsSign.setSkull();
        }

    }



}
