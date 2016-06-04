package me.dominik.Jumper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import me.dominik.Jumper.commands.*;
import me.dominik.Jumper.listener.block.BlockBreakListener;
import me.dominik.Jumper.listener.block.BlockPlaceListener;
import me.dominik.Jumper.listener.entity.EntityDamageListener;
import me.dominik.Jumper.listener.other.SeverPingEvent;
import me.dominik.Jumper.listener.player.*;
import me.dominik.Jumper.methoden.LocationTypeAdapter;
import me.dominik.Jumper.mysql.MySQL;
import me.dominik.Jumper.scoreboards.DeathMatchScoreboard;
import me.dominik.Jumper.scoreboards.IngameScoreboard;
import me.dominik.Jumper.scoreboards.LobbyScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.*;

public class Jumper extends JavaPlugin implements Listener {
    @Getter public static final String PREFIX = "§9Jumper §8| §e";
    @Getter private static Jumper instance;
    @Getter @Setter private LobbyScoreboard lobbyScoreboard;
    @Getter @Setter private IngameScoreboard ingameScoreboard;
    @Getter @Setter private DeathMatchScoreboard deathMatchScoreboard;
    @Getter @Setter private GameState gameState;
    @Getter private Settings settings;
    @Getter private MySQL mySQL;
    @Getter @Setter private Connection con;
    @Getter @Setter private List<Player> spieler;
    public static Gson locationGson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationTypeAdapter()).create();
    @Getter @Setter private HashMap<Player, Location> checkpoints = new HashMap<>();
    @Getter @Setter private HashMap<Location, Inventory> chest = new HashMap<>();
    @Getter @Setter private HashMap<Player, Integer> lives = new HashMap<>();
    @Getter @Setter private Location specloc;
    @Getter @Setter private HashMap<String, Location> spec = new HashMap<>();

    @Override
    public void onEnable() {
        super.onEnable();

        setGameState(GameState.WAITING);

        ConnectMySQL();

        initWorld();
        registerCommands();
        registerListener();
        initSettings();
        initLobbyScroeboard();

    }

    public void onDisable() {
        super.onDisable();
    }

    public void onLoad() {
        Jumper.instance = this;
    }


    public void registerCommands() {
        this.getCommand("edit").setExecutor(new EditCommand());
        this.getCommand("stats").setExecutor(new StatsCommand());
        this.getCommand("arena").setExecutor(new ArenaCreateCommand());
        this.getCommand("start").setExecutor(new StartCommand());
        this.getCommand("test").setExecutor(new TestCommand());
        this.getCommand("chest").setExecutor(new ChestCommand());
        this.getCommand("statswall").setExecutor(new StatsWallCommand());
    }

    public void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new SeverPingEvent(),this);
        pluginManager.registerEvents(new FoodLevelChangeListener(), this);
        pluginManager.registerEvents(new PlayerDropItemListener(),this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerRespawnListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(),this);
        pluginManager.registerEvents(new BlockPlaceListener(),this);
        pluginManager.registerEvents(new EntityDamageListener(),this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
    }

    public void initWorld() {
        World world = Bukkit.getWorld("world");

        world.setDifficulty(Difficulty.PEACEFUL);
        world.setSpawnFlags(false, false);
        world.setThunderDuration(0);
        world.setThundering(false);
        world.setAutoSave(false);
        world.setStorm(false);
        world.setTime(12000L);
        world.setPVP(true);

    }

    public void initSettings() {
        this.settings = new Settings();
    }


    public void initLobbyScroeboard() {
        if (gameState == GameState.WAITING) {
            this.lobbyScoreboard = new LobbyScoreboard();
            this.lobbyScoreboard.update();
        }



    }

    public void checkEnd(){
        if(spieler.size() == 1){
            Player winner = spieler.get(0).getPlayer();
            Bukkit.getScheduler().cancelTasks(this);

            for (int i = 0; i < 5; i++) Bukkit.broadcastMessage(" ");
            Bukkit.broadcastMessage(Jumper.PREFIX + "§6§l" + winner.getName() + " §bhat die Runde gewonnen!");
            winner.sendMessage(Jumper.PREFIX + "§a§lHerzlichen Glückwunsch!");
            winner.sendMessage(Jumper.PREFIX + "§aDu hast das Spiel gewonnen!");
            Bukkit.broadcastMessage(" ");
            stopServer();
        }
        Bukkit.broadcastMessage(Jumper.PREFIX + "§6" + spieler.size() + " §bSpieler verbleiben.");
    }


    public void ConnectMySQL(){
        mySQL = new MySQL("localhost", "jumper", "dome", "uhBxYycytjaSZrG5");
        mySQL.update("CREATE TABLE IF NOT EXISTS Stats(UUID varchar(64), GAMESPLAYED int, KILLS int, DEATHS int, FAILS int);");
        mySQL.update("CREATE TABLE IF NOT EXISTS Arenas(ID int,wfew varchar(255), AUTHORS varchar(255), SPAWNS text, FINISHS text);");
        mySQL.update("CREATE TABLE IF NOT EXISTS Settings(UUID varchar(64),lobbyCountdown int,inGameCountdown int, deathMatchCountdown int,minPlayers int);");
        mySQL.update("CREATE TABLE IF NOT EXISTS Inventorys(ID int, INVENTORY text);");
        mySQL.update("CREATE TABLE IF NOT EXISTS Deathmatch(ID int, NAME text, AUTHOR text, SPAWNS text, SPECTATOR text);");
        mySQL.update("CREATE TABLE IF NOT EXISTS Spieler(NAME text, UUID text);");
        mySQL.update("CREATE TABLE IF NOT EXISTS StatsWall(ID int, NUMBER int, REASON text, LOCATIONS text);");
    }

    public void stopServer() {
        Bukkit.broadcastMessage(Jumper.PREFIX + "Der Server startet in §615 §bSekunden neu...");
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop Das Spiel ist beendet!"), 15 * 20L);
    }
}



