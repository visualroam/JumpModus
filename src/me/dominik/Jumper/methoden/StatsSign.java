package me.dominik.Jumper.methoden;

import me.dominik.Jumper.manager.SpielerDatenbankManager;
import me.dominik.Jumper.manager.StatsManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;

public class StatsSign {

    private int place;
    private  Location signLocation;
    private String grund;
    private String name;

    public StatsSign(int place, Location signLocation, String grund, String name){
        this.place = place;
        this.signLocation = signLocation;
        this.grund = grund;
        this.name = name;
    }

    public void setSign(){
        World world = signLocation.getWorld();
        int x = (int) signLocation.getX();
        int y = (int) signLocation.getY();
        int z = (int) signLocation.getZ();
        Block b = world.getBlockAt(x,y,z);
        BlockState bs = b.getState();
        SpielerDatenbankManager spielerDatenbankManager = new SpielerDatenbankManager();
        StatsManager statsManager = new StatsManager();
        int points =  statsManager.get(spielerDatenbankManager.getPlayerUUID(name), grund);
        if(bs instanceof Sign){
            Sign sign = (Sign) bs;
            sign.setLine(0, Formatierung.getPlatz(place));
            sign.setLine(1, name);
            sign.setLine(2, String.valueOf(points));
            sign.setLine(3, grund.toUpperCase());
            sign.update();
        } else {
            b.setType(Material.WALL_SIGN);
            setSign();
        }
    }

    public void setSkull(){
        World world = signLocation.getWorld();
        int x = (int) signLocation.getX();
        int y = (int) signLocation.getY() + 1;
        int z = (int) signLocation.getZ();
        Block b = world.getBlockAt(x,y,z);
        BlockState bs = b.getState();
        if(bs instanceof Skull){
            Skull skull = (Skull) bs;
            if(skull.getSkullType() == SkullType.PLAYER){
                skull.setOwner(name);
                skull.update();
            } else {
                skull.setSkullType(SkullType.PLAYER);
                setSkull();
            }
        } else {
            b.setType(Material.SKULL);
            setSkull();
        }
    }


}
