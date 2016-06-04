package me.dominik.Jumper.commands;

import me.dominik.Jumper.GameState;
import me.dominik.Jumper.Jumper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EditCommand implements CommandExecutor {

    private GameState gameState = Jumper.getInstance().getGameState();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if(sender.hasPermission("edit")){
            if(gameState != GameState.EDIT){
                Jumper.getInstance().setGameState(GameState.EDIT);
            } else {
                Jumper.getInstance().setGameState(GameState.WAITING);
            }
        }
        return true;
    }
}
