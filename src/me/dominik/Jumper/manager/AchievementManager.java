package me.dominik.Jumper.manager;

import com.google.gson.reflect.TypeToken;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.methoden.Achievement;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.util.UUIDTypeAdapter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
public class AchievementManager {

    private static HashMap<String, Achievement> achievements = new HashMap<>();
    Type type = new TypeToken<Map<String, Boolean>>() { }.getType();
    private Gson gson = new Gson();
    Inventory achievementInventory = Bukkit.createInventory(null,9, "§a§lAchievments");


/*
String json = gson.toJson(map, type);
Map<String, Boolean> map = gson.fromJson(map, type);
 */


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
            Jumper.getInstance().getMySQL().update("INSERT INTO Achievment(UUID, LIST) VALUES ('" + UUID + "', '" + gson.toJson(defaultMap(), type) + "');");
        }

    }

    public String getString(String UUID) {
        String gamesplayed = "";
        if(playerExists(UUID)){
            ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Achievment WHERE UUID= '" + UUID + "'");
            try {
                if((!rs.next()) || (rs.getString("LIST") == null));
                gamesplayed = rs.getString("LIST");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            createPlayer(UUID);
            getString(UUID);
        }
        return gamesplayed;
    }


    public void setString(String UUID, Map<String, Boolean> map){
        Jumper.getInstance().getMySQL().update("UPDATE Achievment SET LIST= '" + gson.toJson(map, type) + "' WHERE UUID= '" + UUID + "';");
    }

    public void newAchievment(Player player){
        Map<String, Boolean> string = gson.fromJson(getString(player.getUniqueId().toString()), type);
        for(Achievement achievement : getAchievements()){
            try {
                boolean b = string.get(achievement.getName());
            } catch (NullPointerException e){
                string.put(achievement.getName(), false);
                setString(player.getUniqueId().toString(), string);
            }
        }


    }

    public void gotAchievment(Player player, Achievement achievement){
        String UUID = player.getUniqueId().toString();
        Map<String, Boolean> ach = gson.fromJson(getString(UUID), type);
        if(!hasAchievement(achievement, player)){
            Jumper.getInstance().getGainedAch().get(player).add(achievement);
            ach.replace(achievement.getName(), true);
            setString(UUID, ach);
        }
        player.sendMessage("§7§m✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛");
        player.sendMessage("§2Erfolg erzielt: " + achievement.getName());
        player.sendMessage("§2" + achievement.getDescription());
        player.sendMessage("§7§m✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛✛");
    }

    public List<Achievement> gainedAchievments(Player player){
        List<Achievement> achievements = getAchievements();
        Map<String, Boolean> MapBoolean = gson.fromJson(getString(player.getUniqueId().toString()), type);
        List<Achievement> gainedAchievments = new ArrayList<>();
        for(int i = 0; i < MapBoolean.size(); i++){
               if(MapBoolean.get(achievements.get(i).getName())){
                   gainedAchievments.add(getAchievement(achievements.get(i).getName()));
               }
        }
        return gainedAchievments;
    }


    private HashMap<String, Boolean> defaultMap(){
        HashMap<String , Boolean> defaultmap = new HashMap<>();
        for(int i = 0; i < getAchievements().size();i++){
            defaultmap.put(getAchievements().get(i).getName(), false);
        }
        return defaultmap;
    }


    public boolean hasAchievement(Achievement achievement, Player player) {
        return Jumper.getInstance().getGainedAch().get(player).contains(achievement);
    }

    public void openInventory(Player player){
        achievementInventory.clear();
        for(Achievement achievement : getAchievements()){
            if(hasAchievement(achievement, player)){
                List<String> lore = new ArrayList<>();
                ItemStack instakill = new ItemStack(Material.NETHER_STAR, 1);
                ItemMeta insta = instakill.getItemMeta();
                insta.setDisplayName("§4" + achievement.getName());
                lore.add("§c" + achievement.getDescription());
                insta.setLore(lore);
                instakill.setItemMeta(insta);
                achievementInventory.addItem(instakill);
            } else {
                List<String> lore = new ArrayList<>();
                ItemStack instakill = new ItemStack(Material.COAL, 1);
                ItemMeta insta = instakill.getItemMeta();
                insta.setDisplayName("§4" + achievement.getName());
                lore.add("§8???");
                insta.setLore(lore);
                instakill.setItemMeta(insta);
                achievementInventory.addItem(instakill);
            }
        }
        player.openInventory(achievementInventory);
    }







}
