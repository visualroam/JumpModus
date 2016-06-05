package me.dominik.Jumper.manager;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class RegionManager {

    private HashMap<String, Location> spawns = new HashMap<>();
    private HashMap<String, Location> finish = new HashMap<>();
    private HashMap<String, Location> death = new HashMap<>();

    private int minX, minY, minZ;
    private int maxX, maxY, maxZ;

    private World world;

    private Location loc1;
    private Location loc2;

    @Getter @Setter private int i = 1;

    public RegionManager(Location loc1, Location loc2){

        this.minX = Math.min(loc1.getBlockX(),loc2.getBlockX());
        this.minY = Math.min(loc1.getBlockY(),loc2.getBlockY());
        this.minZ = Math.min(loc1.getBlockZ(),loc2.getBlockZ());

        this.maxX = Math.max(loc1.getBlockX(),loc2.getBlockX());
        this.maxY = Math.max(loc1.getBlockY(),loc2.getBlockY());
        this.maxZ = Math.max(loc1.getBlockZ(),loc2.getBlockZ());

        this.world = loc1.getWorld();

    }

    public Location getLocationMin(){
        this.loc1 = new Location(this.world,this.minX,this.minY,this.minZ);
        return loc1;
    }
    public Location getLocationMax(){
        this.loc2 = new Location(this.world,this.maxX,this.maxY,this.maxZ);
        return loc2;
    }

    public Map<String, Location> getSpectatorSpawnLocation(boolean what){
        Location spectloc =  new Location(world,0,0,0);
        Map<String, Location> specspawn = new HashMap<>();
            specspawn.put("specspawn", spectloc);
            Location min = this.getLocationMin();
            Location max = this.getLocationMax();
            World w = min.getWorld();

            int minX = min.getBlockX();
            int minY = min.getBlockY();
            int minZ = min.getBlockZ();

            int maxX = max.getBlockX();
            int maxY = max.getBlockY();
            int maxZ = max.getBlockZ();

            for(int x = minX; x <= maxX; x++){
                for(int y = minY; y <= maxY; y++){
                    for(int z = minZ; z <= maxZ; z++){
                        Block b = w.getBlockAt(x,y,z);
                        if(b.getType().equals(Material.SPONGE)){
                            spectloc = b.getLocation();
                            specspawn.replace("specspawn", spectloc);
                            return specspawn;
                        }
                    }
                }
            }
        return specspawn;
    }

    public Map<String, Location> getSpawns(){
        World w = this.world;
        for(int x = minX; x <= maxX; x++){
            for(int y = minY; y <= maxY; y++){
                for(int z = minZ; z <= maxZ; z++){
                    Block b = w.getBlockAt(x,y,z);
                    if(b.getType().equals(Material.SKULL)){
                        Block ob = w.getBlockAt(x,y-1,z);
                        Skull skull = (Skull) b.getState();
                        Location loc = new Location(w,x,y-1,z);
                        Vector vector = new Vector(skull.getRotation().getModX(), skull.getRotation().getModY(), skull.getRotation().getModZ());
                        loc.setDirection(vector);
                        if(ob.getType() == Material.GLOWSTONE){
                            String name = "spawn1";
                            spawns.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.GOLD_BLOCK){
                            String name = "spawn2";
                            spawns.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.IRON_BLOCK){
                            String name = "spawn3";
                            spawns.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.REDSTONE_BLOCK){
                            String name = "spawn4";
                            spawns.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.COAL_BLOCK){
                            String name = "spawn5";
                            spawns.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.DIAMOND_BLOCK){
                            String name = "spawn6";
                            spawns.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.EMERALD_BLOCK){
                            String name = "spawn7";
                            spawns.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.LAPIS_BLOCK){
                            String name = "spawn8";
                            spawns.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.BEACON){
                            String name = "spawn9";
                            spawns.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.FURNACE){
                            String name = "spawn10";
                            spawns.put(name,loc);
                            ob.setType(Material.AIR);
                        }
                        b.setType(Material.AIR);
                    }
                }
            }
        }

        return spawns;
    }

    public Map<String, Location> getFinish(){
        World w = this.world;
        for(int x = minX; x <= maxX; x++){
            for(int y = minY; y <= maxY; y++){
                for(int z = minZ; z <= maxZ; z++){
                    Block b = w.getBlockAt(x,y,z);
                    if(b.getType().equals(Material.JUKEBOX)){
                        Block ob = w.getBlockAt(x,y+1,z);
                        Location loc = new Location(w,x,y,z);
                        if(ob.getType() == Material.GLOWSTONE){
                            String name = "finish1";
                            finish.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.GOLD_BLOCK){
                            String name = "finish2";
                            finish.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.IRON_BLOCK){
                            String name = "finish3";
                            finish.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.REDSTONE_BLOCK){
                            String name = "finish4";
                            finish.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.COAL_BLOCK){
                            String name = "finish5";
                            finish.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.DIAMOND_BLOCK){
                            String name = "finish6";
                            finish.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.EMERALD_BLOCK){
                            String name = "finish7";
                            finish.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.LAPIS_BLOCK){
                            String name = "finish8";
                            finish.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.BEACON){
                            String name = "finish9";
                            finish.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.FURNACE){
                            String name = "finish10";
                            finish.put(name,loc);
                            ob.setType(Material.AIR);
                        }
                        b.setType(Material.AIR);
                    }
                }
            }
        }


        return finish;
    }


    public Map<String, Location> getDeathMatchSpawns(){
        World w = this.world;
        for(int x = minX; x <= maxX; x++){
            for(int y = minY; y <= maxY; y++){
                for(int z = minZ; z <= maxZ; z++){
                    Block b = w.getBlockAt(x,y,z);
                    if(b.getType().equals(Material.JUKEBOX)){
                        Block ob = w.getBlockAt(x,y+1,z);
                        Location loc = new Location(w,x,y,z);
                        if(ob.getType() == Material.GLOWSTONE){
                            String name = "finish1";
                            death.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.GOLD_BLOCK){
                            String name = "finish2";
                            death.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.IRON_BLOCK){
                            String name = "finish3";
                            death.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.REDSTONE_BLOCK){
                            String name = "finish4";
                            death.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.COAL_BLOCK){
                            String name = "finish5";
                            death.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.DIAMOND_BLOCK){
                            String name = "finish6";
                            death.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.EMERALD_BLOCK){
                            String name = "finish7";
                            death.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.LAPIS_BLOCK){
                            String name = "finish8";
                            death.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.BEACON){
                            String name = "finish9";
                            death.put(name,loc);
                            ob.setType(Material.AIR);
                        } else if(ob.getType() == Material.FURNACE){
                            String name = "finish10";
                            death.put(name,loc);
                            ob.setType(Material.AIR);
                        }
                        b.setType(Material.AIR);
                    }
                }
            }
        }


        return death;
    }

    public Map<String, Location> getStatsWallLocation(int anzahl){
        Map<String, Location> skulls = new HashMap<>();
        World w = this.world;
        for(int x = minX; x <= maxX; x++){
            for(int y = minY; y <= maxY; y++){
                for(int z = minZ; z <= maxZ; z++){
                    Block b = w.getBlockAt(x,y,z);
                    if(b.getType().equals(Material.SPONGE)){
                        b.setType(Material.SKULL);
                        Location point = b.getLocation();
                        Block ob = w.getBlockAt(x,y-1,z);
                        if(anzahl >= 1){
                            if(ob.getType() == Material.EMERALD_BLOCK){
                                skulls.put("sign1", ob.getLocation());
                            }
                        }
                        if(anzahl >= 2){
                            if(ob.getType() == Material.DIAMOND_BLOCK){
                                skulls.put("sign2", ob.getLocation());
                            }
                        }
                        if(anzahl >= 3){
                            if(ob.getType() == Material.GOLD_BLOCK){
                                skulls.put("sign3", ob.getLocation());
                            }
                        }
                        if(anzahl >= 4){
                            if(ob.getType() == Material.IRON_BLOCK){
                                skulls.put("sign4", ob.getLocation());
                            }
                        }
                        if(anzahl >= 5){
                            if(ob.getType() == Material.REDSTONE_BLOCK){
                                skulls.put("sign5", ob.getLocation());
                            }
                        }
                        if(anzahl >= 6){
                            if(ob.getType() == Material.COAL_BLOCK){
                                skulls.put("sign6", ob.getLocation());
                            }
                        }
                        if(anzahl >= 7){
                            if(ob.getType() == Material.BEACON){
                                skulls.put("sign7", ob.getLocation());
                            }
                        }
                        if(anzahl >= 8){
                            if(ob.getType() == Material.HAY_BLOCK){
                                skulls.put("sign8", ob.getLocation());
                            }
                        }
                        if(anzahl >= 9){
                            if(ob.getType() == Material.PUMPKIN){
                                skulls.put("sign9", ob.getLocation());
                            }
                        }
                        if(anzahl >= 10){
                            if(ob.getType() == Material.BEDROCK){
                                skulls.put("sign10", ob.getLocation());
                            }
                        }




                    }
                }
            }
        }

        return skulls;
    }


}
