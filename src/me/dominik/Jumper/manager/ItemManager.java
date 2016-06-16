package me.dominik.Jumper.manager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemManager {

    private HashMap<String, ItemStack> items = new HashMap<>();

    public ItemManager(){

    intalizeItemStacks();

    }

    public ItemStack getItem(String name){
        return items.get(name);
    }

    public void intalizeItemStacks(){

        List<String> lore = new ArrayList<>();
        ItemStack instakill = new ItemStack(Material.SLIME_BALL, 1);
        ItemMeta insta = instakill.getItemMeta();
        insta.setDisplayName("§6I§dn§cs§bt§ea§aK§3i§5l§4l");
        insta.setLore(lore);
        instakill.setItemMeta(insta);

        lore.clear();

        ItemStack voting = new ItemStack(Material.PAPER, 1);
        ItemMeta vote = instakill.getItemMeta();
        vote.setDisplayName("§6V§do§ct§bi§en§ag");
        lore.add("§4VOTE FOR TRUMP");
        lore.add("My IDEA");
        vote.setLore(lore);
        voting.setItemMeta(vote);

        lore.clear();

        ItemStack achivment = new ItemStack(Material.EMERALD, 1);
        ItemMeta ach = instakill.getItemMeta();
        ach.setDisplayName("§5§lAchievement");
        lore.add("§4khsiuhfduisahf");
        lore.add("dakjofhoiujds");
        vote.setLore(lore);
        achivment.setItemMeta(ach);

        items.put("kill", instakill);
        items.put("vote", voting);
        items.put("ach", achivment);

        }
    }


