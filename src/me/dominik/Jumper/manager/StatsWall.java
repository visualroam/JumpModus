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
        Map<String, Location> signs = new HashMap<>();
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
        SpielerDatenbankManager spielerDatenbankManager = new SpielerDatenbankManager();
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
        for(int i = 1; i < ranks.size() + 1; i++){

            Location loc = getSigns(id).get("sign" + i);
            StatsSign statsSign = new StatsSign(i,loc,grund,spielerDatenbankManager.getPlayerName(ranks.get(i)));
            statsSign.setSign();
            statsSign.setSkull();
        }

    }

    public void updateAll(){
        for(int i = 1; i < getSize() + 1; i++){
            System.out.println(i);
            updateOne(i);
        }
    }

    public int getSize(){
        ResultSet set = Jumper.getInstance().getMySQL().query("SELECT COUNT(*) FROM StatsWall");
        try {
            if(set.next()) {
                return set.getInt(1);
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return -1;
    }



}
