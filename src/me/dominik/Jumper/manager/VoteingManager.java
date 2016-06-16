package me.dominik.Jumper.manager;

import lombok.Getter;
import me.dominik.Jumper.Jumper;
import me.dominik.Jumper.methoden.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoteingManager {
    @Getter Inventory voteInv = Bukkit.createInventory(null, 9, "§4§lVoting");
    private AreaManager areaManager;
    private HashMap<Integer, Integer> mapvotes = new HashMap<>();
    private HashMap<Player, Integer> playerVote = new HashMap<>();
    HashMap<Integer, String> StringPerID = new HashMap<>();
    HashMap<String, Integer> IDPerString = new HashMap<>();
    @Getter private ArrayList<Integer> mapID = new ArrayList<>();

    public VoteingManager(AreaManager areaManager){
        this.areaManager = areaManager;
    }

    public void startVoting(){
        int mapdbsize = areaManager.getSize("Arenas");
            for(int i = 1; i == mapdbsize; i++){
                mapvotes.put(i, 0);
                mapID.add(i);
                StringPerID.put(i, areaManager.getMapName(i));
                IDPerString.put(StringPerID.get(i), i);
            }

    }

    public void addPlayer(Player player){
        playerVote.putIfAbsent(player, 0);
    }

    public int getMapID(String name){
        return IDPerString.get(name);
    }

    public String getMapName(int id){
        return StringPerID.get(id);
    }


    public int getVotes(int id){
        int i = 0;
        i = mapvotes.get(id);
        return i;
    }

    public void playerVote(Player player, int idMap){
        if(playerVote.get(player) == idMap){
            player.sendMessage(Jumper.getPREFIX() + "Du hast schon für diese Map gevotet.");
            return;
        }
        playerVote.replace(player, idMap);
        mapvotes.replace(idMap, mapvotes.get(idMap) + 1);
        player.sendMessage(Jumper.getPREFIX() + "Danke für deinen Vote");
    }

    public void removePlayerVote(Player player){
        int id = playerVote.get(player);
        if(!(id == 0)){
            playerVote.putIfAbsent(player, 0);
        }
    }


    public void VoteInventory(Player player){
        voteInv.clear();

        int i;
        for(i = 0; i < mapID.size(); i++){
            int id = mapID.get(i);
            List<String> lore = new ArrayList<>();
            ItemStack instakill = new ItemStack(Material.PAPER, mapvotes.get(id));
            ItemMeta insta = instakill.getItemMeta();
            insta.setDisplayName("§4" + areaManager.getMapName(id));
            lore.add("§c" + areaManager.getMapAuthor(id));
            insta.setLore(lore);
            instakill.setItemMeta(insta);
            voteInv.addItem(instakill);
            i++;
        }
        player.openInventory(voteInv);
    }

    public int whoWin(){
        int win = 0;
        if(mapID.size() == 1){
            return mapID.get(0);
        }
        if(mapID.size() == 2){
            return Math.max(mapID.get(0), mapID.get(1));
        }
        if(mapID.size() == 3){
            int i = Math.max(mapID.get(0),mapID.get(1));
            win = Math.max(mapID.get(2), i);
            return win;
        }
        if(win == 0){
            win = Formatting.Random(1, areaManager.getSize("Arenas"));
        }
        return win;
    }


}
