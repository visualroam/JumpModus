package me.dominik.Jumper.listener.other;

import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class SeverPingEvent implements Listener{

    private GameState gameState = Jumper.getInstance().getGameState();

    @EventHandler
    public void ServerPing(ServerListPingEvent e) {
        if (gameState == GameState.WAITING || gameState == GameState.COUNTDOWN) {
            e.setMotd("§4§lWaiting for Players");
        } else if (gameState == GameState.INGAME || gameState == GameState.DEATHMATCH) {
            e.setMotd("§9§lIn einem Game!");
        } else if (gameState == GameState.ENDING) {
            e.setMotd("§7§lRestarting!");
        }
    }

}
