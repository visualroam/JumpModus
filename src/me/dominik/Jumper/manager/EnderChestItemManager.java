package me.dominik.Jumper.manager;

import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.methoden.InventoryStringDeSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;

public class EnderChestItemManager {

    private String inventory;
    int anzahl;
    private HashMap<Location, Inventory> chest = Jumper.getInstance().getChest();

    public EnderChestItemManager(){

    }

    public void createItemList(RegionManager regionManager){

        Location minLoc = regionManager.getLocationMin();
        Location maxLoc = regionManager.getLocationMax();

        World world = maxLoc.getWorld();

        int minX = minLoc.getBlockX();
        int minY = minLoc.getBlockY();
        int minZ = minLoc.getBlockZ();

        int maxX = maxLoc.getBlockX();
        int maxY = maxLoc.getBlockY();
        int maxZ = maxLoc.getBlockZ();

        for(int x = minX; x <= maxX; x++){
            for(int y = minY; y <= maxY; y++){
                for(int z = minZ; z <= maxZ; z++){
                    Block b = world.getBlockAt(x,y,z);
                    if(b.getType().equals(Material.CHEST)){
                            anzahl++;
                            Chest chest = (Chest) b.getState();
                            inventory = InventoryStringDeSerializer.InventoryToString(chest.getInventory());
                            Jumper.getInstance().getMySQL().update("INSERT INTO Inventorys(INVENTORY) VALUES ('" + inventory + "');");
                        }
                    }
                }
            }




    }

    public int getAnzahlEingelesenenKisten(){
        return anzahl;
    }


    public Inventory getChestInventory(int id, Location blockLocation){
        Inventory chests = null;
        if(chest.containsKey(blockLocation)){
            chests = chest.get(blockLocation);
        } else {
            try {
                ResultSet rs = Jumper.getInstance().getMySQL().query("SELECT * FROM Inventorys WHERE ID= '" + id + "'");
                if((!rs.next()) || (rs.getString("INVENTORY") == null));
                String loc = rs.getString("INVENTORY");
                chests = InventoryStringDeSerializer.StringToInventory(loc);
                chest.put(blockLocation, chests);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        return chests;
    }

    public int getSize(){
        ResultSet set = Jumper.getInstance().getMySQL().query("SELECT COUNT(*) FROM Inventorys");
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
