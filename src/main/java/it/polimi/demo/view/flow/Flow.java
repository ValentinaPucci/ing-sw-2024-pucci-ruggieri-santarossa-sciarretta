package it.polimi.demo.view.flow;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.interfaces.PlayerIC;
import it.polimi.demo.view.flow.utilities.FileDisconnection;

import java.io.Serializable;

public abstract class Flow implements GameListener, Serializable {

    /**
     * Resets the game id
     * @param fileDisconnection file to reset
     * @param model model to get players
     */
    protected void resetGameId(FileDisconnection fileDisconnection, GameModelImmutable model) {
        for (PlayerIC p : model.getPlayersConnected()) {
            fileDisconnection.setLastGameId(p.getNickname(), -1);
        }
    }

    /**
     * Saves latest game id
     * @param fileDisconnection file to write
     * @param nick nickname of the player
     * @param gameId game id to save
     */
    protected void saveGameId(FileDisconnection fileDisconnection, String nick, int gameId) {
            fileDisconnection.setLastGameId(nick, gameId);
    }

    /**
     * Shows no connection error
     */
    public abstract void noConnectionError();

}