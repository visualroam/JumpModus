package me.dominik.Jumper.listener.player;

import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginEventListener implements Listener{
    @EventHandler
    public void onLoginEvent(PlayerLoginEvent e){
        if(Jumper.getInstance().getGameState() == GameState.INGAME || Jumper.getInstance().getGameState() == GameState.AFTER || Jumper.getInstance().getGameState() == GameState.DEATHMATCH || Jumper.getInstance().getGameState() == GameState.ENDING){
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§4DER SERVER IST GERADE INGAME");
        } else {
            if(Jumper.getInstance().getSpieler().size() >= 4){
                e.disallow(PlayerLoginEvent.Result.KICK_FULL, "§3DER SERVER IS VOLL");
            } else {

            }
        }
    }

}
