package me.dominik.Jumper.listener.player;

import me.dominik.Jumper.Countdowns;
import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player p = event.getPlayer();
        if(Jumper.getInstance().getGameState().isLobby()){
            if (Jumper.getInstance().getGameState() != GameState.ENDING && (Bukkit.getOnlinePlayers().size() - 1) < Jumper.getInstance().getSettings().getMinPlayers() && Jumper.getInstance().getGameState() == GameState.COUNTDOWN) {
                Countdowns.getLobbyCountdown().stopCountdown(true);
                Jumper.getInstance().setGameState(GameState.WAITING);
                Bukkit.broadcastMessage(Jumper.PREFIX + "§cEs sind zuwenige Spieler online um das Spiel zu starten. Warte auf §4" + (Jumper.getInstance().getSettings().getMinPlayers() - (Bukkit.getOnlinePlayers().size() - 1)) + " §cweitere Spieler...");
            }
            Jumper.getInstance().getVoteingManager().removePlayerVote(p);
            event.setQuitMessage(Jumper.getPREFIX() + "§2[-] §e" + p.getName());
        } else{
            Jumper.getInstance().checkEnd();
            Jumper.getInstance().getSpieler().remove(p);
            event.setQuitMessage(null);
        }
    }

}
