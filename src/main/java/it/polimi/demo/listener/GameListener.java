package it.polimi.demo.listener;

import java.rmi.RemoteException;

/**
 * This interface identifies a listener for the GameModel class.
 */
public interface GameListener extends Listener {
    /**
     * This method is invoked by the GameModel class when the model has a significant change.
     * It is triggered only during the game.
     */
    void modelChanged() throws RemoteException;

    /**
     * This method is invoked by the GameModel class when the game is ended.
     * It should trigger the end of the game for the client.
     */
    void gameEnded() throws RemoteException;

    /**
     * This method is invoked when the game is started.
     */
    void gameStarted() throws RemoteException;

    /**
     * This method is invoked when a player joins the game.
     */
    void playerJoinedGame() throws RemoteException;

    /**
     * This method is invoked when the game becomes full.
     */
    void gameIsFull() throws RemoteException;

    // *************************** GameListListener ***************************

    void newGame() throws RemoteException;

    void removedGame() throws RemoteException;

    void updatedGame() throws RemoteException;
}
