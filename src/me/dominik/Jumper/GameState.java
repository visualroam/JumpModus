package me.dominik.Jumper;

/**
 * Created by Dominik on 18.05.2016.
 */
public enum GameState {

    WAITING(true), COUNTDOWN(true), INGAME(false), AFTER(false),GRACEPERIOD(false) ,DEATHMATCH(false), ENDING(true), EDIT(false);

    private boolean lobby;

    GameState(boolean lobby) {
        this.lobby = lobby;
    }

    public boolean isLobby() {

        return lobby;
    }
}
