package me.dominik.Jumper.manager;

import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.methoden.Achievement;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.util.UUIDTypeAdapter;
import java.util.*;
public class AchievementManager {

    private static HashMap<String, Achievement> achievements = new HashMap<>();


    public void registerAchievement(Achievement achievement) {
        achievements.put(achievement.getName(), achievement);
    }


    public List<Achievement> getAchievements() {
        return new ArrayList<>(achievements.values());
    }
    public Achievement getAchievement(String name) {
        for (Achievement achievement : achievements.values()) {
            if (achievement.getName().equalsIgnoreCase(name)) {
                return achievement;
            }
        }

        return null;
    }

    public String ListToString(HashMap<String, Boolean> achievements){
        String list = "";
        for(int i = 0; i < achievements.size(); i++){
            String name = getAchievements().get(i).getName();
            String bb = String.valueOf(achievements.get(name));
            list = list + name + "|" + bb + ":";
        }
        return list;
    }

    public HashMap<String, Boolean> StringToList(String list){
        HashMap<String, Boolean> achievementHashMap = new HashMap<>();
        String[] achievments = list.split(":");
        for(int i = 0; i < achievments.length; i++){
            String[] achievment = achievments[i].split("|");
            achievementHashMap.put(achievment[0], Boolean.valueOf(achievment[1]));
        }
        return achievementHashMap;
    }

    public boolean playerExists(String UUID){
        ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Achievment WHERE UUID= '" + UUID + "'");
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
            System.out.println("CREATEPLAYER");
            Jumper.getInstance().getMySQL().update("INSERT INTO Achievment(UUID, LIST) VALUES ('" + UUID + "', '" + ListToString(defaultMap()) + "');");
        }

    }

    public String getDataBaseString(String UUID) {
        String gamesplayed = "";
        if(playerExists(UUID)){
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Achievment WHERE UUID= '" + UUID + "'");
            try {
                if((!rs.next()) || rs.getString("LIST") == null);
                gamesplayed = rs.getString("LIST");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            createPlayer(UUID);
            getDataBaseString(UUID);
        }
        return gamesplayed;
    }

    public void setString(String UUID, String database){
        Jumper.getInstance().getMySQL().update("INSERT INTO Achievment(UUID, LIST) VALUES ('" + UUID + "', '" + database + "');");
    }


    private HashMap<String, Boolean> defaultMap(){
        HashMap<String , Boolean> defaultmap = new HashMap<>();
        for(int i = 0; i < getAchievements().size();i++){
            defaultmap.put(getAchievements().get(i).getName(), false);
        }
        return defaultmap;
    }

    public void getGainedA(Player p){
        List<Achievement> achievements = new ArrayList<>();
        if(!playerExists(p.getUniqueId().toString())){
            String db = getDataBaseString(p.getUniqueId().toString());
            HashMap<String, Boolean> test = StringToList(db);
            for(int i = 0; i < test.size(); i++){
                String name = getAchievements().get(i).getName();
                Boolean b = test.get(name);
                if(b){
                   achievements.add(getAchievement(name));
                }
            }
            Jumper.getInstance().getGainedAch().put(p, achievements);
        } else {
            createPlayer(p.getUniqueId().toString());
            getGainedA(p);
        }

    }


}
