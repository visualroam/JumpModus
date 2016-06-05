package me.dominik.Jumper;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Settings {

    @Getter @Setter private int lobbyCountdown = 120;
    @Getter @Setter private int ingameCountdow = 600;
    @Getter @Setter private int deathMatchCountdown = 300;

    @Getter @Setter private GameState startupMode = GameState.WAITING;

    @Getter @Setter private int minPlayers = 1;




}




