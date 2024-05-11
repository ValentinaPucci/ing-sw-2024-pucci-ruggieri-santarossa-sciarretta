package it.polimi.demo.networking;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.view.GameDetails;
import it.polimi.demo.view.GameView;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface defines the methods that the server can call on the client.
 * Namely, server ----> client through Client interface!
 */
public interface Client extends Remote {
    /**
     * This method is called by the server to notify the client about the list of games.
     * @param o the list of games
     */
    void updateGamesList(List<GameDetails> o) throws RemoteException;

    void showStatus(GameStatus status) throws RemoteException;

    /**
     * This method is called by the server when there is an error to show.
     * @param err the error message
     */
    void showError(String err) throws RemoteException;

    /**
     * This method is called by the server when the list of players in the current game changes.
     * @param o the list of players
     */
    void updatePlayersList(List<String> o) throws RemoteException;


    void gameUnavailable() throws RemoteException;

    /**
     * This method is called by the server to notify the client that the game it is in has started.
     * It should trigger a UI change.
     */
    void gameHasStarted() throws RemoteException;

    /**
     * This method is called by the server to notify the client that the model has changed.
     * @param gameView the new model-view object of the game
     */
    void modelChanged(GameView gameView) throws RemoteException;

    /**
     * This method is called by the server to notify the client that the game it is in has ended.
     */
    void gameEnded(GameView gameView) throws RemoteException;

    /**
     * This method is called by the server to check if the client is still connected.
     * The client should respond with a {@link Server#pong()}.
     */
    void ping() throws RemoteException;
}
