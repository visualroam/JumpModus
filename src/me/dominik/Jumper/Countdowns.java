package me.dominik.Jumper;

import lombok.Getter;
import lombok.Setter;
import me.dominik.Jumper.manager.AreaManager;
import me.dominik.Jumper.manager.ChekpointManager;
import me.dominik.Jumper.manager.DeathmatchManager;
import me.dominik.Jumper.manager.StatsManager;
import me.dominik.Jumper.methoden.Countdown;
import me.dominik.Jumper.methoden.CountdownEvent;

import me.dominik.Jumper.methoden.Formatierung;
import me.dominik.Jumper.methoden.Title;
import me.dominik.Jumper.scoreboards.DeathMatchScoreboard;
import me.dominik.Jumper.scoreboards.IngameScoreboard;
import me.dominik.Jumper.scoreboards.LobbyScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Countdowns {

    static Map<String, Location> spawns;
    static Map<String, Location> finish;
    static Map<String, Location> spawnss;


    @Getter @Setter static ChekpointManager chekpointManager;
    @Getter @Setter static DeathmatchManager deathmatchManager;

    @Getter @Setter static AreaManager arena;

    @Getter private static Countdown lobbyCountdown = new Countdown(Jumper.getInstance(), new CountdownEvent() {

        @Override
        public void tick(int i) {
            LobbyScoreboard.scoreboardTimer = i;
            Jumper.getInstance().getLobbyScoreboard().update();
            for(Player p : Bukkit.getOnlinePlayers()){
                p.setLevel(i);
                p.setExp(((float) i / Jumper.getInstance().getSettings().getLobbyCountdown()));
            }
            if(i == 120){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + "Das Spiel beginnt in §6 2 Minuten.");
                Title title = new Title("§6 Noch 2 Minuten ", "§4 Jumper by _XD0M3_",1,2,1);
                title.setTitleColor(ChatColor.BLUE);
                title.setSubtitleColor(ChatColor.GOLD);
                title.broadcast();
            }
            if(i == 60){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + "Das Spiel beginnt in §6 1 Minute.");
                Title title = new Title("§6 Noch 1 Minuten ", " §4Jumper by _XD0M3_",1,2,1);
                title.setTitleColor(ChatColor.BLUE);
                title.setSubtitleColor(ChatColor.GOLD);
                title.broadcast();
            }
            if(i == 30){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + "Das Spiel beginnt in §6 30 Sekunden.");
                Title title = new Title(" §6 Noch 30 Sekunden ", " §4Jumper by _XD0M3_",1,2,1);
                title.setTitleColor(ChatColor.BLUE);
                title.setSubtitleColor(ChatColor.GOLD);
                title.broadcast();
            }
            if(i < 11){
                if(i == 5){
                    arena = new AreaManager();
                    int randomID = Formatierung.Random(1, arena.getSize("Arenas"));
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(Jumper.getPREFIX() + "§6Gespielt wird auf §c" + arena.getMapName(randomID) + " !");
                    Bukkit.broadcastMessage(Jumper.getPREFIX() + "§6Von: " + arena.getMapAuthor(randomID));
                    Countdowns.spawns = arena.getSpawns(randomID);
                    Countdowns.finish = arena.getFinishs(randomID);
                    Title title = new Title("§e Gespielt wird auf §6" + arena.getMapName(randomID) + "§e!", "§eVon: §4" + arena.getMapAuthor(randomID),1,3,1);
                    title.setTitleColor(ChatColor.BLUE);
                    title.setSubtitleColor(ChatColor.GOLD);
                    title.broadcast();
                    Bukkit.broadcastMessage("");
                }
                Bukkit.broadcastMessage(Jumper.getPREFIX() + "Das Spiel beginnt in §6 " + i + " Sekunden.");
            }

    }

        @Override
        public void finish() {

            StatsManager statsManager = new StatsManager();
            Jumper.getInstance().setIngameScoreboard(new IngameScoreboard(spawns,finish));
            Jumper.getInstance().setSpieler((List<Player>) Bukkit.getOnlinePlayers());

            for(int i = 0; i < Jumper.getInstance().getSpieler().size(); i++){
                Player player = (Player) Jumper.getInstance().getSpieler().stream().toArray()[i];
                Jumper.getInstance().getIngameScoreboard().setIngameScoreboard();
                Location location = spawns.get("spawn" + String.valueOf(i + 1));
                player.setGameMode(GameMode.SURVIVAL);
                player.setHealth(20.0D);
                player.setLevel(0);
                player.setExp(0.0F);
                player.getInventory().clear();
                player.teleport(location);
                statsManager.addGamesPlayed(player.getUniqueId().toString(),1);
            }

            Jumper.getInstance().setGameState(GameState.INGAME);
            inGameCountdown.startCountdown();
            ChekpointManager chekpointManager = new ChekpointManager(Jumper.getInstance().getSpieler());

        }
    },Jumper.getInstance().getSettings().getLobbyCountdown(),0,20L);


    @Getter private static Countdown inGameCountdown = new Countdown(Jumper.getInstance(), new CountdownEvent() {
        @Override
        public void tick(int i) {
            IngameScoreboard.scoreboardTimer = i;
            Jumper.getInstance().getIngameScoreboard().update();
            if(i == 600){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 10 Minuten!");
            }
            if(i == 540){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 9 Minuten!");
            }
            if(i == 480){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 8 Minuten!");
            }
            if(i == 420){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 7 Minuten!");
            }
            if(i == 360){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 6 Minuten!");
            }
            if(i == 300){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 5 Minuten!");
            }
            if(i == 240){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 4 Minuten!");
            }
            if(i == 180){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 3 Minuten!");
            }
            if(i == 120){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 2 Minuten!");
            }
            if(i == 60){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 1 Minuten!");
            }
            if(i == 30){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 30 Sekunden!");
            }
            if(i <= 10){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch " + i + " Sekunden!");
            }

        }

        @Override
        public void finish() {
            Jumper.getInstance().setGameState(GameState.GRACEPERIOD);
            int randomID = Formatierung.Random(1, arena.getSize("Deathmatch"));
            AreaManager areaManager = new AreaManager();
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(Jumper.getPREFIX() + "§6Gespielt wird auf §c" + arena.getDeathMatchName(randomID) + " !");
            Bukkit.broadcastMessage(Jumper.getPREFIX() + "§6Von: " + arena.getDeathMatchAuthor(randomID));
            Bukkit.broadcastMessage("");
            spawnss = areaManager.getDeathMatchSpawns(randomID);
            Jumper.getInstance().setSpec((HashMap<String, Location>) areaManager.getSpectatorSpawn(randomID));
            Jumper.getInstance().setSpecloc(areaManager.getSpectatorSpawn(randomID).get("specspawn"));
            for(int i = 0; i < Jumper.getInstance().getSpieler().size(); i++){
                Player player = (Player) Jumper.getInstance().getSpieler().stream().toArray()[i];
                Location location = spawnss.get("finish" + String.valueOf(i + 1));
                player.teleport(location);
            }
            Countdowns.setDeathmatchManager(new DeathmatchManager(Jumper.getInstance().getSpieler()));
            Jumper.getInstance().setDeathMatchScoreboard(new DeathMatchScoreboard(Countdowns.getDeathmatchManager()));
            Jumper.getInstance().getDeathMatchScoreboard().setIngameScoreboard();
            gracePeriodCountdown.startCountdown();
        }
    },Jumper.getInstance().getSettings().getIngameCountdow(),0,20L);


    @Getter private static Countdown gracePeriodCountdown = new Countdown(Jumper.getInstance(), new CountdownEvent() {
        @Override
        public void tick(int i) {
            if(i < 16){
                Title title = new Title("§4 Kampfphase in", " §6" + i + " Sekunden!",1,1,1);
                title.setTitleColor(ChatColor.BLUE);
                title.setSubtitleColor(ChatColor.GOLD);
                title.broadcast();
            }
        }

        @Override
        public void finish() {
            Jumper.getInstance().setGameState(GameState.DEATHMATCH);
            Countdowns.getDeathmatch().startCountdown();
        }
    },15,0,20L);

    @Getter private static Countdown deathmatch = new Countdown(Jumper.getInstance(), new CountdownEvent() {
        @Override
        public void tick(int i) {
            Jumper.getInstance().getDeathMatchScoreboard().update();
            DeathMatchScoreboard.scoreboardTimer = i;
            if(i == 300){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 5 Minuten!");
                Jumper.getInstance().checkEnd();
            }
            if(i == 240){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 4 Minuten!");
                Jumper.getInstance().checkEnd();
            }
            if(i == 180){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 3 Minuten!");
                Jumper.getInstance().checkEnd();
            }
            if(i == 120){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 2 Minuten!");
                Jumper.getInstance().checkEnd();
            }
            if(i == 60){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 1 Minuten!");
                Jumper.getInstance().checkEnd();
                Location location = Jumper.getInstance().getSpieler().get(0).getLocation();
                for(int il = 0; i < Jumper.getInstance().getSpieler().size(); i++){
                    Player player = (Player) Jumper.getInstance().getSpieler().stream().toArray()[il];
                    player.teleport(location);
                }
            }
            if(i == 30){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch 30 Sekunden!");
                Jumper.getInstance().checkEnd();
            }
            if(i <= 10){
                Bukkit.broadcastMessage(Jumper.getPREFIX() + " Noch " + i + " Sekunden!");
                Jumper.getInstance().checkEnd();
            }
        }

        @Override
        public void finish() {
            Bukkit.broadcastMessage(Jumper.getPREFIX() + " Keiner konnt das spiel für sich entscheiden!");
            Jumper.getInstance().stopServer();
        }
    },Jumper.getInstance().getSettings().getDeathMatchCountdown(),0,20L);

}
